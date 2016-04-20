package com.wenjie.app.Tanxun.activity.fragment.Frag_activity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.wenjie.app.Tanxun.R;
import com.wenjie.app.Tanxun.model.Question;
import com.wenjie.app.Tanxun.util.NetWorkImgeUtil;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_home);
		initQuestionView();
		initQuestionData();
	}
	
	/**
	 * 初始化问题控件
	 */
	private void initQuestionView() {
		deQuestionTitle=(TextView)findViewById(R.id.dequestion_title);
		deQuestionContent=(TextView)findViewById(R.id.dequestion_content);
		deQuestionTime=(TextView)findViewById(R.id.dequestion_time);
		deQuestionIcon=(ImageView)findViewById(R.id.dequestion_Icon);
	}
	/**
	 * 初始化问题数据
	 */
	private void initQuestionData() {
		//获取intent传入的qeustionId值
		int id=getIntent().getIntExtra("questionId",0);
		//查找相应问题的数据
		BmobQuery<Question> query=new BmobQuery<Question>();
		query.addWhereEqualTo("questionId", id);
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
	
}
