
package com.sp.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
public class MyCartBO {

	
	HashMap<String,String> hm=new HashMap<String,String>();
	

	private ResultSet rs=null;
	private Connection ct=null;
	private PreparedStatement ps=null;
	
	
	private float allPrice=0.0f;
	
	
	public float getAllPrice(){
		
		return this.allPrice;
	}
	
	

	 */
	
	
	public String getGoodsNumById(String goodsId){
		
		return (String)hm.get(goodsId);
	}
	
	
	public void addGoods(String goodsId,String goodsNum){
		
		hm.put(goodsId, goodsNum);
	}
	

	public void delGoods(String goodsId){
		
		hm.remove(goodsId);
	}
	
	

	
	public void clear(){
		
		hm.clear();
	}
	
	public void upGoods(String goodsId,String newNum){
		
		hm.put(goodsId, newNum);
	}
	
	public ArrayList showMyCart(){
		
		ArrayList <GoodsBean> al=new ArrayList <GoodsBean>();
		
		try {
			
		
			
			String sql="select * from goods where goodsId in";//(1,4)
			
		
			Iterator it=hm.keySet().iterator();
			
			String sub="(";
			while(it.hasNext()){
				
				//取出goodsId
				String goodsId=(String)it.next();
				//判断goodsId是不是最后id
				if(it.hasNext()){
				sub+=goodsId+",";
				}else{
					
					sub+=goodsId+")";
				}
			}
			
			sql+=sub;
			
			ct=new ConnDB().getConn();
			
			ps=ct.prepareStatement(sql);
			
			rs=ps.executeQuery();
			
		
			this.allPrice=0.0f;
			while(rs.next()){
				
				
				GoodsBean gb=new GoodsBean();
				int goodsId=rs.getInt(1);
				gb.setGoodsId(goodsId);
				gb.setGoodsName(rs.getString(2));
				gb.setGoodsIntro(rs.getString(3));
				float unit=rs.getFloat(4);
				gb.setGoodsPrice(unit);
				gb.setGoodsNum(rs.getInt(5));
				gb.setPublisher(rs.getString(6));
				gb.setPhoto(rs.getString(7));
				gb.setType(rs.getString(8));
				
				this.allPrice=this.allPrice+unit*Integer.parseInt(this.getGoodsNumById(goodsId+""));
				
				al.add(gb);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			this.close();
			
		}
		
		return al;
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
