/**
 * 
 */
package com.cookedspecially.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cookedspecially.config.CSConstants;
import com.cookedspecially.domain.Dish;
import com.cookedspecially.domain.Menu;
import com.cookedspecially.domain.MenuWrapper;
import com.cookedspecially.domain.Menus;
import com.cookedspecially.domain.Section;
import com.cookedspecially.domain.User;
import com.cookedspecially.enums.Status;
import com.cookedspecially.service.DishService;
import com.cookedspecially.service.MenuService;
import com.cookedspecially.service.SectionService;
import com.cookedspecially.service.UserService;
import com.cookedspecially.utility.ImageUtility;
import com.cookedspecially.utility.StringUtility;

/**
 * @author sagarwal
 *
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;
	
	@Autowired
	private DishService dishService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SectionService sectionService;
	
	@RequestMapping("/")
	public String listMenus(Map<String, Object> map, HttpServletRequest request) {

		//map.put("menu", new Menu());
		//System.out.println(request.getSession().getAttribute("userId"));
		List<Menu> menus = menuService.listMenuByUser((Integer) request.getSession().getAttribute("userId"));
		map.put("menuList", menus);
		//map.put("dishList", dishService.listDishByUser((Integer) request.getSession().getAttribute("userId")));
		return "listMenu";
	}

		
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public String createMenu(Map<String, Object> map, HttpServletRequest request) {

		map.put("menu", new Menu());
		map.put("statusTypes", Status.values());
		map.put("dishList", dishService.listDishByUser((Integer) request.getSession().getAttribute("userId")));
		return "newMenu";
	}
	@RequestMapping("/edit/{menuId}")
	public String editMenu(Map<String, Object> map, HttpServletRequest request, @PathVariable("menuId") Integer menuId) {

		Menu menu = menuService.getMenu(menuId);
		map.put("menu", menu);
		//System.out.println(request.getSession().getAttribute("userId"));
		//List<Menu> menus = menuService.listMenuByUser((Integer) request.getSession().getAttribute("userId"));
		//map.put("menuList", menus);
		/*
		List<Dish> dishes = menu.getDishes();
		HashMap<Integer, Integer> existingDishIds = new HashMap<Integer,Integer>((dishes != null) ? dishes.size(): 0);
		if(dishes != null) {
			for (Dish dish: dishes) {
				existingDishIds.put(dish.getDishId(), 1);
			}
		}
		map.put("existingDishIds", existingDishIds);
		*/
		map.put("statusTypes", Status.values());
		map.put("dishList", dishService.listDishByUser((Integer) request.getSession().getAttribute("userId")));
		return "newMenu";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addMenu(@ModelAttribute("menu")
	Menu menu, BindingResult result, @RequestParam("dishIds") Integer[] dishIds) {
		ArrayList<Dish> dishes = new ArrayList<Dish>();
		if (dishIds != null && dishIds.length > 0) {
			List<Dish> dishesArr = dishService.getDishes(dishIds);
			HashMap<Integer, Dish> dishesMap = new HashMap<Integer, Dish>();
			
			if (dishesArr != null) {
				for (Dish dish: dishesArr) {
					dishesMap.put(dish.getDishId(), dish);
				}
			}
			for (Integer dishId : dishIds) {
				Dish dish = dishesMap.get(dishId);
				if (dish != null) {
					dishes.add(dish);
				}
			}
		}
		//menu.setDishes(dishes);
		menuService.addMenu(menu);

		return "redirect:/menu/";
	}

	@RequestMapping(value = "/addNew", method = RequestMethod.POST)
	public String addNewMenu(@ModelAttribute("menu")
	Menu menu, BindingResult result, @RequestParam("file") MultipartFile file) {
		//System.out.println(menu);
		FileOutputStream fos = null;
		String fileUrl = menu.getImageUrl();
		if (!file.isEmpty()) {
            try {
				byte[] bytes = file.getBytes();
				//System.out.println(file.getOriginalFilename());
				//System.out.println(file.getContentType());
				String fileDir = File.separator + "static" + File.separator + menu.getUserId() + File.separator ;
				fileUrl = fileDir + menu.getMenuId() + "_menu_" + file.getOriginalFilename().replaceAll("[^a-zA-Z0-9_.]", "_");
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
            // store the bytes somewhere
           //return "uploadSuccess";
       } else {
           //return "uploadFailure";
       }
		if (!fileUrl.equals(menu.getImageUrl()) && menu.getImageUrl().startsWith("/")) {
			File oldFile = new File("webapps" + menu.getImageUrl());
			if (oldFile.exists()) {
				oldFile.delete();
			}
		}
		menu.setImageUrl(fileUrl);
		menu.setRestaurantId(menu.getUserId());
		Set<String> dishIds = new HashSet<String>();
		List<Section> menuSections = menu.getSections();
		TreeMap<Integer, Section> sectionTree = new TreeMap<Integer, Section>();
		List<Integer> removedSections = new ArrayList<Integer>();
		if (menuSections != null && menuSections.size() > 0) {
			for (Section menuSection : menuSections) {
				if (menuSection.isValid() && !StringUtility.isNullOrEmpty(menuSection.getDishIds())) {
					sectionTree.put(menuSection.getPosition(), menuSection);
					String[] dishIdsStrArr = menuSection.getDishIds().split(CSConstants.COMMA);
					if (dishIdsStrArr != null) {
						dishIds.addAll(Arrays.asList(dishIdsStrArr));
					}
				}
				if (!menuSection.isValid() && menuSection.getSectionId() != null && menuSection.getSectionId() > 0) {
					removedSections.add(menuSection.getSectionId());
				}
			}
		}
		Integer[] dishIdsArr = new Integer[dishIds.size()];
		
		HashMap<Integer, Dish> dishMap = new HashMap<Integer, Dish>();
		int dishCounter = 0;
		for (String dishId: dishIds) {
			dishIdsArr[dishCounter++] = Integer.parseInt(dishId);
		}
		
		if (dishIds.size() > 0) {
			List<Dish> dishes = dishService.getDishes(dishIdsArr);
			
			for (Dish dish : dishes) {
				dishMap.put(dish.getDishId(), dish);
			}	
		}
		
		ArrayList<Section> finalSections = new ArrayList<Section>();
		for (Integer key: sectionTree.keySet()) {
			finalSections.add(sectionTree.get(key));
		}
		//Iterator<Entry<Integer, Section>> sectionIterator = sectionTree.entrySet().iterator();
		//while(sectionIterator.hasNext()) {
		//	finalSections.add(sectionIterator.next().getValue());
		//}
		for (Section section : finalSections) {
			ArrayList<Dish> finalDishes = null;
			if (!StringUtility.isNullOrEmpty(section.getDishIds())) {
				String[] dishIdsStrArr = section.getDishIds().split(CSConstants.COMMA);
				if (dishIdsStrArr != null) {
					finalDishes = new ArrayList<Dish>();
					for (int i = 0; i < dishIdsStrArr.length; i++) {
						finalDishes.add(dishMap.get(Integer.parseInt(dishIdsStrArr[i])));
					} 
				}
			}
			section.setDishes(finalDishes);
			//sectionService.addSection(section);
		}
		menu.setSections(finalSections);
		Integer menuId = menu.getMenuId();
		if (menuId != null && menuId > 0) {
			menu.setModifiedTime(new Date());
		} 
		menuService.addMenu(menu);
		if (fileUrl != null && fileUrl.contains("null_")) {
			String newFileUrl = renameFileToHaveMenuId(fileUrl, menu.getMenuId());
			menu.setImageUrl(newFileUrl);
			menuService.addMenu(menu);
		}
		if (removedSections.size() > 0) {
			sectionService.removeSections(removedSections);
		}
		
		return "redirect:/menu/";
	}
	
	@RequestMapping("/delete/{menuId}")
	public String deleteMenu(@PathVariable("menuId")
	Integer menuId) {

		Menu menu = menuService.getMenu(menuId);
		if (menu != null) {
			String menuImageUrl = menu.getImageUrl();
			if (!StringUtility.isNullOrEmpty(menuImageUrl) && menuImageUrl.startsWith("/")) {
				File image = new File("webapps" + menuImageUrl);
				if (image.exists()) {
					image.delete();
				}
			}
			menuService.removeMenu(menuId);
		}
		
		return "redirect:/menu/";
	}
	
	@RequestMapping("/show/{menuId}")
	public String showMenu(Map<String, Object> map, @PathVariable("menuId") Integer menuId) {
		Menu menu = menuService.getMenu(menuId);
		map.put("menu", menu);
		return "showNewMenu";
	}
	
	@RequestMapping("/getjson/{menuId}")
	public @ResponseBody MenuWrapper showMenuJson(Map<String, Object> map, @PathVariable("menuId") Integer menuId) {
		return MenuWrapper.getMenuWrapper(menuService.getMenu(menuId));
	}
	
	@RequestMapping("/getallmenusjson/{restaurantId}")
	public @ResponseBody Menus showAllMenusJson(Map<String, Object> map, @PathVariable("restaurantId") Integer restaurantId) {
		Menus menus = new Menus();
		menus.setStatus(Status.ACTIVE);
		menus.setRestaurantId(restaurantId);
		//Currently restaurant Id and userId are same.
		User user = userService.getUser(restaurantId);
		if (user != null) {
			menus.setPortraitImageUrl(user.getBusinessPortraitImageUrl());
			menus.setLandscapeImageUrl(user.getBusinessLandscapeImageUrl());
			menus.setAppCacheIconUrl(user.getAppCacheIconUrl());
			menus.setButtonIconUrl(user.getButtonIconUrl());
		}
		List<Menu> menuList = menuService.allMenusByStatus(restaurantId, Status.ACTIVE);
		List<MenuWrapper> menuWrappers = new ArrayList<MenuWrapper>();
		for (Menu menu : menuList) {
			menuWrappers.add(MenuWrapper.getMenuWrapper(menu));
		}
		menus.setMenus(menuWrappers);
		return menus;
	}
	
	@RequestMapping("/getmenusjsonbyuname")
	public @ResponseBody Menus getMenusJsonByUsername(HttpServletRequest request, HttpServletResponse response){
		String username = request.getParameter("username");
		Menus menus = new Menus();
		if (StringUtility.isNullOrEmpty(username)) {
			return menus;
		}
		
		
		User user = userService.getUserByUsername(username);
		if (user == null) {
			return menus;
		}
		menus.setStatus(Status.ACTIVE);
		menus.setRestaurantId(user.getUserId());
		menus.setPortraitImageUrl(user.getBusinessPortraitImageUrl());
		menus.setLandscapeImageUrl(user.getBusinessLandscapeImageUrl());
		menus.setAppCacheIconUrl(user.getAppCacheIconUrl());
		menus.setButtonIconUrl(user.getButtonIconUrl());
		List<Menu> menuList = menuService.allMenusByStatus(user.getUserId(), Status.ACTIVE);
		List<MenuWrapper> menuWrappers = new ArrayList<MenuWrapper>();
		for (Menu menu : menuList) {
			menuWrappers.add(MenuWrapper.getMenuWrapper(menu));
		}
		menus.setMenus(menuWrappers);
		return menus;
	}
	
	@RequestMapping(value = "/manifest/{restaurantId}.manifest", method = RequestMethod.GET)
	public void getManifestFile(HttpServletRequest request, HttpServletResponse response, @PathVariable("restaurantId") Integer restaurantId) throws IOException {
		User user = userService.getUser(restaurantId);
		if (user != null) {
			String businessName = user.getBusinessName();
			businessName = businessName.replaceAll("[^a-zA-Z0-9_]", "");
			List<Menu> menus = menuService.allMenusByStatus(user.getUserId(), Status.ACTIVE);
			HashSet<String> fileNames = new HashSet<String>();
			if (!StringUtility.isNullOrEmpty(user.getBusinessLandscapeImageUrl())) {
				fileNames.add(user.getBusinessLandscapeImageUrl());
			}
			if (!StringUtility.isNullOrEmpty(user.getBusinessPortraitImageUrl())) {
				fileNames.add(user.getBusinessPortraitImageUrl());
			}
			if (!StringUtility.isNullOrEmpty(user.getAppCacheIconUrl())) {
				fileNames.add(user.getAppCacheIconUrl());
			}
			if (!StringUtility.isNullOrEmpty(user.getButtonIconUrl())) {
				fileNames.add(user.getButtonIconUrl());
			}
			
			for (Menu menu : menus) {
				List<Section> sections = menu.getSections();
				for (Section section: sections) {
					List<Dish> dishes = section.getDishes();
					for (Dish dish : dishes) {
						String imageUrl = dish.getImageUrl();
						if (!fileNames.contains(imageUrl)) {
							fileNames.add(imageUrl);
						}
						String smallImageUrl = ImageUtility.getSmallImageUrl(imageUrl, 200, 200);
						if (!fileNames.contains(smallImageUrl)) {
							fileNames.add(smallImageUrl);
						}
					}
				}
			}
			File fileDir = new File("webapps" + File.separator + "static" + File.separator + "clients" + File.separator + "com"  + File.separator + "cookedspecially" + File.separator + businessName);
			fileDir.mkdirs();
			File file = new File("webapps" + File.separator + "static" + File.separator + "clients" + File.separator + "com"  + File.separator + "cookedspecially" + File.separator + businessName + File.separator + businessName + ".manifest");
			BufferedWriter bw = null;
			BufferedReader br = null;
			try {
				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}
	 
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				bw = new BufferedWriter(fw);
				File staticFile = new File("webapps" + File.separator + "static" + File.separator + "resources" + File.separator + "staticFile");
				FileReader fr = new FileReader(staticFile);
				br = new BufferedReader(fr);
				String line;
				while((line = br.readLine()) != null) {
					bw.write(line + "\n");
				}
				
				Iterator<String> iterFileName = fileNames.iterator();
				while(iterFileName.hasNext()) {
					bw.write(iterFileName.next() + "\n");
				}
				bw.write("\n");
				bw.write("NETWORK:\n");
				bw.write("/CookedSpecially/menu/getallmenusjson/" + user.getUserId() + "\n*\n\n");
				bw.write("FALLBACK:\n");
				
				bw.flush();
				
			} catch(IOException excep) {
				excep.printStackTrace();
			} finally {
				if (br != null) {
					br.close();
				}
				if (bw != null) {
					bw.close();
				}
			}
			
		    response.setContentType("text/cache-manifest");
		    response.setCharacterEncoding("UTF-8");
		    response.setHeader("Pragma","No-cache");     
		    response.setHeader("Cache-Control","no-cache");     
		    response.setDateHeader("Expires", 0);   
			response.setContentLength(new Long(file.length()).intValue());
	        FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
		}
	}
	
	private String renameFileToHaveMenuId(String fileUrl, Integer menuId) {
		File oldFile = new File("webapps" + fileUrl);
		String newFileUrl = fileUrl.replace("null_", menuId + "_");
		File newFile = new File("webapps" + newFileUrl);
		oldFile.renameTo(newFile);
		return newFileUrl;
	}
}
