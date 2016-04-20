package com.wenjie.app.Tanxun.activity.fragment.Frag_activity;

import com.wenjie.app.Tanxun.R;

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
		int id=getIntent().getIntExtra("questionId",0);
		Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
	}
	
}
