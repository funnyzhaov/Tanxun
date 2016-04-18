package com.wenjie.app.Tanxun.model;

import cn.bmob.v3.BmobObject;
/**
 * 问题表对应模型
 * @author dell
 *
 */
public class Question extends BmobObject {
	private String studentId; //学生ID
	private String questionTitle; //问题标题
	private String questionContent; //问题内容
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getQuestionTitle() {
		return questionTitle;
	}
	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}
	public String getQuestionContent() {
		return questionContent;
	}
	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}
}
