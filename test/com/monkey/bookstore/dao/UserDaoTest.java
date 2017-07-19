package com.monkey.bookstore.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cn.itcast.commons.CommonUtils;

import com.monkey.bookstore.po.User;

public class UserDaoTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindUserByName() {
		UserDao dao = new UserDao();
		User user = dao.findUserByName("admin");
		System.out.println(user);
	}

	@Test
	public void testFindUseByEmail() {
		UserDao dao = new UserDao();
		User user = dao.findUseByEmail("admin@qq.com");
		System.out.println(user);
	}

	@Test
	public void testAddUser() {
		UserDao dao = new UserDao();
		User form = new User();
		form.setUid(CommonUtils.uuid());
		form.setUsername("admin");
		form.setPassword("admin");
		form.setEmail("admin@qq.com");
		form.setCode(CommonUtils.uuid()+CommonUtils.uuid());
		form.setState(false);
		dao.addUser(form);
		
	}
	
	@Test
	public void testUpdateState() {
		UserDao dao = new UserDao();
		dao.updateState("1D10C4740C1E4CFCA16EB243241F8130",false );
	}

}
