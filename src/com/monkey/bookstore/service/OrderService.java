package com.monkey.bookstore.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.jdbc.JdbcUtils;

import com.monkey.bookstore.dao.OrderDao;
import com.monkey.bookstore.po.order.Order;

/**
 * <p>类名称	OrderService </p>
* <p>类描述	订单的业务层</p>
* @author	裴健
* @date		2017年3月28日 下午2:16:43
 */
public class OrderService {
	private OrderDao orderDao = new OrderDao();
	
	/**
	 * <p>方法名	addOrder </p>
	* <p>方法描述	添加订单
	* 				同时添加订单中的订单条目</p>
	* 参数 @param order 参数说明
	* 返回类型 void 返回类型
	* @author	裴健
	* @date		2017年3月28日 下午3:34:55
	 */
	public void addOrder(Order order){
		//添加订单和添加订单条目需要事务处理
		try {
			JdbcUtils.beginTransaction();
			orderDao.addOrder(order);
			orderDao.addOrderItem(order.getOrderitem());
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
			}
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>方法名	findOrderByUid </p>
	* <p>方法描述	查询订单</p>
	* 参数 @param uid
	* 参数 @return 参数说明
	* 返回类型 List<Order> 返回类型
	* @author	裴健
	* @date		2017年3月28日 下午6:56:20
	 */
	public List<Order> findOrderByUid(String uid) {
		return orderDao.findOrderByUid(uid);
	}

	/**
	 * <p>方法名	deleteOrder </p>
	* <p>方法描述	根据oid删除订单</p>
	* 参数 @param oid 参数说明
	* 返回类型 void 返回类型
	* @author	裴健
	* @date		2017年3月28日 下午10:10:28
	 */
	public void deleteOrder(String oid) {
		//删除订单之前，先删除订单明细
		try {
			JdbcUtils.beginTransaction();
			orderDao.deleteOrderItem(oid);
			orderDao.deleteOrder(oid);
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
			}
			throw new RuntimeException(e);
		}
	}

	public Order findOrderByOid(String oid) {
		return orderDao.findOrderByOid(oid);
	}
	
	/**
	 * <p>方法名	confirmGoods </p>
	* <p>方法描述	确认收货功能。
	* 				查询订单状态是否为3，否则抛出异常。
	* 				为3则修改订单状态为4
	* </p>
	* 参数 @param oid 参数说明
	* 返回类型 void 返回类型
	* @author	裴健
	 * @throws OrderException 
	* @date		2017年3月29日 下午2:22:56
	 */
	public void confirmGoods(String oid) throws OrderException {
		try {
			JdbcUtils.beginTransaction();
			//调用dao查询订单状态
			int state =orderDao.findOrderStateByOid(oid);
			if (state==3) {
				orderDao.updateOrderStateByOid(oid,4);
			}else {
				throw new OrderException("您在非法操作！");
			}
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
			}
			e.printStackTrace();
		}
		
	}

	public void zhiFu(String oid) {
		/*
		 * 1. 获取订单的状态
		 *   * 如果状态为1，那么执行下面代码
		 *   * 如果状态不为1，那么本方法什么都不做
		 */
		int state = orderDao.findOrderStateByOid(oid);
		if(state == 1) {
			// 修改订单状态为2
			orderDao.updateOrderStateByOid(oid, 2);
		}
		
	}

	/**
	 * <p>方法名	findAll </p>
	* <p>方法描述	查询所有订单</p>
	* 参数 @return 参数说明
	* 返回类型 List<Order> 返回类型
	* @author	裴健
	* @date		2017年3月30日 下午9:52:52
	 */
	public List<Order> findAll() {
		return orderDao.findAll();
	}

	/**
	 * <p>方法名	findOrderByState </p>
	* <p>方法描述	根据订单状态查询订单</p>
	* 参数 @param i
	* 参数 @return 参数说明
	* 返回类型 List<Order> 返回类型
	* @author	裴健
	* @date		2017年3月30日 下午10:40:46
	 */
	public List<Order> findOrderByState(int i) {
		return orderDao.findOrderByState(i);
	}
}
