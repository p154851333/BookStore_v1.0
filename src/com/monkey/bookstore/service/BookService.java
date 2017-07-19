package com.monkey.bookstore.service;

import java.util.List;

import com.monkey.bookstore.dao.BookDao;
import com.monkey.bookstore.po.Book;

/**
 * <p>类名称	BookService </p>
* <p>类描述	图书类的业务成</p>
* @author	裴健
* @date		2017年3月26日 下午10:54:45
 */
public class BookService {
	BookDao bookDao = new BookDao();
	
	/**
	 * <p>方法名	findAll </p>
	* <p>方法描述	查询所有图书</p>
	* 参数 @return 参数说明
	* 返回类型 List<Book> 返回类型
	* @author	裴健
	* @date		2017年3月26日 下午10:55:58
	 */
	public List<Book> findAll(){
		return bookDao.findAll();
	}

	public List<Book> findBookByCategory(String cid) {
		return bookDao.findBookByCategory(cid);
	}

	public Book findBookById(String bid) {
		return bookDao.findBookById(bid);
	}

	/**
	 * <p>方法名	addBook </p>
	* <p>方法描述	添加图书</p>
	* 参数 @param book 参数说明
	* 返回类型 void 返回类型
	* @author	裴健
	* @date		2017年3月30日 下午6:26:17
	 */
	public void addBook(Book book) {
		bookDao.addBook(book);
	}

	/**
	 * <p>方法名	delete </p>
	* <p>方法描述	修改指定bid的显示状态（删除）</p>
	* 参数 @param bid 参数说明
	* 返回类型 void 返回类型
	* @author	裴健
	* @date		2017年3月30日 下午8:27:39
	 */
	public void delete(String bid) {
		bookDao.delete(bid);
	}

	public void updateBook(Book book) {
		bookDao.updateBook(book);
	}
	
}
