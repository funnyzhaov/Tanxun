package com.wenjie.app.Tanxun.model;

import java.util.List;

import com.wenjie.app.Tanxun.Controller.ILoginController;
import com.wenjie.app.Tanxun.Controller.IStudentInfoView;
import com.wenjie.app.Tanxun.activity.BaseActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;

/**
 * 学生业务接口实现类
 * @author dell
 *
 */
public class IStudentImpl implements IStudent {
	private String picPath;
	StudentInfo stuInfo;                   //学生实例
	private Handler handler;	

	public IStudentImpl(){
		handler=new Handler(Looper.getMainLooper());
	}

	@Override
	public void doLogin(final String stuId, final String stuPawd,
			final Context context,final ILoginController logincon) {
		BmobQuery<StudentInfo> bmobQuery = new BmobQuery<StudentInfo>();	//从Bmob的数据库中查询 
		bmobQuery.addWhereEqualTo("studentId", stuId);
		bmobQuery.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);   	    //先从缓存获取数据，如果没有，再从网络获取。
		bmobQuery.findObjects(context, new FindListener<StudentInfo>() {

			@Override
			public void onSuccess(List<StudentInfo> arg0) {
				Intent intent=new Intent(context,BaseActivity.class);
				stuInfo=arg0.get(0);
				if(stuInfo!=null){
					if(stuId.equals(stuInfo.getStudentId())&& 
							stuPawd.equals(stuInfo.getStudentPasswd())){
						logincon.onsetProgressBarVin(View.INVISIBLE);
						
//						intent.putExtra("studentId", stuId);				//将学号信息放入intent
						Toast.makeText(context, "登陆成功！姓名:"+stuInfo.getStudentName(),//用一个封装的Log打印
								Toast.LENGTH_SHORT).show();
						BmobFile fileIcon=stuInfo.getStudentIcon();
						if(fileIcon!=null){
							logincon.startServiceForupload(fileIcon);	   //再进入主页面的同时，开启后台服务下载个人头像
						}
						//将个人学号信息放在SharedPreferences中
						SharedPreferences.Editor editor=context.getSharedPreferences("nowstudentdata", 0).edit();
						editor.putString("stuid", stuId);
						editor.commit();
						logincon.enterBaseActivity(intent);
					}else{
						logincon.onsetProgressBarVin(View.INVISIBLE);
						Toast.makeText(context, "密码错误!", Toast.LENGTH_SHORT).show();
					}
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				//				Toast.makeText(context, "查询失败，请检查学号是否正确", Toast.LENGTH_SHORT).show();
			}
		});

	}

	@Override
	public void doLoginHandle(final String stuId, final String stuPawd,
			final Context context,final ILoginController logincon) {
		logincon.onsetProgressBarVin(View.VISIBLE);
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				doLogin(stuId, stuPawd, context,logincon);
			}
		},1000);		
	}

	@Override
	public void doPersonShow(String studentId, final Context context,final IStudentInfoView infoView) {
		BmobQuery<StudentInfo> query=new BmobQuery<StudentInfo>();			//通过学号查找姓名
		query.clearCachedResult(context, StudentInfo.class);
		query.setCachePolicy(CachePolicy.IGNORE_CACHE);
		query.addWhereEqualTo("studentId", studentId);
		query.setLimit(5);
		query.findObjects(context, new FindListener<StudentInfo>() {

			@Override
			public void onError(int arg0, String arg1) {
				Toast.makeText(context, "查询个人信息失败",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(List<StudentInfo> studentlist) {
				StudentInfo studentInfo=studentlist.get(0);
				String studentName=studentInfo.getStudentName();
				String imagePath=context.getApplicationContext().getCacheDir()+"/bmob/"+
						studentInfo.getStudentIcon().getFilename();
				infoView.UpdateInfoName(studentName);
				infoView.updateInfoImage(imagePath);
				Log.d("pic", imagePath);
				Toast.makeText(context, "查询个人信息成功!",Toast.LENGTH_SHORT).show();
			}
		});
	}


}
