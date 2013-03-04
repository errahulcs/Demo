/**
 * 
 */
package com.cookedspecially.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cookedspecially.domain.User;
import com.cookedspecially.service.UserService;
import com.cookedspecially.utility.MailerUtility;
import com.cookedspecially.utility.StringUtility;

/**
 * @author sagarwal
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response ) {
		return "login";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String signup(Map<String, Object> map) {
		map.put("user", new User());
		return "signup";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signup(@ModelAttribute("user") User user, BindingResult result, @RequestParam("password") String password) {
		user.setPasswordHash(userService.getHash(password));
		userService.addUser(user);
		return "signupSuccess";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("username");
		request.getSession().removeAttribute("token");
		request.getSession().removeAttribute("userId");
		return "redirect:/";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response) {
		//@RequestParam("username") String username, @RequestParam("password") String password,
		System.out.println(request.getHeader("referer"));
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		//String signup = request.getParameter("signup");
		if(!StringUtility.isNullOrEmpty(username) || !StringUtility.isNullOrEmpty(password)) {
			
			User user = userService.getUserByUsername(username);
			
			if (user != null) {
				if (userService.isValidUser(user, password))  {
					request.getSession().setAttribute("username", username);
					request.getSession().setAttribute("token", user.getPasswordHash());
					request.getSession().setAttribute("userId", user.getUserId());
					String redirectPath = (String) request.getSession().getAttribute("requestpath");
					if (!StringUtility.isNullOrEmpty(redirectPath)) {
						request.getSession().removeAttribute("requestpath");
					} else {
						redirectPath = "/";
					}
					return "redirect:"+redirectPath;
				}
			} else {
				/*if ("true".equals(signup)) {
					user = new User();
					user.setUsername(username);
					user.setPasswordHash(userService.getHash(password));
					userService.addUser(user);
					request.getSession().setAttribute("username", username);
					request.getSession().setAttribute("token", user.getPasswordHash());
					return "loginSuccess";
				}
				*/
			}
		}
		
		return "login";
	}
	
	@RequestMapping(value="/forgotPassword", method=RequestMethod.GET)
	public String getForgotPassword(HttpServletRequest request, HttpServletResponse response) {
		return "forgotPassword";
	}
	
	@RequestMapping(value="/forgotPassword", method=RequestMethod.POST)
	public String forgotPassword(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String completeUrl = request.getRequestURL().toString();
		String resetPasswordUrl = "";
		User user = userService.getUserByUsername(username);
		if (user != null) {			
			resetPasswordUrl = completeUrl.substring(0, completeUrl.lastIndexOf("/forgotPassword")) + "/resetPassword.html?userId=" + user.getUserId() + "&code=" + user.getPasswordHash();
		} else {
			
		}
		MailerUtility.sendMail(user.getUsername(), "Password Reset Email", "Hi, Please click on this url " + resetPasswordUrl );
		map.put("message", "Successfully sent the email");
		
		return "forgotPassword";
	}
	
	@RequestMapping(value="/resetPassword", method=RequestMethod.GET)
	public String resetPassword(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {
		Integer userId = Integer.parseInt(request.getParameter("userId"));
		String code = request.getParameter("code");
		User user = userService.getUser(userId);
		if (code.equals(user.getPasswordHash())) {
			map.put("userId", userId);
			map.put("code", code);
		}
		return "resetPassword";
	}
	
	@RequestMapping(value="/updatePassword", method=RequestMethod.POST)
	public String updatePassword(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {
		Integer userId = Integer.parseInt(request.getParameter("userId"));
		String code = request.getParameter("code");
		String newPassword = request.getParameter("newPassword");
		User user = userService.getUser(userId);
		if (code.equals(user.getPasswordHash())) {
			user.setPasswordHash(userService.getHash(newPassword));
			userService.addUser(user);
		}
		map.put("message", "Updated Password");
		return "updatePassword";
	}
}
