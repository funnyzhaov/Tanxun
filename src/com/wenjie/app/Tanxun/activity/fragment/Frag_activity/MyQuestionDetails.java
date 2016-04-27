package com.wenjie.app.Tanxun.activity.fragment.Frag_activity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.wenjie.app.Tanxun.R;
import com.wenjie.app.Tanxun.model.Comments;
import com.wenjie.app.Tanxun.model.Question;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
/**
 * 我的问题详情
 * @author dell
 *
 */
public class MyQuestionDetails extends Activity implements OnClickListener{
	/*
	 * 
	 * UI组件
	 */
	private ImageButton ibBack;//返回按钮
	private TextView tvQetitle;//问题标题
	private TextView tvQeContent;//问题内容
	
	private FrameLayout flComments;//评论布局块
	private TextView tvQenum;//评论数量
	
	private int myQeId;//我的问题Id
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutquestion_myqe);
		initView();
		queryData();
	}
	/**
	 * 初始化布局
	 */
	public  void initView() {
		//获取传递的问题id
		myQeId=getIntent().getIntExtra("myqeId",0);
		ibBack=(ImageButton)findViewById(R.id.ib_back_myqe);
		ibBack.setOnClickListener(this);
		
		tvQetitle=(TextView)findViewById(R.id.tv_qe_title);
		tvQeContent=(TextView)findViewById(R.id.tv_qe_content);
		tvQenum=(TextView)findViewById(R.id.tv_qe_num);
		flComments=(FrameLayout)findViewById(R.id.fl_comments);
		flComments.setOnClickListener(this);
	}
	/**
	 * 设置数据
	 */
	public void setData(Question myque,int num){
		tvQetitle.setText(myque.getQuestionTitle());
		tvQeContent.setText(myque.getQuestionContent());
		tvQenum.setText(String.valueOf(num));
	}
	/**
	 * 查询数据
	 */
	public void queryData() {
		BmobQuery<Question> query = new BmobQuery<Question>();
		query.addWhereEqualTo("questionId", myQeId);
		query.findObjects(this, new FindListener<Question>() {
			
			@Override
			public void onSuccess(List<Question> quelist) {
				final Question mQue=quelist.get(0);
				BmobQuery<Comments> query2=new BmobQuery<Comments>();
				query2.addWhereEqualTo("questionId", myQeId);
				query2.findObjects(MyQuestionDetails.this, new FindListener<Comments>() {
					@Override
					public void onSuccess(List<Comments> arg0) {
						int comnum=0;//计数
						for (Comments comment : arg0) {
							comnum++;
						}
						//设置数据
						setData(mQue, comnum);
					}
					@Override
					public void onError(int arg0, String arg1) {
						
					}
				});
				
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				
			}
		});
	}
	
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//返回问题详情页面
		case R.id.ib_back_myqe:
			finish();
			break;
		//进入评论列表页面
		case R.id.fl_comments:
			Intent intentc=new Intent(this,MyQuestionComments.class);
			intentc.putExtra("myquedetailid", myQeId);
			startActivity(intentc);
			break;
		default:
			break;
		}
		
	}
	
}
