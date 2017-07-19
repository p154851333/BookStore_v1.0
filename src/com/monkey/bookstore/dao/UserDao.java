package com.monkey.bookstore.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.monkey.bookstore.po.User;

import cn.itcast.jdbc.TxQueryRunner;


/**
 * <p>类名称	UserDao </p>
* <p>类描述	User持久层</p>
* @author	裴健
* @date		2017年3月25日 下午5:21:41
 */
public class UserDao {
	//依赖对象(查询对象)
	private QueryRunner qr = new TxQueryRunner();
	
	/**
	 * <p>方法名	findUserByName </p>
	* <p>方法描述	通过username得到User</p>
	* 参数 @param username
	* 参数 @return 参数说明
	* 返回类型 User 返回类型
	* @author	裴健
	* @date		2017年3月25日 下午9:07:05
	 */
	public User findUserByName(String username){
		try {
			//得到sql模板
			String sql = "SELECT * FROM tb_user where username =?";
			User user;
			user = qr.query(sql, new BeanHandler<User>(User.class),username);
			return user;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * <p>方法名	findUseByEmail </p>
	* <p>方法描述	通过email得到user</p>
	* 参数 @param email
	* 参数 @return 参数说明
	* 返回类型 User 返回类型
	* @author	裴健
	* @date		2017年3月25日 下午9:09:23
	 */
	public User findUseByEmail(String email){
		try {
			//得到sql模板
			String sql = "SELECT * FROM tb_user where email =?";
			User user = qr.query(sql, new BeanHandler<User>(User.class),email);
			return user;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void addUser(User form){
		try {
			//得到sql模板
			String sql = "INSERT INTO tb_user VALUES (?,?,?,?,?,?)";
			Object [] param = {form.getUid(),form.getUsername(),form.getPassword()
					,form.getEmail(),form.getCode(),form.isState()};
			qr.update(sql, param);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>方法名	findUseByCode </p>
	* <p>方法描述	根据code查询是否有user</p>
	* 参数 @param code
	* 参数 @return 参数说明
	* 返回类型 User 返回类型
	* @author	裴健
	* @date		2017年3月26日 下午4:47:56
	 */
	public User findUseByCode(String code) {
		try {
			String sql= "SELECT * FROM tb_user WHERE code=?";
			User user = qr.query(sql, new BeanHandler<User>(User.class), code);
			return user;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * <p>方法名	updateState </p>
	* <p>方法描述	更改数据state(账户激活状态)</p>
	* 参数 @param uid
	* 参数 @param state 参数说明
	* 返回类型 void 返回类型
	* @author	裴健
	* @date		2017年3月26日 下午4:49:23
	 */
	public void updateState(String uid ,boolean state){
		try {
			String sql = "UPDATE tb_user SET state=? WHERE uid=?";
			qr.update(sql, state,uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>方法名	findUserById </p>
	* <p>方法描述	通过uid得到user对象</p>
	* 参数 @param uid
	* 参数 @return 参数说明
	* 返回类型 User 返回类型
	* @author	裴健
	* @date		2017年3月28日 下午8:42:29
	 */
	public User findUserById(String uid) {
		try {
			String sql = "SELECT * FROM tb_user where uid =?";
			User user;
			user = qr.query(sql, new BeanHandler<User>(User.class), uid);
			return user;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
