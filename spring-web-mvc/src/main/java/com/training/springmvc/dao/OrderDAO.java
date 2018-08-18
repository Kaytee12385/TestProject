package com.training.springmvc.dao;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.training.springmvc.model.Order;

public interface OrderDAO {
	
	public long insertOrder(Order order, long customerId) throws Exception;
	
	public long caluculateOrderTotalPrice(Order order, ArrayList items, long customerid) throws SQLException, Exception ;
	
	public long getCustomerSequenceId() throws SQLException ;
	
	public void insertCustomerDetails(int customer_id,String userName,String password,String firstName,String lastName,int age,String gender,int phone, String mail );

	
	

}
