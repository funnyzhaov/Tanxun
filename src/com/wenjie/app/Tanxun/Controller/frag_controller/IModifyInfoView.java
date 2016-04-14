package com.wenjie.app.Tanxun.Controller.frag_controller;

import com.wenjie.app.Tanxun.model.StudentInfo;

/**
 * 修改个人资料UI接口
 * @author dell
 *
 */
public interface IModifyInfoView {
	/**
	 * 更新个人信息
	 * @param stuinfo
	 */
	void setTextInfo(StudentInfo stuinfo);
	/**
	 * 获取当前学生的ObjectId
	 * @param stuinfo
	 */
	void getStudentObjectId(StudentInfo stuinfo);
	/**
	 * 更新头像
	 */
	void updateImage(String imagePath);
}
