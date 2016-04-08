package com.wenjie.app.Tanxun.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
/**
 * 学生信息实体类
 * @author dell
 *
 */
public class StudentInfo extends BmobObject {
	private String studentId;//学号
	private String studentPasswd;//密码
	private String studentName;//姓名
	private BmobFile studentIcon;//学生自定义头像
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentPasswd() {
		return studentPasswd;
	}
	public void setStudentPasswd(String studentPasswd) {
		this.studentPasswd = studentPasswd;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public BmobFile getStudentIcon() {
		return studentIcon;
	}
	public void setStudentIcon(BmobFile studentIcon) {
		this.studentIcon = studentIcon;
	}
	
}
