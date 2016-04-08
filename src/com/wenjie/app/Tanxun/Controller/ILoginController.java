package com.wenjie.app.Tanxun.Controller;

import android.content.Intent;

/**
 * 登录模块
 * 控制器接口
 * @author dell
 *
 */
public interface ILoginController {
	/**
	 * 设置进度条是否可见
	 */
	void onsetProgressBarVin(int vis);
	/**
	 * 进入主界面
	 */
	void enterBaseActivity(Intent intent);
	
}
