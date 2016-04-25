package com.wenjie.app.Tanxun.activity.fragment.Frag_activity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.wenjie.app.Tanxun.R;
import com.wenjie.app.Tanxun.activity.BaseActivity;
import com.wenjie.app.Tanxun.model.Comments;
import com.wenjie.app.Tanxun.model.Question;
import com.wenjie.app.Tanxun.model.StudentInfo;
import com.wenjie.app.Tanxun.model.adapter.CommentsAdapter;
import com.wenjie.app.Tanxun.util.NetWorkImgeUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 问题详情Activity
 * @author dell
 *
 */
public class QuestionDetailsActivity extends Activity {
	/*
	 * 问题详情控件
	 */
	private TextView deQuestionTitle;//问题标题
	private TextView deQuestionContent;//问题内容
	private TextView deQuestionTime;//问题发表时间
	private ImageView deQuestionIcon;//问题发表人图片
	/*
	 * 评论控件 
	 */
	private CommentsAdapter adapter;//适配器
	static List<Comments> listdata=new ArrayList<Comments>(); //评论数据集合
	private ListView mCommentsListView;  //ListView
	/*
	 * 发表评论控件 
	 */
	private Button btnCommit;//发表
	private EditText yourComment;//评论文本
	private ImageButton imgbt_backhome;//返回
	/*
	 * 数据
	 */
	private int queid; //问题id
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_home);
		initQuestionView();
		initQuestionData();
		queryCommentsData();
	}
	
	

	/**
	 * 初始化问题控件
	 */
	private void initQuestionView() {
		imgbt_backhome=(ImageButton)findViewById(R.id.imgbt_backhome);
		imgbt_backhome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Intent i=new Intent(QuestionDetailsActivity.this,BaseActivity.class);
				//startActivity(i);
				finish();
			}
		});
		deQuestionTitle=(TextView)findViewById(R.id.dequestion_title);
		deQuestionContent=(TextView)findViewById(R.id.dequestion_content);
		deQuestionTime=(TextView)findViewById(R.id.dequestion_time);
		deQuestionIcon=(ImageView)findViewById(R.id.dequestion_Icon);
		btnCommit=(Button)findViewById(R.id.dequestion_commit);
		btnCommit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveCommentData();
			}
		});
		yourComment=(EditText)findViewById(R.id.dequestion_edit);
		mCommentsListView=(ListView)findViewById(R.id.list_comments);
		adapter=new CommentsAdapter(this, R.layout.comments_details_item, listdata);
		mCommentsListView.setAdapter(adapter);
		
	}
	/**
	 * 初始化问题数据
	 */
	private void initQuestionData() {
		//获取intent传入的qeustionId值
		 queid=getIntent().getIntExtra("questionId",0);
		//查找相应问题的数据
		BmobQuery<Question> query=new BmobQuery<Question>();
		query.addWhereEqualTo("questionId", queid);
		query.findObjects(this, new FindListener<Question>() {
			
			@Override
			public void onSuccess(List<Question> arg0) {
				Question nowqu=arg0.get(0);
				deQuestionTitle.setText(nowqu.getQuestionTitle());
				deQuestionContent.setText(nowqu.getQuestionContent());
				deQuestionTime.setText(nowqu.getCreatedAt());
				//获取图片URL
				String path=nowqu.getStudentIcon().getFileUrl(QuestionDetailsActivity.this);
				//显示图片
				NetWorkImgeUtil.getInstance(QuestionDetailsActivity.this).imageRequest(path, deQuestionIcon);
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				
			}
		});
	}
	/**
	 * 初始化评论数据
	 */
	private void queryCommentsData() {
		 BmobQuery<Comments> query = new BmobQuery<Comments>();
			// 按时间降序查询
			query.order("-createdAt");
			query.setSkip(0);
			
			query.addWhereEqualTo("questionId", queid);
			// 查找数据
			query.findObjects(this, new FindListener<Comments>() {
				@Override
				public void onSuccess(List<Comments> list) {
					if(list.size()>0){
						listdata.clear();
						for (Comments td : list) {
							
							listdata.add(td);
						}
						adapter.notifyDataSetChanged();
					}else{
						listdata.clear();
						adapter.notifyDataSetChanged();
					}
					
				
				}
					@Override
					public void onError(int arg0, String arg1) {
						//adapter.notifyDataSetChanged();
					}
			});
	}
	/**
	 * 获取数据集合
	 * @return
	 */
	public static List<Comments> getListData(){
		return listdata;
	}
	/**
	 * 插入数据库评论表
	 */
	public void saveCommentData(){
		
		//获取当前用户信息（姓名和IconPath）
		SharedPreferences pref=getSharedPreferences("nowstudentdata", 0);
		String studentId=pref.getString("stuid", "");

		BmobQuery<StudentInfo> stuQue=new BmobQuery<StudentInfo>();
		stuQue.addWhereEqualTo("studentId", studentId);
		stuQue.findObjects(this,new FindListener<StudentInfo>() {

			@Override
			public void onError(int arg0, String arg1) {
				Log.d("findObjects", "获取数据失败!");
			}

			@Override
			public void onSuccess(List<StudentInfo> arg0) {
				String picIconPath=arg0.get(0).getStudentIcon().getFileUrl(QuestionDetailsActivity.this);
				String userName=arg0.get(0).getStudentName();
				//获取评论内容
				String content=yourComment.getText().toString();
				Comments comment=new Comments();
				comment.setQuestionId(queid);
				comment.setCommentContent(content);
				comment.setCommentUserIconPath(picIconPath);
				comment.setCommentUserName(userName);
				comment.save(QuestionDetailsActivity.this, new SaveListener() {
					
					@Override
					public void onSuccess() {
						Log.d("save", "保存数据成功!");
						queryCommentsData();
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						Log.d("save", "保存数据失败!");
					}
				});
				
			}
		});
	}



	@Override
	public void onBackPressed() {
		finish();
	}
	
	
}
