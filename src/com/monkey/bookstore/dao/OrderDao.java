package com.monkey.bookstore.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

import com.monkey.bookstore.po.Book;
import com.monkey.bookstore.po.User;
import com.monkey.bookstore.po.order.Order;
import com.monkey.bookstore.po.order.OrderItem;

/**
 * <p>类名称	OrderDao </p>
* <p>类描述	订单的持久层	</p>
* @author	裴健
* @date		2017年3月28日 下午2:14:43
 */
public class OrderDao {

	private QueryRunner qr = new TxQueryRunner();
	
	/**
	 * <p>方法名	addOrder </p>
	* <p>方法描述	向数据库中添加订单</p>
	* 参数 @param order 参数说明
	* 返回类型 void 返回类型
	* @author	裴健
	* @date		2017年3月28日 下午2:32:18
	 */
	public void addOrder(Order order){
		try {
			String sql = "INSERT into orders values (?,?,?,?,?,?)";
			//时间对象的类型不同
			Timestamp ordertime = new Timestamp(order.getOrdertime().getTime());
			Object [] param = {order.getOid(),ordertime,order.getTotal(),
					order.getState(),order.getUser().getUid(),order.getAddress()};
			qr.update(sql,param);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * <p>方法名	addOrderItem </p>
	* <p>方法描述	添加订单条目。
	* 				条目可以一次添加很多，所以使用批处理添加
	* </p>
	* 参数 @param orderItemList 参数说明
	* 返回类型 void 返回类型
	* @author	裴健
	* @date		2017年3月28日 下午2:48:14
	 */
	public void addOrderItem(List<OrderItem> orderItemList){
		try {
			String sql = "INSERT INTO orderitem VALUES (?,?,?,?,?)";
			//创建参数的二维数组
			Object [][] params = new Object[orderItemList.size()][];
			//循环遍历给数组赋值
			for (int i = 0; i < orderItemList.size(); i++) {
				OrderItem item = orderItemList.get(i);
				params[i]=new Object[]{item.getIid(),item.getCount(),item.getSubtotal()
						,item.getOrder().getOid(),item.getBook().getBid()};
			}
			qr.batch(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>方法名	findOrderByUid </p>
	* <p>方法描述	查询用户订单</p>
	* 参数 @param uid
	* 参数 @return 参数说明
	* 返回类型 List<Order> 返回类型
	* @author	裴健
	* @date		2017年3月28日 下午6:57:21
	 */
	public List<Order> findOrderByUid(String uid) {
		try {
			//从数据库中查询到订单。
			String sqlOrder = "SELECT * FROM orders WHERE uid=?";
			List<Order> orderList = qr.query(sqlOrder, new BeanListHandler<Order>(Order.class),uid);
			//根据订单的集合，循环查询订单条目，并赋值到订单中。
			for (Order order : orderList) {
				//重新写一个加载Order对象的类（给Order内部的所有属性赋值）
				loaderOrder(order);
			}
			return orderList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>方法名	loaderOrder </p>
	* <p>方法描述	加载Order的类
	* 				为其属性赋值</p>
	* 参数 @param order 参数说明
	* 返回类型 void 返回类型
	* @author	裴健
	 * @throws SQLException 
	* @date		2017年3月28日 下午9:01:58
	 */
	private void loaderOrder(Order order) throws SQLException {
		//通过一个订单的id查询订单明细。得到该订单的订单明细列表，订单明细中的Book需要联合查询
		String sqlOrderItem = "SELECT * FROM orderitem,book WHERE orderitem.bid=book.bid AND oid=?";
		//得到联合查询的组合表。
		List<Map<String, Object>> mapList = qr.query(sqlOrderItem, new MapListHandler(),order.getOid());
		//创建一个订单明细集合，用来将联合查询结果结构化。
		List<OrderItem> orderItemList = toOrderItemList(mapList);
		
		//将查询出来的订单条目，添加到订单中,将所属用户添加到订单的属性中
		order.setOrderitem(orderItemList);
	}

	/**
	 * <p>方法名	toOrderItemList </p>
	* <p>方法描述	将联合查询出来的ListMap数据结构化为订单条目集合</p>
	* 参数 @param mapList
	* 参数 @return 参数说明
	* 返回类型 List<OrderItem> 返回类型
	* @author	裴健
	* @date		2017年3月28日 下午9:21:17
	 */
	private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
		//接受mapList的值
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		//循环遍历mapList
		for (Map<String, Object> map : mapList) {
			//将map映射成OrderItem对象
			OrderItem orderItem = mapToOrderItem(map);
			orderItemList.add(orderItem);
		}
		return orderItemList;
	}

	/**
	 * <p>方法名	mapToOrderItem </p>
	* <p>方法描述	将map转换成OrderItem对象</p>
	* 参数 @param map
	* 参数 @return 参数说明
	* 返回类型 OrderItem 返回类型
	* @author	裴健
	* @date		2017年3月28日 下午9:27:08
	 */
	private OrderItem mapToOrderItem(Map<String, Object> map) {
		//将联合查询得到的map封装成OrderItem对象
		OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
		//将联合查询得到的map封装成Book对象
		Book book = CommonUtils.toBean(map, Book.class);
		orderItem.setBook(book);
		return orderItem;
	}

	/**
	 * <p>方法名	findOrderByOid </p>
	* <p>方法描述	根据订单oid查询订单</p>
	* 参数 @param oid
	* 参数 @return 参数说明
	* 返回类型 Order 返回类型
	* @author	裴健
	* @date		2017年3月28日 下午10:58:43
	 */
	public  Order findOrderByOid(String oid) {
		try {
			String sql = "SELECT * FROM orders WHERE oid=?";
			Order order = qr.query(sql, new BeanHandler<Order>(Order.class),oid);
			this.loaderOrder(order);
			return order;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>方法名	deleteOrder </p>
	* <p>方法描述	根据oid删除订单</p>
	* 参数 @param oid 参数说明
	* 返回类型 void 返回类型
	* @author	裴健		
	* @date		2017年3月28日 下午10:11:20
	 */
	public void deleteOrder(String oid) {
		try {
			String sqlOrder = "DELETE	FROM orders WHERE oid =?";
			qr.update(sqlOrder, oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * <p>方法名	deleteOrderItem </p>
	* <p>方法描述	根据oid删除订单明细</p>
	* 参数 @param oid 参数说明
	* 返回类型 void 返回类型
	* @author	裴健
	* @date		2017年3月28日 下午10:19:55
	 */
	public void deleteOrderItem(String oid) {
		try {
			String sqlOrderItem = "DELETE	FROM orderitem WHERE oid =?";
			qr.update(sqlOrderItem, oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>方法名	findOrderStateByOid </p>
	* <p>方法描述	根据oid查询订单的当前状态</p>
	* 参数 @param oid
	* 参数 @return 参数说明
	* 返回类型 int 返回类型
	* @author	裴健
	* @date		2017年3月29日 下午2:37:24
	 */
	public int findOrderStateByOid(String oid) {
		try {
			String sql = "SELECT state FROM orders WHERE oid = ?";
			Number state = (Number) qr.query(sql, new ScalarHandler(),oid);
			return state.intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>方法名	updateOrderStateByOid </p>
	* <p>方法描述	</p>
	* 参数 @param oid 参数说明
	* 返回类型 void 返回类型
	* @author	裴健
	* @date		2017年3月29日 下午2:38:03
	 */
	public void updateOrderStateByOid(String oid ,int state) {
		String sql = "UPDATE	orders SET state = (?) WHERE oid =?";
		try {
			qr.update(sql,state,oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>方法名	findAll </p>
	* <p>方法描述	查询所有订单</p>
	* 参数 @return 参数说明
	* 返回类型 List<Order> 返回类型
	* @author	裴健
	* @date		2017年3月30日 下午9:53:10
	 */
	public List<Order> findAll() {
		try {
			String sql = "SELECT * FROM orders";
			List<Order> orderList = qr.query(sql,new BeanListHandler<Order>(Order.class));
			//根据订单的集合，循环查询订单条目，并赋值到订单中。
			for (Order order : orderList) {
				//重新写一个加载Order对象的类（给Order内部的所有属性赋值）
				loaderOrder(order);
			}
			return orderList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>方法名	findOrderByState </p>
	* <p>方法描述	根据订单状态查询订单</p>
	* 参数 @param i
	* 参数 @return 参数说明
	* 返回类型 List<Order> 返回类型
	* @author	裴健
	* @date		2017年3月30日 下午10:38:37
	 */
	public List<Order> findOrderByState(int i) {
		try {
			String sql = "SELECT * FROM orders WHERE state = ?";
			List<Order> orderList = qr.query(sql,new BeanListHandler<Order>(Order.class),i);
			//根据订单的集合，循环查询订单条目，并赋值到订单中。
			for (Order order : orderList) {
				//重新写一个加载Order对象的类（给Order内部的所有属性赋值）
				loaderOrder(order);
			}
			return orderList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
