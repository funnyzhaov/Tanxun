package com.wenjie.app.Tanxun.model.frag_model;

import android.content.Context;

import com.wenjie.app.Tanxun.Controller.frag_controller.IModifyInfoView;
import com.wenjie.app.Tanxun.model.StudentInfo;

/**
 * 个人页面操作接口
 * @author dell
 *
 */
public interface IPerson {
	
	/**
	 * 获取当前学生信息对象
	 * @param studentName    学生姓名
	 * @param context
	 * @param imofidyView    修改个人资料Controller实例
	 */
	void getStudentInfo(final String studentName,final Context context,final IModifyInfoView imofidyView);
}
