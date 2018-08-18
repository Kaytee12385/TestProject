package com.training.springmvc.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.training.springmvc.model.Order;

public interface OrderService {
	
	public long insertOrder(Order order, long customerId) throws Throwable;
	public long caluculateOrderTotalPrice(Order order, ArrayList items, long customerid) throws SQLException, Throwable ;
	
    public long getCustomerSequenceId() throws SQLException ;
	
	public void insertCustomerDetails(int customer_id,String userName,String password,String firstName,String lastName,int age,String gender,int phone, String mail );

	

}
