package com.monkey.bookstore.web.adminservlet;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import sun.java2d.cmm.kcms.CMM;
import cn.itcast.commons.CommonUtils;

import com.monkey.bookstore.po.Book;
import com.monkey.bookstore.po.Category;
import com.monkey.bookstore.service.BookService;
import com.monkey.bookstore.service.CategoryService;
import com.monkey.bookstore.web.servlet.BookServlet;

/**
 * 后台管理员添加图书
 */
@WebServlet("/admin/AdminAddBookServlet")
public class AdminAddBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookService bookService = new BookService();
	private CategoryService categoryService = new CategoryService();
	private Book book =null;
	/**
	 * 处理上传的数据（文件表单项和普通表单项）
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//得到文件存放的地址
		String realPath = this.getServletContext().getRealPath("/book_img");//得到项目中/book_img在硬盘上的路径。
		/*
		 * 一、封装表单数据为book对象。
		 */
		/*
		 * 上传三部曲，
		 * 1、创建工厂
		 * 2、通过工厂得到解析器，
		 * 3、通过解析器得到表单项FileItem
		 */
		
		//创建工厂
		DiskFileItemFactory factory = new DiskFileItemFactory(20*1024, new File(realPath));
		//得到解析器
		ServletFileUpload upload = new ServletFileUpload(factory);
		//单个上传文件大小限制
		upload.setFileSizeMax(20*1024);
		
		try {
			//解析器得到表单
			List<FileItem> fileItemList = upload.parseRequest(request);
			//将表单信息存放到map中
			Map<String, String> map = new HashMap<String, String>();
			for (FileItem fileItem : fileItemList) {
				//判断内容是否是普通表单
				if (fileItem.isFormField()) {
					//getFieldName得到普通表单项的name,getString得到普通表单项的内容
					map.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
				}
			}
			//得到表单的book信息
			book = CommonUtils.toBean(map, Book.class);
			book.setBid(CommonUtils.uuid());
			//表单中的cid封装到category对象
			Category category =CommonUtils.toBean(map, Category.class);
			book.setCategory(category);
			
			
			/*
			 * 二、得到上传的图片
			 * 	1.给文件处理重名问题
			 * 	2.检验文件的扩展名（不符，给页面提示错误信息）
			 * 	3.将文件保存到本地
			 * 	4.得到本地文件，校验图片的尺寸，（尺寸不符，删除文件，给页面提示错误信息）
			 * 	5.book对象添加上传的图片地址。
			 * 	6.向数据库添加book对象
			 * 	7.转发到页面，显示所有图书列表
			 */
			//得到上传的文件
			FileItem bookImage = fileItemList.get(1);
			//得到文件表单项的文件名添加前缀以防止重名
			String bookImageName = CommonUtils.uuid()+"_"+bookImage.getName();
			
			/*
			 * 校验图片扩展名
			 */
			if (!bookImageName.toLowerCase().endsWith(".jpg")) {//查看文件的后缀是否是.jpg
				request.setAttribute("book", book);
				//不是，保存错误信息到添加图书的界面
				request.setAttribute("msg","*您添加的图片不是.jpg格式的");
				//查询所有分类，用于回显
				request.setAttribute("categoryList",categoryService.findAll());
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);
			}
			
			
			//保存到本地(使用地址+文件名)创建文件路径（注：此时硬盘并没有文件写入）
			File file = new File(realPath, bookImageName);
			bookImage.write(file);//将文件写入
			
			/*
			 * 校验图片尺寸
			 */
			Image image = new ImageIcon(file.getAbsolutePath()).getImage();
			if(image.getWidth(null) > 150 || image.getHeight(null) > 150) {
				file.delete();//删除这个文件！
				request.setAttribute("book", book);
				request.setAttribute("msg", "*您上传的图片尺寸超出了150 x 150！");
				request.setAttribute("categoryList", categoryService.findAll());
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
						.forward(request, response);
				return;
			}
			
			//书中的图片地址保存到书中
			book.setImage("book_img/"+bookImageName);
			
			/*
			 * 校验book对象的内容
			 * 
			 */
			if (book.getBname()==null||book.getBname().equals("")) {
				request.setAttribute("book", book);
				request.setAttribute("msg", "*请输入书名！");
				request.setAttribute("categoryList", categoryService.findAll());
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
						.forward(request, response);
			}
			if (book.getPrice()==0.0) {
				request.setAttribute("book", book);
				request.setAttribute("msg", "*请给图书定价！");
				request.setAttribute("categoryList", categoryService.findAll());
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
				.forward(request, response);
			}
			if (book.getAuthor().equals("")) {
				request.setAttribute("book", book);
				request.setAttribute("msg", "*您的图书没有作者！");
				request.setAttribute("categoryList", categoryService.findAll());
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);
			}
			//调用图书的业务层，添加图书
			bookService.addBook(book);
			//调用添加图书的servlet的显示所有图书方法。
			request.getRequestDispatcher("/admin/AdminBookServlet?method=findAllBook").forward(request, response);
		} catch (Exception e) {
			if (e instanceof FileUploadBase.FileSizeLimitExceededException) {
				request.setAttribute("book", book);
				request.setAttribute("msg", "*您上传的文件超出20KB");
				//查询所有分类，用于回显
				request.setAttribute("categoryList",categoryService.findAll());
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);
			}
		}
	}

}
