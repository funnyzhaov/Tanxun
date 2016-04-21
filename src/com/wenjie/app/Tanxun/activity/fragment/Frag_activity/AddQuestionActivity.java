package com.wenjie.app.Tanxun.activity.fragment.Frag_activity;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.wenjie.app.Tanxun.R;
import com.wenjie.app.Tanxun.Controller.frag_controller.IAddQuestionView;
import com.wenjie.app.Tanxun.activity.BaseActivity;
import com.wenjie.app.Tanxun.activity.fragment.HomeFragment;
import com.wenjie.app.Tanxun.model.Question;
import com.wenjie.app.Tanxun.model.StudentInfo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * 添加问题Activity
 * @author dell
 *
 */
public class AddQuestionActivity extends Activity implements IAddQuestionView {
	/*
	 * 添加问题页面控件
	 */
	private EditText editque_title;//问题标题
	private EditText editque_content;//问题内容
	private Button   editque_commit;//发表问题
	private static String  nowquesObjectId;//当前问题Id
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addquestion_home);
		initView();
	}
	/**
	 * 初始化视图
	 */
	public void initView(){
		editque_title=(EditText)findViewById(R.id.addq_title);
		editque_content=(EditText)findViewById(R.id.addq_content);
		editque_commit=(Button)findViewById(R.id.addq_commit);
		//提交事件 
		editque_commit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveQuestionData(AddQuestionActivity.this);
			}
		});
	}
	/**
	 * 保存问题数据至数据库中
	 */
	public void saveQuestionData(final IAddQuestionView addQuestion){
		/*
		 * 获取数据
		 */
		//获取当前用户信息（studentId和图片名）
		SharedPreferences pref=getSharedPreferences("nowstudentdata", 0);
		String studentId=pref.getString("stuid", "");
		BmobQuery<StudentInfo> stuQue=new BmobQuery<StudentInfo>();
		stuQue.addWhereEqualTo("studentId", studentId);
		stuQue.findObjects(this, new FindListener<StudentInfo>() {
			
			@Override
			public void onSuccess(List<StudentInfo> arg0) {
				Question question=new Question();
				question.setStudentId(arg0.get(0).getStudentId());
				question.setQuestionTitle(editque_title.getText().toString());
				question.setQuestionContent(editque_content.getText().toString());
				
				//保存数据
				question.save(AddQuestionActivity.this,new SaveListener() {
					
					@Override
					public void onSuccess() {
						Toast.makeText(getApplicationContext(), "保存问题数据成功！", Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						Toast.makeText(getApplicationContext(), "保存问题数据失败！", Toast.LENGTH_SHORT).show();
					}
				});
				//获取Id
				getQuestionObjectId();
				//上传图片
				String picfile=getApplicationContext()
						.getCacheDir()+"/bmob/"+arg0.get(0).getStudentIcon().getFilename();
				final BmobFile bmobFile=new BmobFile(new File(picfile));
				bmobFile.uploadblock(AddQuestionActivity.this, new UploadFileListener() {
					
					@Override
					public void onSuccess() {
						Toast.makeText(getApplicationContext(), "上传图片成功！", Toast.LENGTH_SHORT).show();
						//关联图片
						Question que=new Question();
						que.setStudentIcon(bmobFile);
						que.update(AddQuestionActivity.this, nowquesObjectId, new UpdateListener() {
							
							@Override
							public void onSuccess() {
								Toast.makeText(getApplicationContext(), "发表问题成功！", Toast.LENGTH_SHORT).show();
								addQuestion.updateView();
								Intent intent=new Intent(AddQuestionActivity.this,BaseActivity.class);
								startActivity(intent);
							}
							
							@Override
							public void onFailure(int arg0, String arg1) {
								Toast.makeText(getApplicationContext(), "发表问题失败！", Toast.LENGTH_SHORT).show();
							}
						});
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						Toast.makeText(getApplicationContext(), "上传图片失败！", Toast.LENGTH_SHORT).show();
					}
					@Override
					public void onProgress(Integer value) {
						// 返回的上传进度（百分比）
					}
				});
				
				
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				
			}
		});
		
		
	}
	
	@Override
	public void updateView() {
		editque_title.setText("");
		editque_content.setText("");
	}
	/**
	 * 通过问题标题获取获取objectId
	 */
	public void getQuestionObjectId(){
		BmobQuery<Question> query=new BmobQuery<Question>();
		query.addWhereEqualTo("questionTitle", editque_title.getText().toString());
		query.findObjects(this, new FindListener<Question>() {

			@Override
			public void onError(int arg0, String arg1) {
				
			}

			@Override
			public void onSuccess(List<Question> arg0) {
				nowquesObjectId=arg0.get(0).getObjectId();
			}
		});
	}

}
