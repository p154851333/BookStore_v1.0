package com.monkey.bookstore.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.monkey.bookstore.dao.OrderDao;

public class OrderServiceTest {
	OrderService orderService = new OrderService();
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAddOrder() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindOrderByUid() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteOrder() {
		orderService.deleteOrder("A04AC7B4F3564911BC16CA692B93739E");
	}
	@Test
	public void testConfirmGoods() {
		try {
			orderService.confirmGoods("358490D38C30499CADBFC480E05F1FB2");
		} catch (OrderException e) {
			System.out.println(e.getMessage());
		}
	}

}
