/**
 * 
 */
package com.cookedspecially.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javassist.expr.NewArray;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cookedspecially.service.CheckService;

/**
 * @author shashank
 *
 */
@Controller
@RequestMapping("/reports")
public class ReportingController {

	@Autowired
	private CheckService checkService;
	
	@RequestMapping(value = "jrreport", method=RequestMethod.GET)
	public ModelAndView showReport(@RequestParam("restaurantId") Integer restaurantId, ModelAndView mav) {
		JRDataSource jrds = new JRBeanCollectionDataSource(checkService.getAllOpenChecks(restaurantId));
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		//model.addAttribute("datasource", jrds);
		//model.addAttribute("format", "pdf");
		//return "multiViewReport";
		parameterMap.put("datasource", jrds);
		 
        //xlsReport bean has ben declared in the jasper-views.xml file
		mav = new ModelAndView("htmlReport", parameterMap);
 
        return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "pdf")
    public ModelAndView generatePdfReport(@RequestParam("restaurantId") Integer restaurantId, ModelAndView modelAndView){
 
        Map<String,Object> parameterMap = new HashMap<String,Object>();
        JRDataSource jrds = new JRBeanCollectionDataSource(checkService.getAllOpenChecks(restaurantId));
        
 
        parameterMap.put("datasource", jrds);
 
        //pdfReport bean has ben declared in the jasper-views.xml file
        modelAndView = new ModelAndView("pdfReport", parameterMap);
 
        return modelAndView;
 
    }//generatePdfReport
 
}
