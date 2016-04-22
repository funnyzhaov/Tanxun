package com.wenjie.app.Tanxun.activity.fragment;

import com.wenjie.app.Tanxun.R;
import com.wenjie.app.Tanxun.Controller.IStudentInfoView;
import com.wenjie.app.Tanxun.activity.LoginActivity;
import com.wenjie.app.Tanxun.activity.fragment.Frag_activity.ModifyInfoActivity;
import com.wenjie.app.Tanxun.model.IStudent;
import com.wenjie.app.Tanxun.model.IStudentImpl;
import com.wenjie.app.Tanxun.util.DataCleanManager;
import com.wenjie.app.Tanxun.util.RoundImage;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Person主页
 * @author dell
 *
 */
public class PersonFragment extends Fragment implements IStudentInfoView ,OnClickListener{
	private IStudent Istudent;//处理信息查询的接口
	private String studentId=null;
	private View personView;
	private Activity InBaseActivity;//当前Fragment的载体Activity
	private TextView textStuName;//学生姓名文本
	private LinearLayout modifyInfoLay;//修改资料布局
	private boolean isOnlie;//用户是否在线
	private String studentName="";//学生姓名
	private ImageView imageHead;//学生头像图片
	private LinearLayout clearCahe;//清除缓存组件
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		personView=inflater.inflate(R.layout.person, container, false);
		initView();
		return personView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
	}
	

	@Override
	public void onStart() {
		super.onStart();
		initView();
	}

	/**
	 * 初始化视图
	 */
	private void initView(){
		Istudent=new IStudentImpl();
		InBaseActivity=getActivity();
		textStuName=(TextView)personView
				.findViewById(R.id.text_studentName);
		imageHead=(ImageView)personView.findViewById(R.id.image_head);
		clearCahe=(LinearLayout)personView.findViewById(R.id.clear_data);
		//尝试用文件取出id
		SharedPreferences pref=getActivity().getSharedPreferences("nowstudentdata", 0);
		studentId=pref.getString("stuid", "");
		//studentId=InBaseActivity.getIntent().getStringExtra("studentId");
		modifyInfoLay=(LinearLayout)personView.findViewById(R.id.modify_info);
		modifyInfoLay.setOnClickListener(this);
		clearCahe.setOnClickListener(this);
		isOnlie=isOnline(studentId);
	}
	@Override
	public void UpdateInfoName(String studentName) {
		this.studentName=studentName;
		textStuName.setText(studentName);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.clear_data:
			Log.d("dianji", "点击了");
			DataCleanManager.cleanApplicationData(getActivity().getApplicationContext(), 
					"");
			break;
		case R.id.modify_info:
			if(isOnlie){
					//跳转至修改页面
					Intent intent=new Intent(InBaseActivity,ModifyInfoActivity.class);
					intent.putExtra("studentName", studentName);
					startActivity(intent);
			}else{
				//跳转至登录页面
				Intent intent=new Intent(InBaseActivity,LoginActivity.class);
				startActivity(intent);
			}
			break;
		default:
			break;
		}
		
	}
	/**
	 * 判断用户是否登录
	 * @param studentId
	 * @return
	 */
	public boolean isOnline(String studentId){
		if(studentId!=null){
			Istudent.doPersonShow(studentId, InBaseActivity,this);
			return true;
		}else{
			textStuName.setText("未登录");
			return false;
		}
	}
	@Override
	public void updateInfoImage(String imagePath) {

		if(imageHead.getDrawable().getCurrent().getConstantState()
				.equals(getResources().getDrawable(R.drawable.head).getConstantState()))
		{

			if(imagePath!=null){
				Log.d("picupdate", imagePath);
				Bitmap rawBitmap=BitmapFactory.decodeFile(imagePath);
				//得到图片原始的高宽
				int rawHeight = rawBitmap.getHeight();
				int rawWidth = rawBitmap.getWidth();
				// 设定图片新的高宽
				int newHeight = 90;
				int newWidth = 90;
				// 计算缩放因子
				float heightScale = ((float) newHeight) / rawHeight;
				float widthScale = ((float) newWidth) / rawWidth;
				// 新建立矩阵
				Matrix matrix = new Matrix();
				matrix.postScale(heightScale, widthScale);
				// 压缩后图片的宽和高以及kB大小均会变化
				Bitmap newBitmap = Bitmap.createBitmap(rawBitmap, 0, 0, rawWidth,
						rawHeight, matrix, true);
				//回收大图的对象
				if(!rawBitmap.isRecycled())
				{
					rawBitmap.recycle();
				}     
				imageHead.setImageBitmap(RoundImage.roundImage(newBitmap));
			}else{
				Toast.makeText(InBaseActivity, "failed to get image", Toast.LENGTH_SHORT).show();
			}
		}
	}

	
}
