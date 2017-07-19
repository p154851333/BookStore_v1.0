package com.monkey.bookstore.web.servlet;

import cn.itcast.servlet.BaseServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.monkey.bookstore.service.BookService;

/**
 * Servlet implementation class BookServlet
 */
@WebServlet("/BookServlet")
public class BookServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private BookService bookService = new BookService();

	/**
	 * <p>方法名	findAll </p>
	* <p>方法描述	查询所有图书</p>
	* 参数 @param request
	* 参数 @param response
	* 参数 @return
	* 参数 @throws ServletException
	* 参数 @throws IOException 参数说明
	* 返回类型 String 返回类型
	* @author	裴健
	* @date		2017年3月26日 下午11:09:31
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("bookList",bookService.findAll());
		return "f:/jsps/book/list.jsp";
	}
	
	
	/**
	 * <p>方法名	findBookByCategory </p>
	* <p>方法描述	按照分类cid查询图书</p>
	* 参数 @return 参数说明
	* 返回类型 String 返回类型
	* @author	裴健
	* @date		2017年3月26日 下午11:09:46
	 */
	public String findBookByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		request.setAttribute("bookList", bookService.findBookByCategory(cid));
		return "f:/jsps/book/list.jsp";
	}
	
	/**
	 * <p>方法名	findBookById </p>
	* <p>方法描述	通过图书bid查询图书</p>
	* 参数 @param request
	* 参数 @param response
	* 参数 @return
	* 参数 @throws ServletException
	* 参数 @throws IOException 参数说明
	* 返回类型 String 返回类型
	* @author	裴健
	* @date		2017年3月26日 下午11:21:50
	 */
	public String findBookById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bid = request.getParameter("bid");
		request.setAttribute("book", bookService.findBookById(bid));
		return "f:/jsps/book/desc.jsp";
	}

}
