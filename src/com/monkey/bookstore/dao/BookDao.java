package com.monkey.bookstore.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.monkey.bookstore.po.Book;
import com.monkey.bookstore.po.Category;

import cn.itcast.jdbc.TxQueryRunner;

/**
 * <p>类名称	BookDao </p>
* <p>类描述	图书的持久层</p>
* @author	裴健
* @date		2017年3月26日 下午10:47:25
 */
public class BookDao {

	private QueryRunner qr  = new TxQueryRunner();
	
	/**
	 * <p>方法名	findAll </p>
	* <p>方法描述	查询所有图书</p>
	* 参数 @return 参数说明
	* 返回类型 List<Book> 返回类型
	* @author	裴健
	* @date		2017年3月26日 下午10:53:53
	 */
	public List<Book> findAll(){
		try {
			String sql = "SELECT * FROM book where del=FALSE ";
			return qr.query(sql, new BeanListHandler<Book>(Book.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>方法名	findBookByCategory </p>
	* <p>方法描述	根据分类cid，查询此分类的所有图书</p>
	* 参数 @param cid
	* 参数 @return 参数说明
	* 返回类型 List<Book> 返回类型
	* @author	裴健
	* @date		2017年3月29日 下午9:22:46
	 */
	public List<Book> findBookByCategory(String cid) {
		try {
			String sql = "SELECT * FROM book where cid =? and del=FALSE ";
			return qr.query(sql, new BeanListHandler<Book>(Book.class),cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>方法名	findBookById </p>
	* <p>方法描述	根据图书bid，查询图书</p>
	* 参数 @param bid
	* 参数 @return 参数说明
	* 返回类型 Book 返回类型
	* @author	裴健
	* @date		2017年3月29日 下午9:23:32
	 */
	public Book findBookById(String bid) {
		try {
			String sql = "SELECT * FROM book where bid =? and del=FALSE ";
			Book book = qr.query(sql, new BeanHandler<Book>(Book.class),bid);
			book=loaderBook(book,bid);//加载图书的所属分类
			return book;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>方法名	loaderBook </p>
	* <p>方法描述	加载图书的所属分类</p>
	* 参数 @param book
	* 参数 @param bid
	* 参数 @return 参数说明
	* 返回类型 Book 返回类型
	* @author	裴健
	* @date		2017年3月29日 下午9:24:53
	 */
	private Book loaderBook(Book book,String bid) {
		try {
			String sql = "SELECT cid FROM book where bid =?";
			String cid = (String) qr.query(sql, new ScalarHandler() ,bid);
			CategoryDao categoryDao = new CategoryDao();
			Category category = categoryDao.findCateforyBcid(cid);
			book.setCategory(category);
			return book;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * <p>方法名	addBook </p>
	* <p>方法描述	添加图书</p>
	* 参数 @param book 参数说明
	* 返回类型 void 返回类型
	* @author	裴健
	* @date		2017年3月30日 下午6:31:43
	 */
	public void addBook(Book book) {
		try {
			String sql = "INSERT INTO book VALUES (?,?,?,?,?,?,FALSE)";
			Object[] params = { book.getBid(), book.getBname(),
					book.getPrice(), book.getAuthor(), book.getImage(),
					book.getCategory().getCid()};
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>方法名	delete </p>
	* <p>方法描述	根据bid修改图书的显示方式（删除）</p>
	* 参数 @param bid 参数说明
	* 返回类型 void 返回类型
	* @author	裴健
	* @date		2017年3月30日 下午8:28:31
	 */
	public void delete(String bid) {
		String sql ="UPDATE book SET del=true WHERE bid=?";
		try {
			qr.update(sql,bid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public void updateBook(Book book) {
		try {
			String sql ="UPDATE book SET bname=?,price=?,author=?,cid=? WHERE bid=?";
			Object[]params ={book.getBname(),book.getPrice(),book.getAuthor(),
						book.getCategory().getCid(),book.getBid()};
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
