package com.monkey.bookstore.web.adminservlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.monkey.bookstore.po.Book;
import com.monkey.bookstore.po.Category;
import com.monkey.bookstore.service.BookService;
import com.monkey.bookstore.service.CategoryService;

/**
 * Servlet implementation class AdminBookServlet
 */
@WebServlet("/admin/AdminBookServlet")
public class AdminBookServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private BookService bookService = new BookService();
	private CategoryService categoryService = new CategoryService();
	/**
	 * 显示所有的图书
	 */
	public String findAllBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Book> bookList = bookService.findAll();
		request.setAttribute("bookList", bookList);
		return "f:/adminjsps/admin/book/list.jsp";
	}

	/**
	 * <p>方法名	findBookByBid </p>
	* <p>方法描述	根据id查询图书</p>
	* 参数 @param request
	* 参数 @param response
	* 参数 @return
	* 参数 @throws ServletException
	* 参数 @throws IOException 参数说明
	* 返回类型 String 返回类型
	* @author	裴健
	* @date		2017年3月29日 下午8:57:28
	 */
	public String findBookByBid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//得到页面的图书bid
		String bid = request.getParameter("bid");
		//根据bid查询对应的图书
		Book book = bookService.findBookById(bid);
		//将图书添加到域中
		request.setAttribute("book", book);
		//因为页面将要修改图书的所属分类，这里查询所有分类
		List<Category> categoryList = categoryService.findAll();
		//将所有分类添加到域中
		request.setAttribute("categoryList",categoryList);
		return "f:/adminjsps/admin/book/desc.jsp";
	}
	
	public String addBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//因为页面将要修改图书的所属分类，这里查询所有分类
		List<Category> categoryList = categoryService.findAll();
		//将所有分类添加到域中
		request.setAttribute("categoryList",categoryList);
		return "f:/adminjsps/admin/book/add.jsp";
	}
	
	/**
	 * <p>方法名	delete </p>
	* <p>方法描述	改变图书的显示状态</p>
	* 参数 @param request
	* 参数 @param response
	* 参数 @return
	* 参数 @throws ServletException
	* 参数 @throws IOException 参数说明
	* 返回类型 String 返回类型
	* @author	裴健
	* @date		2017年3月30日 下午8:25:22
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//得到页面传来的bid
		String bid = request.getParameter("bid");
		bookService.delete(bid);
		return this.findAllBook(request, response);
	}
	
	public String edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//得到页面的book信息，封装成book对象
		Book book = CommonUtils.toBean(request.getParameterMap(), Book.class);
		//得到页面传来的图书分类cid
		String cid = request.getParameter("cid");
		book.setCategory(categoryService.findCateforyBcid(cid));
		bookService.updateBook(book);
		return this.findAllBook(request, response);
	}
	
}
