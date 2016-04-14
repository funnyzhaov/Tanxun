package com.wenjie.app.Tanxun.Controller;

import cn.bmob.v3.datatype.BmobFile;
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
	/**
	 * 启动下载个人头像的后台服务
	 */
	void startServiceForupload(BmobFile fileIcon);
	
}
