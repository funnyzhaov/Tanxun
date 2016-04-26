package com.wenjie.app.Tanxun.activity.fragment.Frag_activity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.wenjie.app.Tanxun.R;
import com.wenjie.app.Tanxun.activity.fragment.PersonFragment;
import com.wenjie.app.Tanxun.model.Question;
import com.wenjie.app.Tanxun.model.adapter.MyQuestionAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
/**
 * 我的提问
 * @author dell
 *
 */
public class MyQuestionListActivity extends Activity {
	private MyQuestionAdapter myadapter;//适配器
	private static List<Question> listdata=new ArrayList<Question>();//数据集合
	private ListView myListView;//组件
	private String myStuid="";//当前用户id
	
	private ImageButton ib_back_person;//返回个人按钮
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myqe_person);
		initView();
		queryMyData();
	}
	
	/**
	 * 初始化视图
	 */
	public void initView() {
		myStuid=getIntent().getStringExtra("myStuid");
		myListView=(ListView)findViewById(R.id.lv_myquestion);
		//返回个人页面
		ib_back_person=(ImageButton)findViewById(R.id.ib_back_person);
		ib_back_person.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Question nowQuestion=listdata.get(position);
				Intent intent=new Intent(MyQuestionListActivity.this,MyQuestionDetails.class);
				//装入问题Id的值
				int Id= nowQuestion.getQuestionId();
				intent.putExtra("myqeId", Id);
				startActivity(intent);
			}
		});
		myadapter=new MyQuestionAdapter(this, R.layout.item_myqe_person, listdata);
		myListView.setAdapter(myadapter);
	}
	/**
	 * 查询数据
	 */
	public void queryMyData() {
		BmobQuery<Question> query = new BmobQuery<Question>();
		// 按时间降序查询
		query.order("-createdAt");
		query.addWhereEqualTo("studentId", myStuid);
		query.setSkip(0);
		// 查找数据
		query.findObjects(this, new FindListener<Question>() {
			@Override
			public void onSuccess(List<Question> list) {
				if(list.size()>0){
					listdata.clear();
					// 将本次查询的数据添加到bankCards中
					for (Question td : list) {
						//字符串截取处理
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
					myadapter.notifyDataSetChanged();
				}else{
					listdata.clear();
					myadapter.notifyDataSetChanged();
				}
			}
			@Override
			public void onError(int arg0, String arg1) {

			}
		});
	}
	public static List<Question> getListData(){
		return listdata;
	}
}
