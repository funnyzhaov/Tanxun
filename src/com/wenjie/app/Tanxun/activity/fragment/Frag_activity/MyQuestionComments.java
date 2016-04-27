package com.wenjie.app.Tanxun.activity.fragment.Frag_activity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.wenjie.app.Tanxun.R;
import com.wenjie.app.Tanxun.model.Comments;
import com.wenjie.app.Tanxun.model.adapter.CommentsAdapter;
import com.wenjie.app.Tanxun.model.adapter.MyQueCommentsAdapter;
import com.wenjie.app.Tanxun.model.adapter.MyQuestionAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 我的问题评论详情
 * @author dell
 *
 */
public class MyQuestionComments extends Activity {
	/*
	 * 评论控件 
	 */
	private MyQueCommentsAdapter adapter;//适配器
	static List<Comments> listdata=new ArrayList<Comments>(); //评论数据集合
	private ListView mCommentsListView;  //ListView
	private TextView tvCommentNum;//标题
	private ImageButton ibBackMyqedetail;//返回键
	/*
	 * 数据
	 */
	private int queid; //问题id
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comments_myqe_home);
		initView();
		queryCommentsData();
	}
	
	/**
	 * 初始化控件
	 */
	public void initView() {
		queid=getIntent().getIntExtra("myquedetailid", 0);
		tvCommentNum=(TextView)findViewById(R.id.tv_comment_num);
		ibBackMyqedetail=(ImageButton)findViewById(R.id.ib_back_myqedetail);
		ibBackMyqedetail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mCommentsListView=(ListView)findViewById(R.id.lv_comments);
		adapter=new MyQueCommentsAdapter(this, R.layout.comments_myqe_item, listdata);
		mCommentsListView.setAdapter(adapter);
	}
	/**
	 * 查询评论数据
	 */
	public void queryCommentsData() {
		
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
						int num=0;
						for (Comments td : list) {
							listdata.add(td);
							num++;
						}
						setTitleBar(num);
						adapter.notifyDataSetChanged();
					}else{
						listdata.clear();
						adapter.notifyDataSetChanged();
					}
				}
					@Override
					public void onError(int arg0, String arg1) {
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
	 * 设置标题文字
	 * @param num 回答个数
	 */
	public void setTitleBar(int num){
		tvCommentNum.setText(String.valueOf(num)+"个回答");
	}
	
	
}
