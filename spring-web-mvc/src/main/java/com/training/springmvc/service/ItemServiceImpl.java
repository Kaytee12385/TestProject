package com.training.springmvc.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.training.springmvc.model.Item;

import com.training.springmvc.dao.ItemDAO;


@Service
@Transactional
public class ItemServiceImpl implements ItemService{

		@Autowired
		private ItemDAO itemDAO;

		@Override
		public HashMap<Integer, Item> getItemsByKeyword(String keyword) {
			return itemDAO.getItemsByKeyword(keyword);
		}
		
		public Item  getItemById(Integer id) {
			return itemDAO.getItemById(id);
		}
		
		public HashMap<Integer, Item> getItemsByItemIdList(ArrayList<String> itemIds){
			return itemDAO.getItemsByItemIdList(itemIds);
			
		}
		
		public long getCustomerId(String username, String password) {
			return itemDAO.getCustomerId(username, password);
		}
		


}
