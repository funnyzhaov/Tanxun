package com.wenjie.app.Tanxun.activity.fragment;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.wenjie.app.Tanxun.R;
import com.wenjie.app.Tanxun.model.StudentInfo;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Person主页
 * @author dell
 *
 */
public class PersonFragment extends Fragment {
	private String studentId=null;
	private String studentName="";
	private View personView;
	private Activity InBaseActivity;//当前Fragment的载体Activity
	private TextView textStuName;//学生姓名文本
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		personView=inflater.inflate(R.layout.person, container, false);
		initView();
		return personView;
	}
	/**
	 * 初始化视图
	 */
	private void initView(){
		InBaseActivity=getActivity();
		textStuName=(TextView)personView
				.findViewById(R.id.text_studentName);
		studentId=InBaseActivity.getIntent().getStringExtra("studentId");
		if(studentId!=null){
			//通过学号查找姓名
			 BmobQuery<StudentInfo> query=new BmobQuery<StudentInfo>();
			 query.addWhereEqualTo("studentId", studentId);
			 query.setLimit(5);
			 query.findObjects(InBaseActivity, new FindListener<StudentInfo>() {

				@Override
				public void onError(int arg0, String arg1) {
					Toast.makeText(getActivity(), "查询失败",Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onSuccess(List<StudentInfo> studentlist) {
					StudentInfo studentInfo=studentlist.get(0);
					studentName=studentInfo.getStudentName();
					//更新UI线程问题待解决
					//。。。。。。
					Toast.makeText(getActivity(), studentName,Toast.LENGTH_SHORT).show();
					textStuName.setText(studentName);
				}
			});
		}else{
			textStuName.setText("未登录");
		}
		
	}
}
