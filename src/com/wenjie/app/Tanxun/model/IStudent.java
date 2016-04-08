package com.wenjie.app.Tanxun.model;

import com.wenjie.app.Tanxun.Controller.ILoginController;

import android.content.Context;

/**
 * 登录模块
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
}
