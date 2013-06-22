/**
 * 
 */
package com.cookedspecially.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;

import com.cookedspecially.config.CSConstants;
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
	
	private static int MAXFILESIZE=5; //in MB
	
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
	public String signup(@ModelAttribute("user") User user, HttpServletRequest request, BindingResult result, @RequestParam("password") String password) {
		user.setPasswordHash(userService.getHash(password));
		User existingUser = userService.getUserByUsername(user.getUsername());
		if (existingUser != null) {
			result.rejectValue("username", "error.username.exists");
			return "signup";
		} else {
			userService.addUser(user);
			userLogin(request, user.getUsername(), password);
		}
		return "signupSuccess";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute(CSConstants.USERNAME);
		request.getSession().removeAttribute(CSConstants.TOKEN);
		request.getSession().removeAttribute("userId");
		return "redirect:/";
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public String editUser(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {
		Object userIdObj = request.getSession().getAttribute("userId");
		if(userIdObj != null) {
			map.put("user", userService.getUser((Integer) userIdObj));
			return "editUser";
		}
		return "redirect:/";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateUser(Map<String, Object> map, @ModelAttribute("user")
	User user, BindingResult result, @RequestParam("files[0]") MultipartFile portraitImage, @RequestParam("files[1]") MultipartFile landscapeImage) {
		FileOutputStream fos = null;
		ArrayList<MultipartFile> files = new ArrayList<MultipartFile>();
		files.add(portraitImage);
		files.add(landscapeImage);
		if (files != null && files.size() == 2) {
			String[] fileUrls = new String[2];
			int iter = 0;
			for (MultipartFile file : files) {
				String fileUrl = (iter==0)?user.getBusinessPortraitImageUrl():user.getBusinessLandscapeImageUrl();
				if (!file.isEmpty()) {
					if (file.getSize() > MAXFILESIZE*1000*1000) {
						result.rejectValue(((iter == 0)?"businessPortraitImageUrl":"businessLandscapeImageUrl"), "error.upload.sizeExceeded", "You cannot upload the file of more than " + MAXFILESIZE + " MB");
						map.put("user", user);
						return "editUser";
					}
					try {
						byte[] bytes = file.getBytes();
						
						//System.out.println(file.getOriginalFilename());
						//System.out.println(file.getContentType());
						String fileDir = File.separator + "static" + File.separator + user.getUserId() + File.separator ;
						fileUrl = fileDir + ((iter==0)?"portrait":"landscape") + "_" + file.getOriginalFilename().replaceAll("[^a-zA-Z0-9_.]", "_");
						fileUrls[iter] = fileUrl;
						File dir = new File("webapps" + fileDir);
						if (!dir.exists()) { 
							dir.mkdirs();
						}
						File outfile = new File("webapps" + fileUrl); 
						//System.out.println(outfile.getAbsolutePath());
						fos = new FileOutputStream(outfile);
						fos.write(bytes);
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						if (fos != null) {
							try {
								fos.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
				iter++;
			}
			
			for (iter = 0; iter < 2; iter++) {
				String existingImageUrl = (iter==0)?user.getBusinessPortraitImageUrl():user.getBusinessLandscapeImageUrl();
				String fileUrl = fileUrls[iter];
				if(!StringUtility.isNullOrEmpty(fileUrl)) {
					if (!fileUrl.equals(existingImageUrl) && existingImageUrl.startsWith("/")) {
						File oldFile = new File("webapps" + existingImageUrl);
						if (oldFile.exists()) {
							oldFile.delete();
						}
					}
					if (iter == 0) {
						user.setBusinessPortraitImageUrl(fileUrl);
					} else {
						user.setBusinessLandscapeImageUrl(fileUrl);
					}
				}
			}
		}
		userService.addUser(user);    
		return "redirect:/";
	}

	private String userLogin(HttpServletRequest request, String username, String password) {
		String returnPath = null;
		
		//String signup = request.getParameter("signup");
		if(!StringUtility.isNullOrEmpty(username) || !StringUtility.isNullOrEmpty(password)) {
			
			User user = userService.getUserByUsername(username);
			
			if (user != null) {
				if (userService.isValidUser(user, password))  {
					request.getSession().setAttribute(CSConstants.USERNAME, username);
					request.getSession().setAttribute(CSConstants.TOKEN, user.getPasswordHash());
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
		return returnPath;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response) {
		//@RequestParam("username") String username, @RequestParam("password") String password,
		System.out.println(request.getHeader("referer"));
		String username = request.getParameter(CSConstants.USERNAME);
		String password = request.getParameter(CSConstants.PASSWORD);
		
		String returnPath = userLogin(request, username, password);
		if (!StringUtility.isNullOrEmpty(returnPath)) {
			return returnPath;
		}
		return "login";
	}
	
	@RequestMapping(value="/forgotPassword", method=RequestMethod.GET)
	public String getForgotPassword(HttpServletRequest request, HttpServletResponse response) {
		return "forgotPassword";
	}
	
	@RequestMapping(value="/forgotPassword", method=RequestMethod.POST)
	public String forgotPassword(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter(CSConstants.USERNAME);
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
