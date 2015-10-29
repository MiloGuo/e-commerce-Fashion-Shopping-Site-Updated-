package com.sp.serlvet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sp.model.*;
public class ShoppingClServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		
		
		
		String type=request.getParameter("type");
		
		MyCartBO mbo=(MyCartBO)request.getSession().getAttribute("mycart");
		
		if(mbo==null){
			
			
			mbo=new MyCartBO();
			request.getSession().setAttribute("mycart", mbo);
		}
		
		if(type.equals("addGoods")){
		
		
	
		String goodsId=request.getParameter("goodsId");
		
	
		
		
		
		
		mbo.addGoods(goodsId, "1");
		
		
		}else if(type.equals("delGoods")){
			
			
			
			String goodsId=request.getParameter("goodsId");
			
			
			mbo.delGoods(goodsId);
		}else if(type.equals("show")){
			
		}else if(type.equals("delAll")){
			
			mbo.clear();
		}else if(type.equals("updateGoods")){
			
			
			
			String goodsId[]=request.getParameterValues("goodsId");
			String newNums[]=request.getParameterValues("newNums");
			
			
			for(int i=0;i<goodsId.length;i++){
				
				System.out.println("id==="+goodsId[i]+"  数量="+newNums[i]);
			
				mbo.upGoods(goodsId[i], newNums[i]);
			}
			
			
			
		}
		
	
		
		ArrayList al=mbo.showMyCart();
		
		
		request.setAttribute("mycartinfo", al);
		
		request.getRequestDispatcher("showMycart.jsp").forward(request, response);
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
