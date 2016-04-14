package com.wenjie.app.Tanxun.model;

import com.wenjie.app.Tanxun.Controller.ILoginController;
import com.wenjie.app.Tanxun.Controller.IStudentInfoView;

import android.content.Context;

/**
 * 
 * 学生业务接口
 * @author dell
 *
 */
public interface IStudent {
	/**
	 * 延时操作
	 */
	void doLoginHandle(final String stuId, final String stuPawd,final Context context,final ILoginController logincon);
	/**
	 * 处理登录结果
	 */
	void doLogin(final String stuId, final String stuPawd,final Context context,final ILoginController logincon);
	/**
	 * 通过学号查询学生的名字和头像
	 * @param studentId
	 * @param context 当前Fragment的载体Activity
	 */
	void doPersonShow(String studentId,final Context context,final IStudentInfoView infoView);
}
