package com.monkey.bookstore.po;
/**
 * <p>类名称	User </p>
* <p>类描述	用户的领域对象</p>
* @author	裴健
* @date		2017年3月25日 下午5:16:36
 */
public class User {
/*
	用户表
	CREATE TABLE tb_user(
	  uid CHAR(32) PRIMARY KEY,主键
	  username VARCHAR(50) NOT NULL,用户名
	  `password` VARCHAR(50) NOT NULL,密码
	  email VARCHAR(50) NOT NULL,邮箱
	  `code` CHAR(64) NOT NULL,激活码
	  state BOOLEAN用户状态，有两种是否激活
	);*/
	
	
	private String uid; 		//用户id
	private String username; 	//用户名
	private String password; 	//密码
	private String email; 		//电子邮箱
	private String code; 		//激活码
	private boolean state; 		//激活状态
	

	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", password="
				+ password + ", email=" + email + ", code=" + code + ", state="
				+ state + "]";
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public User() {
		super();
	}
	public User(String uid, String username, String password, String email,
			String code, boolean state) {
		super();
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.email = email;
		this.code = code;
		this.state = state;
	}
	
	
}
