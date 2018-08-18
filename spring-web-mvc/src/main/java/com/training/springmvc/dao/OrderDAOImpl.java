package com.training.springmvc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.management.Query;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.training.springmvc.model.Item;

import com.training.springmvc.model.Order;

import oracle.jdbc.driver.OracleDriver;



@Repository
public class OrderDAOImpl implements OrderDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	long orderId = 0;
	public static final String GET_SEQ_ID = "SELECT ORDER_DETAILS_SEQ.nextval ORDER_ID FROM DUAL";
	public static final String GET_CUST_ID = "SELECT cust_sequence.nextval CUST_ID FROM DUAL";
	public static final String INSERT_ORDER_SQL = "INSERT INTO ORDER_DETAILS(ORDER_ID, CUST_ID, TOTAL_PRICE) VALUES(? , ?, ?)";
	public static final String INSERT_ORDER_ITEM = "INSERT INTO ORDER_ITEM(ORDER_ID,ITEM_ID,ITEM_PRICE,QUANTITY,TOTAL_PRICE) VALUES (?,?,?,?,?)";
	public static final String CUST_SQL = "INSERT INTO CUSTOMER(CUST_ID,USER_NAME,PASSWORD,FIRST_NAME,LAST_NAME,AGE,GENDER,PHONE,EMAIL) VALUES(? ,?,?,?,?, ?,?, ?,?)";

	private static Connection getConnection() throws Exception {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String username = "SYSTEM";
		String password = "SYSADMIN";
		DriverManager.registerDriver(new OracleDriver());
		Connection conn = DriverManager.getConnection(url, username, password);
		conn.setAutoCommit(false);

		return conn;
	}


	public long getOrderDeatilsSequenceId() throws SQLException {

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try {

			conn = getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(GET_SEQ_ID);

			while (rs.next()) {
				orderId = rs.getInt("ORDER_ID");

			}
			
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			conn.rollback();
		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			try {
				rs.close();
				st.close();
				conn.close();
			} catch (Exception ex) {

			}
		}

		return orderId;
	}
	public void insertOrderDetails(long orderId, long custID, Order order) {

		Connection conn = null;
		PreparedStatement pst = null;

		try {
			conn = getConnection();
			pst = conn.prepareStatement(INSERT_ORDER_SQL);

			pst.setLong(1, orderId);
			pst.setDouble(2, custID);
			pst.setDouble(3, order.totalPrice);
			pst.executeUpdate();

			conn.commit();

		} catch (SQLException sqlEx) {
			System.out.println("Problem while executing sql");
			sqlEx.printStackTrace();

		} catch (Exception ex) {
			System.out.println("Unknown exception.");

		} finally {
			try {

				pst.close();
				conn.close();

			} catch (SQLException sqlExce) {
				System.out.println("Problem while closing the connection.");
			}

		}

	}

	public void insertOrderItem(long orderId, Order order) {

		Connection conn = null;
		PreparedStatement pst = null;

		try {
			conn = getConnection();
			pst = conn.prepareStatement(INSERT_ORDER_ITEM);

			for (Item itm : order.items) {

				int item_id = itm.getITEM_ID();
				float item_price = itm.getITEM_PRICE();
				int item_quantity = itm.getITEM_QUANTITY();
				double total_price = itm.getTOTAL_PRICE();

				pst.setLong(1, orderId);
				pst.setInt(2, item_id);
				pst.setDouble(3, item_price);
				pst.setInt(4, item_quantity);
				pst.setDouble(5, total_price);
				pst.executeUpdate();

				
			}
			conn.commit();

		} catch (SQLException sqlEx) {
			System.out.println("Problem while executing sql");
			sqlEx.printStackTrace();

		} catch (Exception ex) {

			System.out.println("Unknown exception.");

		} finally {
			try {

				pst.close();
				conn.close();

			} catch (SQLException sqlExce) {
				System.out.println("Problem while closing the connection.");
			}

		}

	}

	public long insertOrder(Order order, long customerId) throws Exception {
		long ordr_id = getOrderDeatilsSequenceId();
		
		insertOrderDetails(ordr_id, customerId, order);
		insertOrderItem(ordr_id, order);
		return ordr_id;

	}

	@Override
	public long caluculateOrderTotalPrice(Order order, ArrayList items, long customerid) throws Exception {
		
            OrderDAOImpl ordr= new OrderDAOImpl();
		   for (int i = 0; i < items.size(); i++) {
		      Item tempitem = (Item) items.get(i);
		      
		     
		     
		     if(tempitem.getITEM_IS_ON_SALE().equals("Y")) {
		    
		    	 double price1 = (double) ((tempitem.getITEM_QUANTITY()) * ((tempitem.getITEM_PRICE())) * (100 - tempitem.getITEM_DISCOUNT())) / 100;
		    	 System.out.println("individual order price: "+price1);
	              tempitem.setTOTAL_PRICE(price1);
		    	 
		    	 order.totalPrice= order.totalPrice+tempitem.getTOTAL_PRICE();
		    	 }
		    	 else {
		    		 double price1 = (double) ((tempitem.getITEM_QUANTITY()) * (tempitem.getITEM_PRICE()));
			    	 System.out.println("individual order price: "+price1);
		                tempitem.setTOTAL_PRICE(price1);
			    	 
			    	 order.totalPrice=order.totalPrice+tempitem.getTOTAL_PRICE();
		    		 
		    	 }
		   }
		   
		   long ordr_id = ordr.insertOrder(order,customerid);
		   return ordr_id;
		     
		    	 
}
	public long getCustomerSequenceId() throws SQLException {
         int customer_id=0;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try {

			conn = getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(GET_CUST_ID);

			while (rs.next()) {
				 customer_id = rs.getInt("CUST_ID");

			}
			
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			conn.rollback();
		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			try {
				rs.close();
				st.close();
				conn.close();
			} catch (Exception ex) {

			}
		}

		return customer_id;
	}
//CUST_ID,USER_NAME,PASSWORD,FIRST_NAME,LAST_NAME,AGE,GENDER,PHONE,EMAIL
	public void insertCustomerDetails(int customer_id,String userName,String password,String firstName,String lastName,int age,String gender,int phone, String mail ) {

		Connection conn = null;
		PreparedStatement pst = null;

		try {
			conn = getConnection();
			pst = conn.prepareStatement(INSERT_ORDER_SQL);

			pst.setLong(1, customer_id);
			pst.setString(2, userName );
			pst.setString(3, password);
			pst.setString(4, firstName);
			pst.setString(5,lastName);
			pst.setInt(6,age);
			pst.setString(7,gender);
			pst.setInt(8,phone);
			pst.setString(9,mail);
			
			pst.executeUpdate();

			conn.commit();

		} catch (SQLException sqlEx) {
			System.out.println("Problem while executing sql");
			sqlEx.printStackTrace();

		} catch (Exception ex) {
			System.out.println("Unknown exception.");

		} finally {
			try {

				pst.close();
				conn.close();

			} catch (SQLException sqlExce) {
				System.out.println("Problem while closing the connection.");
			}

		}
	
	
	}
}




