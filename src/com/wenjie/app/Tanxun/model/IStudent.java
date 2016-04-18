package com.wenjie.app.Tanxun.model;

import com.wenjie.app.Tanxun.Controller.ILoginController;
import com.wenjie.app.Tanxun.Controller.IStudentInfoView;

import android.content.Context;

/**
 * 
 * 学生业务接口:负责登录和BaseActivity中数据的访问和处理
 * @author dell
 *
 */
public interface IStudent {
	
	/**
	 * 延时操作
	 * @param stuId     学号
	 * @param stuPawd   密码
	 * @param context  
	 * @param logincon  登录模块Controller实例(当前Activity)
	 */
	void doLoginHandle(final String stuId, final String stuPawd,final Context context,final ILoginController logincon);
	/**
	 * 登录操作
	 * @param stuId
	 * @param stuPawd
	 * @param context
	 * @param logincon
	 */
	void doLogin(final String stuId, final String stuPawd,final Context context,final ILoginController logincon);
	/**
	 * 通过学号查询学生的名字和头像
	 * @param studentId
	 * @param context  当前Fragment的载体Activity
	 */
	void doPersonShow(String studentId,final Context context,final IStudentInfoView infoView);
	/**
	 * 通过学号查询头像pic
	 * @param studentId
	 */
	void queryImageById(String studentId,final Context context,final int i);
}
