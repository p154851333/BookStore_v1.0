package com.monkey.bookstore.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.monkey.bookstore.po.order.Order;

public class OrderDaoTest {
	private OrderDao dao = new OrderDao();
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAddOrder() {
	}

	@Test
	public void testAddOrderItem() {
	}

	@Test
	public void testFindOrderByUid() {
		List<Order> findOrderByUid = dao.findOrderByUid("136059D52CC84B8882F0A8647E91C4D9");
		System.out.println(findOrderByUid);
	}
	@Test
	public void testFindOrderByOid() {
		Order order = dao.findOrderByOid("3EE257D0E1C74DDE89D0A0C0E6A0F27E");
		System.out.println(order);
	}
	@Test
	public void testFindOrderStateByOid() {
		int order = dao.findOrderStateByOid("358490D38C30499CADBFC480E05F1FB2");
		System.out.println(order);
	}

}
