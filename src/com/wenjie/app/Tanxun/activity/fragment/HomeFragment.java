package com.wenjie.app.Tanxun.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.wenjie.app.Tanxun.R;
import com.wenjie.app.Tanxun.activity.fragment.Frag_activity.QuestionDetailsActivity;
import com.wenjie.app.Tanxun.model.Question;
import com.wenjie.app.Tanxun.model.StudentInfo;
import com.wenjie.app.Tanxun.model.adapter.QuestionAdapter;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
/**
 * Home主页
 * @author dell
 *
 */
public class HomeFragment extends Fragment {
	private Activity InBaseActivity;//当前Fragment的载体Activity
	private View homeview;
	private QuestionAdapter adapter; //适配器
	static List<Question> listdata=new ArrayList<Question>(); //数据集合
	BmobQuery<Question> query;
	PullToRefreshListView Pulllistview;    
	ListView mMsgListView;   
	private int limit = 7;		// 每页的数据是7条
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		homeview=inflater.inflate(R.layout.home, container, false);
		
		initListView();
		queryData(0);
		return homeview;
	}
	
	public static List<Question> getListData(){
		return listdata;
	}
	
	/**
	 * 初始化视图
	 */
	private void initListView(){
		InBaseActivity=getActivity();
		Pulllistview=(PullToRefreshListView)homeview.findViewById(R.id.list_question);
		//设置子项单击事件
		Pulllistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Question nowQuestion=listdata.get(position-1);
				Intent intent=new Intent(InBaseActivity,QuestionDetailsActivity.class);
				//装入问题Id的值
				int Id=(Integer) nowQuestion.getQuestionId();
				intent.putExtra("questionId", Id);
				startActivity(intent);
			}
		});
		//设置监听事件
		Pulllistview.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// 下拉刷新(从第一页开始装载数据)
				queryData(0);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				
			}
		});
		//adapter.setActivity(InBaseActivity);
		mMsgListView=Pulllistview.getRefreshableView();
		adapter=new  QuestionAdapter(InBaseActivity, R.layout.question_home_item, listdata);
		mMsgListView.setAdapter(adapter);
	}
	/**
	 * 下拉查询
	 * @param page 页码
	 */
	private void queryData(int page) {
		 BmobQuery<Question> query = new BmobQuery<Question>();
		// 按时间降序查询
		query.order("-createdAt");
		query.setSkip(0);
		// 设置每页数据个数
		query.setLimit(limit);
		// 查找数据
		query.findObjects(InBaseActivity, new FindListener<Question>() {
			@Override
			public void onSuccess(List<Question> list) {
				if(list.size()>0){
					listdata.clear();
					// 将本次查询的数据添加到bankCards中
					for (Question td : list) {
						
						listdata.add(td);
					}
					// 这里在每次加载完数据后，将当前页码+1，这样在上拉刷新的onPullUpToRefresh方法中就不需要操作curPage了
					adapter.notifyDataSetChanged();
					Pulllistview.onRefreshComplete();
				}
				
			
			}
				@Override
				public void onError(int arg0, String arg1) {
					Pulllistview.onRefreshComplete();
				}
		});
	}
	
	
	
}
