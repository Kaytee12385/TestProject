package com.training.springmvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.training.springmvc.model.Constants;
import com.training.springmvc.model.Item;
import com.training.springmvc.model.Order;
import com.training.springmvc.model.Search;
import com.training.springmvc.model.User;
import com.training.springmvc.model.UserDetails;
import com.training.springmvc.service.ItemService;
import com.training.springmvc.service.OrderService;
import com.training.springmvc.dao.ItemDAO;

import java.io.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

@Controller
public class ItemController {
	private static final Logger logger = Logger.getLogger(ItemController.class);

	public ItemController() {
		System.out.println("ItemController()");
	}

	static public void initializeLog4jXML(){
		DOMConfigurator.configure("log4j.xml");
		
		logger.info("Logger initialized");
		
		
	}
	@Autowired
	private ItemService itemService;
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(ModelAndView model, HttpServletRequest request) throws IOException {
		initializeLog4jXML();
		Search search = new Search();
		model.addObject("search", search);
		logger.info("redirecting to home");
		model.setViewName("home");
		return model;
	}

	@RequestMapping(value = "/home", method = RequestMethod.POST)
	public ModelAndView returnhome(ModelAndView model, HttpServletRequest request) throws IOException {
		Search search = new Search();
		model.addObject("search", search);
		model.setViewName("home");
		return model;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView listItem(@ModelAttribute Search search, HttpServletRequest request) throws IOException {

		HashMap<Integer, Item> items = itemService.getItemsByKeyword(search.getKeyword());
		ModelAndView mv = new ModelAndView();
		request.getSession().setAttribute(Constants.SEARCH_ITEMS, items);
		mv.addObject("searchItems", items);
        logger.info("redirecting to search page");
		
		mv.setViewName("home");
		return mv;
	}

	@RequestMapping(value = "/showItem", method = RequestMethod.GET)
	public ModelAndView showItem(HttpServletRequest request) throws IOException {
		int itemId = Integer.parseInt(request.getParameter("id"));
		Item itemValue = itemService.getItemById(itemId);
		ArrayList<Item> item = new ArrayList<>();
		item.add(itemValue);
		ModelAndView mv = new ModelAndView();
		mv.addObject("item", item);
		
		mv.setViewName("showItem");
		return mv;
	}

	@RequestMapping(value = "/addToCart", method = RequestMethod.GET)
	public ModelAndView addToCart(ModelAndView model, HttpServletRequest request) throws IOException {

		String itemId = request.getParameter("itemId");
		if (request.getSession().getAttribute(Constants.CART_ITEMS) != null) {
			ArrayList<String> Alitems = (ArrayList) request.getSession().getAttribute(Constants.CART_ITEMS);
			Alitems.add(itemId);

		} else {
			ArrayList<String> Alitems = new ArrayList<>();
			Alitems.add(itemId);
			request.getSession().setAttribute(Constants.CART_ITEMS, Alitems);

		}

		Search search = new Search();
		model.addObject("search", search);

		model.setViewName("home");
		return model;
	}

	@RequestMapping(value = "/cartItems", method = RequestMethod.GET)
	public ModelAndView cartItems(ModelAndView model, HttpServletRequest request) throws IOException {

		ArrayList<String> Alitems = (ArrayList) request.getSession().getAttribute(Constants.CART_ITEMS);
		if (Alitems != null) {
			HashMap<Integer, Item> cartItems = itemService.getItemsByItemIdList(Alitems);
			model.addObject("cartItems", cartItems);

			model.setViewName("cartItems");
			return model;
		} else {
			Search search = new Search();
			model.addObject("search", search);
			model.setViewName("home");
			return model;

		}
	}

	@RequestMapping(value = "/Login", method = RequestMethod.POST)
	public ModelAndView login(ModelAndView model) throws IOException {
		UserDetails userDetails = new UserDetails();
		model.addObject("userDetails",userDetails);
		model.setViewName("Login");
		return model;
	}

	@RequestMapping(value = "/loginRead", method = RequestMethod.POST)
	public ModelAndView loginRead(@ModelAttribute UserDetails userDetails, ModelAndView model, HttpServletRequest request) throws IOException, SQLException {
		Order order = new Order();
		ArrayList<String> Alitems = (ArrayList) request.getSession().getAttribute(Constants.CART_ITEMS);
		ArrayList<Item> items = new ArrayList<>();
		HashMap<Integer, Item> itemsMap = itemService.getItemsByItemIdList(Alitems);
		for (int i = 0; i < Alitems.size(); i++) {
			String tempitem = (String) Alitems.get(i);

			Item item = itemsMap.get(Integer.valueOf(tempitem));
			item.setITEM_QUANTITY(1);
			items.add(item);
			order.items = items;
		}
		String username = userDetails.getUserName();
		String password = userDetails.getPassword();

		long customer_id = itemService.getCustomerId(username, password);
		if (customer_id > 0) {

			long ordr_id = 0;
			try {
				ordr_id = orderService.caluculateOrderTotalPrice(order, items, customer_id);
			} catch (Throwable e) {
				e.printStackTrace();

				logger.error(e.printStackTrace());
			}

			request.getSession().removeAttribute(Constants.CART_ITEMS);
			request.getSession().setAttribute(Constants.LOGIN_ORDERID, ordr_id);
		
			model.setViewName("loginRead");
		} else {
			request.getSession().setAttribute("LoginFailed", "Invalid UserName or Password");
			String msg = (String) request.getSession().getAttribute("LoginFailed");
			logger.info(msg);
			
			model.addObject("userDetails",userDetails);
			model.addObject("msg", msg);
			model.setViewName("Login");

		}
		return model;
	}

	@RequestMapping(value = "/orderSuccess", method = RequestMethod.POST)
	public ModelAndView orderSuccess(ModelAndView model, HttpServletRequest request) throws IOException {

		long orderId = (long) request.getSession().getAttribute(Constants.LOGIN_ORDERID);
		request.getSession().removeAttribute(Constants.SEARCH_ITEMS);
		request.getSession().removeAttribute(Constants.LOGIN_FAILED);
		request.getSession().removeAttribute(Constants.LOGIN_ORDERID);
		request.getSession().removeAttribute(Constants.CART_ITEMS);
		
		
		

		model.addObject("orderId", orderId);
		model.setViewName("orderSuccess");
		logger.info("order Success");

		return model;
	}
	
	
	@RequestMapping(value = "/registrationSuccess", method = RequestMethod.POST)
	public ModelAndView registrationSuccess(@ModelAttribute User user,ModelAndView model,HttpServletResponse response) throws IOException, SQLException {
		int id=(int) orderService.getCustomerSequenceId();
		response.getWriter().println(id);
		
		orderService.insertCustomerDetails(id,user.getUsername(), user.getPassword(), user.getFirstname(), user.getLastname(), 
				user.getAge(), user.getGender(), user.getPhone(), user.getEmail());
		response.getWriter().println(user.getUsername());
		
		
		
		model.setViewName("Login");
		return model;
	
}
	@RequestMapping(value = "/newUser", method = RequestMethod.POST)
	public ModelAndView newUser(ModelAndView model, HttpServletRequest request) throws IOException {
		User user = new User();
		model.addObject("user",user);
		model.setViewName("newUser");
		return model;
	}
	
}
