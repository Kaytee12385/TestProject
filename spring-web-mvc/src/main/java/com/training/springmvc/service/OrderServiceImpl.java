package com.training.springmvc.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.training.springmvc.dao.ItemDAO;
import com.training.springmvc.dao.OrderDAO;
import com.training.springmvc.model.Order;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderDAO orderDAO;
	public long insertOrder(Order order, long customerId) throws Throwable {
		return orderDAO.insertOrder(order, customerId);
	}
	
	public long caluculateOrderTotalPrice(Order order, ArrayList items, long customerid) throws Throwable {
		return orderDAO.caluculateOrderTotalPrice(order, items, customerid);
	}
	public long getCustomerSequenceId() throws SQLException {
		return orderDAO.getCustomerSequenceId();
	
		
	}

	public void insertCustomerDetails(int customer_id, String userName, String password, String firstName,
			String lastName, int age, String gender, int phone, String mail) {
		}
	

}
