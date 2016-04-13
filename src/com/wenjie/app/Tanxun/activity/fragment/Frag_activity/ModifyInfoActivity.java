package com.wenjie.app.Tanxun.activity.fragment.Frag_activity;

import cn.bmob.v3.listener.InitListener;

import com.wenjie.app.Tanxun.R;
import com.wenjie.app.Tanxun.Controller.frag_controller.IModifyInfoView;
import com.wenjie.app.Tanxun.model.StudentInfo;
import com.wenjie.app.Tanxun.model.frag_model.IPerson;
import com.wenjie.app.Tanxun.model.frag_model.IPersonImpl;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
/**
 * 修改个人头像Activity
 * @author dell
 *
 */
public class ModifyInfoActivity extends Activity implements IModifyInfoView{
	private IPerson Iperson;
	private TextView stuNameText;//学生姓名文本
	private TextView stuSexText;//学生性别文本
	private TextView stuLevelText;//学生年级文本
	private TextView stuMajorText;//学生专业文本
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_person);
		initViewInfo();
	}
	/**
	 * 初始化视图信息
	 */
	public void initViewInfo(){
		Iperson=new IPersonImpl();
		stuNameText=(TextView)findViewById(R.id.studentName);
		stuSexText=(TextView)findViewById(R.id.sex);
		stuLevelText=(TextView)findViewById(R.id.level);
		stuMajorText=(TextView)findViewById(R.id.major);
		Iperson.getStudentInfo(getIntent().getStringExtra("studentName"),
				ModifyInfoActivity.this,this);
	}
	@Override
	public void setTextInfo(StudentInfo stuinfo) {
		stuNameText.setText(stuinfo.getStudentName());
		stuSexText.setText(stuinfo.getStudentSex());
		stuLevelText.setText(stuinfo.getStudentLevel());
		stuMajorText.setText(stuinfo.getMajor());
		
	}
	
}
