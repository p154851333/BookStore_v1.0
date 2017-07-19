package com.monkey.bookstore.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.jdbc.TxQueryRunner;

import com.monkey.bookstore.po.Category;

/**
 * <p>类名称	CategoryDao </p>
* <p>类描述	分类模块的持久层</p>
* @author	裴健
* @date		2017年3月26日 下午10:09:54
 */
public class CategoryDao {
	private QueryRunner qr = new TxQueryRunner();

	/**
	 * <p>方法名	findAll </p>
	* <p>方法描述	查询所有分类</p>
	* 参数 @return 参数说明
	* 返回类型 List<Category> 返回类型
	* @author	裴健
	* @date		2017年3月26日 下午10:34:58
	 */
	public List<Category> findAll() {
		try {
			String sql = "SELECT * FROM category";
			return qr.query(sql, new BeanListHandler<Category>(Category.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void addCategory(Category category) {
		try {
			String sql = "INSERT INTO category (cid,cname) VALUES(?,?)";
			qr.update(sql,category.getCid(),category.getCname());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>方法名	delete </p>
	* <p>方法描述	根据cid删除分类</p>
	* 参数 @param cid 参数说明
	* 返回类型 void 返回类型
	* @author	裴健
	* @date		2017年3月29日 下午8:12:46
	 */
	public void delete(String cid) {
		String sql ="DELETE	FROM category WHERE cid =?";
		try {
			qr.update(sql,cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Category findCateforyBcid(String cid) {
		try {
			String sql ="SELECT * FROM category WHERE cid =?";
			Category category = qr.query(sql, new BeanHandler<Category>(Category.class),cid);
			return category;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void updateCategory(Category category) {
		try {
			String sql = "UPDATE	category SET cname=? WHERE cid =?";
			qr.update(sql,category.getCname(),category.getCid());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

}
