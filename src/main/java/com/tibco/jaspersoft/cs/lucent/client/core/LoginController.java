package com.tibco.jaspersoft.cs.lucent.client.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/*
 * $Id: LoginController.java 222 2017-11-01 21:14:04Z jwhang $
 */

@Controller
@SessionAttributes("name")
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String showLoginPage(ModelMap model){
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String showMainPage(ModelMap model, @RequestParam String userName, @RequestParam String password){
		boolean isValidUser = loginService.verifyLogin(userName, password);
		
		if (!isValidUser){
			model.put("errorMessage", "Invalid Login");
			return "login";
		}
		
		model.put("name", userName);
		
		return "mainPage";
	}
	
}


