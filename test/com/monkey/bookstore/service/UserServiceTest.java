package com.monkey.bookstore.service;

import org.junit.Before;
import org.junit.Test;

import cn.itcast.commons.CommonUtils;

import com.monkey.bookstore.po.User;

public class UserServiceTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRegist() {
		UserService service = new UserService();
		User form = new User();
		form.setUid(CommonUtils.uuid());
		form.setUsername("admin1");
		form.setPassword("admin1");
		form.setEmail("admin1@qq.com");
		form.setCode(CommonUtils.uuid()+CommonUtils.uuid());
		form.setState(false);
		try {
			service.regist(form);
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void zhengze(){
		String s="Aadmindmin1？";
		boolean matches = s.matches("(?=.*[0-9])(?=.*[a-zA-Z]).{8,30}");
		//^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$
		System.out.println(s);
		System.out.println(matches);
	}
	
	@Test
	public void active(){
		UserService service = new UserService();
		try {
			service.active("AED91AEDFEED4F899952E638473D2AA10D488D9CCD9E4A2AB1DC82B77D19BDD8");
		} catch (UserException e) {
			e.printStackTrace();
		}
	}
	
	

}
