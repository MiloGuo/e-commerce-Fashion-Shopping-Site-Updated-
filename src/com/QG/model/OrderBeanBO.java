
package com.sp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class OrderBeanBO {


	private ResultSet rs=null;
	private Connection ct=null;
	private PreparedStatement ps=null;
	
	
	
	
	
	public OrderInfoBean addOrder(MyCartBO mbo,String userId){
		
		OrderInfoBean oib=new OrderInfoBean();
		boolean b=true;
		try {
			
			
			ct=new ConnDB().getConn();
			ps=ct.prepareStatement("insert into orders (userId,isPayed,totalPrice)  values(?,?,?)");
			ps.setString(1, userId);
			ps.setByte(2, (byte)0);
			ps.setFloat(3, mbo.getAllPrice());
			
			
			int a=ps.executeUpdate();
			
			if(a==1){
				
				
				
				
				ps=ct.prepareStatement("select max(ordersId) from orders");
				
				rs=ps.executeQuery();
				
				int orderId=0;
				if(rs.next()){
					
					orderId=rs.getInt(1);
					
				}
				
				
				ArrayList al=mbo.showMyCart();
				
			
				Statement sm=ct.createStatement();
				for(int i=0;i<al.size();i++){
					
					GoodsBean gb=(GoodsBean)al.get(i);
					sm.addBatch("insert into orderDetail values('"+orderId+"','"+gb.getGoodsId()+"','"+mbo.getGoodsNumById(gb.getGoodsId()+"")+"')");
					
				}
				
				
				sm.executeBatch();
				
				
				String sql="select ordersId ,truename,address,postcode,phone,totalPrice,username,email from users,orders"+ 
				" where ordersId=? and users.userid = (select orders.userid from orders where ordersId=?)";
				
				
				ps=ct.prepareStatement(sql);
				ps.setInt(1,orderId);
				ps.setInt(2,orderId );
				rs=ps.executeQuery();
				
				if(rs.next()){
					
				
					oib.setOrdersId(rs.getInt(1));
					oib.setTruename(rs.getString(2));
					oib.setAddress(rs.getString(3));
					oib.setPostcode(rs.getString(4));
					oib.setPhone(rs.getString(5));
					oib.setTotalPrice(rs.getFloat(6));
					oib.setUsername(rs.getString(7));
					oib.setEmail(rs.getString(8));
					
				}
				
			}
			
		} catch (Exception e) {
			b=false;
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			
			
			this.close();
		}
		if(b){
		return oib;
		}else{
			return null;
		}
	}
	
	

	
	public void close(){
		
		try {
			
			if(rs!=null){
				
				rs.close();
				rs=null;//
			}
			if(ps!=null){
				
				ps.close();
				ps=null;
			}
			
			if(!ct.isClosed()){
				
				ct.close();
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
}
