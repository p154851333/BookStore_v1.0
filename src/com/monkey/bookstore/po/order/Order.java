package com.monkey.bookstore.po.order;

import java.util.Date;
import java.util.List;

import com.monkey.bookstore.po.User;

/**
 * <p>类名称	Order </p>
* <p>类描述	订单领域对象</p>
* @author	裴健
* @date		2017年3月28日 下午2:02:28
 */
public class Order {
	private String oid ;//订单id
	private Date ordertime;//订单创建时间
	private double total;//订单合计
	private int state;//订单状态，1.未付款，2.已付款无发货，3.已发货，未收货，4.已收货，订单完成。
	private String address;//收货地址
	private User user;//订单所属用户。
	private List<OrderItem> orderitem;//所有的订单明细
	@Override
	public String toString() {
		return "Order [oid=" + oid + ", ordertime=" + ordertime + ", total="
				+ total + ", state=" + state + ", address=" + address
				+ ", user=" + user + ", orderitem=" + orderitem + "]";
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<OrderItem> getOrderitem() {
		return orderitem;
	}
	public void setOrderitem(List<OrderItem> orderitem) {
		this.orderitem = orderitem;
	}
	
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Order(String oid, Date ordertime, double total, int state,
			String address, User user, List<OrderItem> orderitem) {
		super();
		this.oid = oid;
		this.ordertime = ordertime;
		this.total = total;
		this.state = state;
		this.address = address;
		this.user = user;
		this.orderitem = orderitem;
	}
	
}
