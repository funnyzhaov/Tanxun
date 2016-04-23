package com.wenjie.app.Tanxun.Controller;

/**
 * 学生个人信息页面接口
 * @author dell
 *
 */
public interface IStudentInfoView {
	/**
	 * 更新姓名信息
	 * @param studentName
	 */
	void UpdateInfoName(String studentName);
	/**
	 * 更新头像
	 */
	void updateInfoImage(String imagePath);
	
	
}
