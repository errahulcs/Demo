/**
 * 
 */
package com.cookedspecially.interceptors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cookedspecially.domain.User;
import com.cookedspecially.service.UserService;
import com.cookedspecially.utility.StringUtility;

/**
 * @author sagarwal
 *
 */
public class AdminInterceptors implements HandlerInterceptor {

	@Autowired
	private UserService userService;
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		System.out.println("Entering pre handle");
		String username = (String) request.getSession().getAttribute("username");
		String token = (String) request.getSession().getAttribute("token");
		String base = request.getContextPath();
		String uri = request.getRequestURI();
		uri = uri.replaceAll(base, "");
		
		String redirect = "/CookedSpecially/user/login";
		boolean valid = true;
		if (StringUtility.isNullOrEmpty(username) || StringUtility.isNullOrEmpty(token)) {
			valid = false;
		} else {
			User user = userService.getUserByUsername(username);
			if (user == null) {
				valid = false;
			} else {
				if (!token.equals(user.getPasswordHash())) {
					valid = false;
				}
			}
		}
		
		if (!valid) {
			request.getSession().setAttribute("requestpath", uri);
			response.sendRedirect(redirect);
			return false;
		}
		return true;
	}

}
