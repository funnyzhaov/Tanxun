package com.wenjie.app.Tanxun.model;

import java.util.List;

import com.wenjie.app.Tanxun.Controller.ILoginController;
import com.wenjie.app.Tanxun.activity.BaseActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;

/**
 * 学生业务接口实现类
 * @author dell
 *
 */
public class IStudentImpl implements IStudent {
	
	StudentInfo stuInfo;
	private Handler handler;
	//构造方法
	public IStudentImpl(){
		handler=new Handler(Looper.getMainLooper());
	}
	@Override
	public void doLogin(final String stuId, final String stuPawd,
			final Context context,final ILoginController logincon) {
		
		//从Bmob的数据库中查询 
		BmobQuery<StudentInfo> bmobQuery = new BmobQuery<StudentInfo>();
		bmobQuery.addWhereEqualTo("studentId", stuId);
		bmobQuery.findObjects(context, new FindListener<StudentInfo>() {
			
			@Override
			public void onSuccess(List<StudentInfo> arg0) {
				Intent intent=new Intent(context,BaseActivity.class);
				stuInfo=arg0.get(0);
				if(stuInfo!=null){
					if(stuId.equals(stuInfo.getStudentId())&& 
							stuPawd.equals(stuInfo.getStudentPasswd())){
						
						logincon.onsetProgressBarVin(View.INVISIBLE);
						//将学号信息放入intent
						intent.putExtra("studentId", stuId);
						//用一个封装的Log打印
						Toast.makeText(context, "登陆成功！姓名:"+stuInfo.getStudentName(),
								Toast.LENGTH_SHORT).show();
						
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
		},3000);		
	}

}
