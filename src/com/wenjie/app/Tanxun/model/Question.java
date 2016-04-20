package com.wenjie.app.Tanxun.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
/**
 * 问题表对应模型
 * @author dell
 *
 */
public class Question extends BmobObject {
	private String studentId; //学生ID
	private Integer questionId;//问题ID
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	private String questionTitle; //问题标题
	private String questionContent; //问题内容
	private BmobFile studentIcon;//学生头像
	public BmobFile getStudentIcon() {
		return studentIcon;
	}
	public void setStudentIcon(BmobFile studentIcon) {
		this.studentIcon = studentIcon;
	}
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
