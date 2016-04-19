package com.wenjie.app.Tanxun.model;

import cn.bmob.v3.BmobObject;
/**
 * 评论实体模型
 * @author dell
 *
 */
public class Comments extends BmobObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Number questionId;//问题Id
	private String commentContent;//评论内容
	private String commentUserName;//评论人姓名
	private String commentUserIconPath;//评论人头像URL
	public Number getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Number questionId) {
		this.questionId = questionId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getCommentUserName() {
		return commentUserName;
	}
	public void setCommentUserName(String commentUserName) {
		this.commentUserName = commentUserName;
	}
	public String getCommentUserIconPath() {
		return commentUserIconPath;
	}
	public void setCommentUserIconPath(String commentUserIconPath) {
		this.commentUserIconPath = commentUserIconPath;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
