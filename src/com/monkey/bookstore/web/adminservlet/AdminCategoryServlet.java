package com.monkey.bookstore.web.adminservlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.monkey.bookstore.po.Category;
import com.monkey.bookstore.service.CategoryService;

/**
 * 后台管理员的表示层
 */
@WebServlet("/admin/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private CategoryService categoryService = new CategoryService();
	/**
	 * 查询所有的分类
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Category> categoryList = categoryService.findAll();
		request.setAttribute("categoryList", categoryList);
		return "f:/adminjsps/admin/category/list.jsp";
	}

	/**
	 * <p>方法名	addCategory </p>
	* <p>方法描述	添加分类</p>
	* 参数 @param request
	* 参数 @param response
	* 参数 @return
	* 参数 @throws ServletException
	* 参数 @throws IOException 参数说明
	* 返回类型 String 返回类型
	* @author	裴健
	* @date		2017年3月29日 下午7:38:55
	 */
	public String addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1.将页面的表单数据封装成category对象
		 * 2.为其创建cid
		 * 3.调用业务层添加方法
		 */
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		category.setCid(CommonUtils.uuid());
		categoryService.addCategory(category);
		return this.findAll(request, response);
	}
	
	/**
	 * <p>方法名	updateCategory </p>
	* <p>方法描述	根据cid修改分类</p>
	* 参数 @param request
	* 参数 @param response
	* 参数 @return
	* 参数 @throws ServletException
	* 参数 @throws IOException 参数说明
	* 返回类型 String 返回类型
	* @author	裴健
	* @date		2017年3月29日 下午8:33:36
	 */
	public String updateCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1.将页面的表单数据封装成category对象
		 * 2.为其创建cid
		 * 3.调用业务层添加方法
		 */
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		categoryService.updateCategory(category);
		return this.findAll(request, response);
	}
	
	/**
	 * <p>方法名	deleteCatefory </p>
	* <p>方法描述	删除分类</p>
	* 参数 @param request
	* 参数 @param response
	* 参数 @return
	* 参数 @throws ServletException
	* 参数 @throws IOException 参数说明
	* 返回类型 String 返回类型
	* @author	裴健
	* @date		2017年3月29日 下午8:10:06
	 */
	public String deleteCatefory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1.得到界面传输来的cid
		 * 2.根据cid删除分类
		 */
		String cid = request.getParameter("cid");
		categoryService.delete(cid);
		return this.findAll(request, response);
	}
	
	/**
	 * <p>方法名	findCateforyBcid </p>
	* <p>方法描述	根据cid查询分类</p>
	* 参数 @param request
	* 参数 @param response
	* 参数 @return
	* 参数 @throws ServletException
	* 参数 @throws IOException 参数说明
	* 返回类型 String 返回类型
	* @author	裴健
	* @date		2017年3月29日 下午8:23:56
	 */
	public String findCateforyBcid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		Category category = categoryService.findCateforyBcid(cid);
		request.setAttribute("category", category);
		return "f:/adminjsps/admin/category/mod.jsp";
	}
}
