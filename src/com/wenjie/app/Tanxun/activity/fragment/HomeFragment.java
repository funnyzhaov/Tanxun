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
import com.wenjie.app.Tanxun.activity.fragment.Frag_activity.AddQuestionActivity;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
/**
 * Home��ҳ
 * @author dell
 *
 */
public class HomeFragment extends Fragment {
	public static Activity InBaseActivity;//��ǰFragment������Activity
	private View homeview;
	private QuestionAdapter adapter; //������
	static List<Question> listdata=new ArrayList<Question>(); //���ݼ���
	BmobQuery<Question> query;
	PullToRefreshListView Pulllistview;    
	ListView mMsgListView;   
	private int limit = 7;		// ÿҳ��������7��
	
	/*
	 * �������
	 */
	private ImageButton plusQuestion;//�������ⰴť
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
	 * ��ʼ����ͼ
	 */
	private void initListView(){
		InBaseActivity=getActivity();
		//�ҵ�Button�ؼ�
		plusQuestion=(ImageButton)homeview.findViewById(R.id.imgbt_plus);
		//��ת������ҳ��
		plusQuestion.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intentto=new Intent(InBaseActivity,AddQuestionActivity.class);
				startActivity(intentto);
				InBaseActivity.overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
			}
		});
		Pulllistview=(PullToRefreshListView)homeview.findViewById(R.id.list_question);
		//����������¼�
		Pulllistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Question nowQuestion=listdata.get(position-1);
				Intent intent=new Intent(InBaseActivity,QuestionDetailsActivity.class);
				//װ������Id��ֵ
				int Id= nowQuestion.getQuestionId();
				intent.putExtra("questionId", Id);
				startActivity(intent);
				
			}
		});
		//���ü����¼�
		Pulllistview.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// ����ˢ��(�ӵ�һҳ��ʼװ������)
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
	 * ������ѯ
	 * @param page ҳ��
	 */
	private void queryData(int page) {
		 BmobQuery<Question> query = new BmobQuery<Question>();
		// ��ʱ�併���ѯ
		query.order("-createdAt");
		query.setSkip(0);
		// ����ÿҳ���ݸ���
		query.setLimit(limit);
		// ��������
		query.findObjects(InBaseActivity, new FindListener<Question>() {
			@Override
			public void onSuccess(List<Question> list) {
				if(list.size()>0){
					listdata.clear();
					// �����β�ѯ���������ӵ�bankCards��
					for (Question td : list) {
						//�ַ�����ȡ����
						String newContent="";
						String newTitle="";
						if(td.getQuestionTitle().length()<=20){
							newTitle=td.getQuestionTitle();
						}else{
							newTitle=td.getQuestionTitle().substring(0, 20)+"...?";
						}
						if(td.getQuestionContent().length()<=12){
							 newContent=td.getQuestionContent();
						}else{
							newContent=td.getQuestionContent().substring(0, 12)+"...";
						}
						td.setQuestionTitle(newTitle);
						td.setQuestionContent(newContent);
						
						listdata.add(td);
					}
					// ������ÿ�μ��������ݺ󣬽���ǰҳ��+1������������ˢ�µ�onPullUpToRefresh�����оͲ���Ҫ����curPage��
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