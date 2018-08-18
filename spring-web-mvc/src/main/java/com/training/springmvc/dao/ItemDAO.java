package com.training.springmvc.dao;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.training.springmvc.model.Item;

public interface ItemDAO {
	
	public HashMap<Integer, Item> getItemsByKeyword(String keyword);
	public Item getItemById(Integer id);
	public HashMap<Integer, Item> getItemsByItemIdList(ArrayList<String> itemIds);
	public long getCustomerId(String username, String password);

}
