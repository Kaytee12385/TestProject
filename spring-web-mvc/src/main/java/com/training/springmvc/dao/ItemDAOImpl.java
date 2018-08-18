package com.training.springmvc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.management.Query;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.training.springmvc.model.Constants;
import com.training.springmvc.model.Item;

import oracle.jdbc.driver.OracleDriver;

@Repository
public class ItemDAOImpl implements ItemDAO {

	@Autowired
	private SessionFactory sessionFactory;
	HashMap<Integer, Item> itemslist = new HashMap<>();
	HashMap<Integer, Item> cartItemsList = new HashMap<>();

	public HashMap<Integer, Item> getItemsByKeyword(String keyword) {
		itemslist.clear();
		String HQL_QUERY = " SELECT itm FROM Item itm WHERE itm.ITEM_NAME LIKE :in ";

		org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(HQL_QUERY);
		query.setParameter("in", "%" + keyword + "%");
		List<Item> itms = query.list();
		for (Item itm : itms) {
			itemslist.put(itm.getITEM_ID(), itm);
			System.out.println(itm.getITEM_ID());
		}
		return itemslist;
	}
	/*
	 * HashMap<Integer, Item> items = new HashMap<>(); Connection conn = null;
	 * PreparedStatement pst = null; ResultSet rs = null;
	 * 
	 * try { conn = getConnection(); pst = conn.prepareStatement(GET_ITEMS_SQL);
	 * 
	 * pst.setString(1, "%"+keyword+"%"); rs = pst.executeQuery();
	 * 
	 * while (rs.next()) { int item_id = rs.getInt("ITEM_ID"); String item_name =
	 * rs.getString("ITEM_NAME"); String item_desc = rs.getString("ITEM_DESC");
	 * Float item_price = (float) rs.getInt("ITEM_PRICE"); String item_is_on_sale =
	 * rs.getString("ITEM_IS_ON_SALE"); Float item_discount = (float)
	 * rs.getInt("ITEM_DISCOUNT");
	 * 
	 * Item item = new Item(); item.setITEM_ID(item_id);
	 * item.setITEM_NAME(item_name); item.setITEM_DESC(item_desc);
	 * 
	 * item.setITEM_IS_ON_SALE(item_is_on_sale);
	 * 
	 * 
	 * item.setITEM_DISCOUNT(item_discount); item.setITEM_PRICE(item_price);
	 * 
	 * 
	 * items.put(item.getITEM_ID(), item); }
	 * 
	 * } catch (SQLException sqlEx) {
	 * System.out.println("Problem while executing sql"); sqlEx.printStackTrace();
	 * 
	 * } catch (Exception ex) { System.out.println("Unknown exception.");
	 * 
	 * } finally { try { rs.close(); pst.close(); conn.close();
	 * 
	 * } catch (SQLException sqlExce) {
	 * System.out.println("Problem while closing the connection."); }
	 * 
	 * } return items; }
	 */

	public Item getItemById(Integer id) {

		String HQL_QUERY = " SELECT itm FROM Item itm WHERE itm.ITEM_ID = :id ";
		org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(HQL_QUERY);
		query.setParameter("id", id);
		List<Item> itms = query.list();

		return itms != null ? itms.get(0) : new Item();

	}

	private String createQuery(int noOfItemIds) {

		String HQL_QUERY = "SELECT itm  FROM Item itm WHERE itm.ITEM_ID IN ( ";

		StringBuilder queryBuilder = new StringBuilder(HQL_QUERY);
		for (int i = 0; i < noOfItemIds; i++) {
			queryBuilder.append(" ?");
			if (i != noOfItemIds - 1)
				queryBuilder.append(",");
		}
		queryBuilder.append(")");
		return queryBuilder.toString();
	}

	public HashMap<Integer, Item> getItemsByItemIdList(ArrayList<String> itemIds) {
		cartItemsList.clear();
		String query = createQuery(itemIds.size());
		org.hibernate.Query hquery = sessionFactory.getCurrentSession().createQuery(query);

		int count = 0;
		for (String id : itemIds) {
			hquery.setParameter(count, Integer.valueOf(id));
			count++;
		}
		List<Item> itms = hquery.list();

		for (Item itm : itms) {

			cartItemsList.put(itm.getITEM_ID(), itm);

		}
		return cartItemsList;

	}

	/*
	 * public long getCustomerId(String username, String password) {
	 * 
	 * long customer_id = 0; String HQL_QUERY =
	 * "SELECT cust.CUST_ID , cust.USER_NAME FROM Customer cust where cust.USER_NAME = :un and cust.PASSWORD = :p"
	 * ;
	 * 
	 * request.getSession().removeAttribute(Constants.SEARCH_ITEMS);
	 * org.hibernate.Query hquery =
	 * sessionFactory.getCurrentSession().createQuery(HQL_QUERY);
	 * hquery.setParameter("un", username); hquery.setParameter("p",password);
	 * 
	 * @SuppressWarnings("unchecked") List<Customer> customer =
	 * (List<Customer>)hquery.list(); for(Customer cust : customer) { customer_id=
	 * cust.getCUST_ID(); }
	 * 
	 * return customer_id;
	 * 
	 * }
	 */
	public static final String GET_CustomerID_SQL = "SELECT CUST_ID, USER_NAME FROM CUSTOMER WHERE USER_NAME = ? AND PASSWORD = ?";

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

	public long getCustomerId(String username, String password) {
		long customer_id = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			pst = conn.prepareStatement(GET_CustomerID_SQL);

			pst.setString(1, username);
			pst.setString(2, password);
			rs = pst.executeQuery();

			while (rs.next()) {
				customer_id = rs.getInt("CUST_ID");

			}

		} catch (SQLException sqlEx) {
			System.out.println("Problem while executing sql");
			sqlEx.printStackTrace();

		} catch (Exception ex) {
			System.out.println("Unknown exception.");

		} finally {
			try {
				rs.close();
				pst.close();
				conn.close();

			} catch (SQLException sqlExce) {
				System.out.println("Problem while closing the connection.");
			}

		}

		return customer_id;
	}
}
