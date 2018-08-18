package com.training.springmvc.service;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.training.springmvc.model.Item;

public interface ItemService {
	
	public HashMap<Integer, Item> getItemsByKeyword(String keyword) ;
	public Item getItemById(Integer id);
	public HashMap<Integer, Item> getItemsByItemIdList(ArrayList<String> itemIds);
	public long getCustomerId(String username, String password) ;

}
