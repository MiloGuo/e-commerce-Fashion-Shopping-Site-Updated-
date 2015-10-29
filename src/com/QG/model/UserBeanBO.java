
package com.sp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserBeanBO {
	

	private ResultSet rs=null;
	private Connection ct=null;
	private PreparedStatement ps=null;


	
	public UserBean getUserBean(String u){
		
		UserBean ub=new UserBean();
		try {
			
			
			ct=new ConnDB().getConn();
			ps=ct.prepareStatement("select top 1 * from users where username=?");
			ps.setString(1, u);
			rs=ps.executeQuery();
			if(rs.next()){
				
				ub.setUserid(rs.getLong(1));
				ub.setUsername(rs.getString(2));
				ub.setTruename(rs.getString(3));
				ub.setPasswd(rs.getString(4));
				ub.setEmail(rs.getString(5));
				ub.setPhone(rs.getString(6));
				ub.setAddress(rs.getString(7));
				ub.setPostcode(rs.getString(8));
				ub.setGrade(rs.getInt(9));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally{
			this.close();
		}
		
		return ub;
	}
	

	
	public boolean checkUser(String u,String p){
		
		
		boolean b=false;
		
		
		try {
			
			
			
			ct=new ConnDB().getConn();
			
		
			ps=ct.prepareStatement("select top 1 passwd from users where username=?");
			
			ps.setString(1, u);
			
			rs=ps.executeQuery();
			
			if(rs.next()){
				
			
				String dbPasswd=rs.getString(1);
				
				if(dbPasswd.equals(p)){
					
					b=true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			
		
			this.close();
		}
		
		return b;
		
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
