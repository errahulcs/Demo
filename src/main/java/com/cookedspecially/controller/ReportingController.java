/**
 * 
 */
package com.cookedspecially.controller;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javassist.expr.NewArray;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cookedspecially.domain.Check;
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
	
	@RequestMapping(value = "checkReport.xlsx", method=RequestMethod.GET)
	public ModelAndView generateCheckReport(@RequestParam("restaurantId") Integer restaurantId, ModelAndView mav) {
		List<Check> checks = checkService.getAllOpenChecks(restaurantId);
		Map<String, Object> reportDataMap = new HashMap<String, Object>();
		reportDataMap.put("checks", checks);
		reportDataMap.put("reportName", "checkReport");
		reportDataMap.put("restaurantName", "Axis Restaurant");
		reportDataMap.put("Headers", Arrays.asList("Id", "price"));
		return new ModelAndView("ExcelReportView", "reportData", reportDataMap);
	}
	
	@RequestMapping(value = "dailySalesSummary.xlsx", method=RequestMethod.GET)
	public ModelAndView dailySalesSummaryExcel(@RequestParam("restaurantId") Integer restaurantId, ModelAndView mav) {
		Calendar today = Calendar.getInstance();
		//today.add(Calendar.DAY_OF_YEAR, -100);
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		Calendar nextDay = Calendar.getInstance();
		nextDay.setTime(today.getTime());
		nextDay.add(Calendar.DAY_OF_MONTH, 1);
		List data = checkService.getClosedChecksByDate(restaurantId, today.getTime(), nextDay.getTime());
				
		Map<String, Object> reportDataMap = new HashMap<String, Object>();
		reportDataMap.put("data", data);
		reportDataMap.put("reportName", "dailySalesSummary");
		reportDataMap.put("Headers", Arrays.asList("", "Food", "Beverages"));
		reportDataMap.put("Cat", Arrays.asList("DINE IN", "ROOM SERVICE", "TAKEAWAY", "DELIVERY", "GROSS SALES"));
		reportDataMap.put("restaurantName", "Axis Restaurant");
		return new ModelAndView("ExcelReportView", "reportData", reportDataMap);
	}
	
//	@RequestMapping(value = "html", method=RequestMethod.GET)
//	public ModelAndView generateHTMLReport(@RequestParam("restaurantId") Integer restaurantId, ModelAndView mav) {
//		JRDataSource jrds = new JRBeanCollectionDataSource(checkService.getAllOpenChecks(restaurantId));
//		Map<String,Object> parameterMap = new HashMap<String,Object>();
//		//model.addAttribute("datasource", jrds);
//		//model.addAttribute("format", "pdf");
//		//return "multiViewReport";
//		parameterMap.put("datasource", jrds);
//		 
//        //xlsReport bean has ben declared in the jasper-views.xml file
//		mav = new ModelAndView("htmlReport", parameterMap);
// 
//        return mav;
//	}
//	
//	@RequestMapping(method = RequestMethod.GET , value = "pdf")
//    public ModelAndView generatePdfReport(@RequestParam("restaurantId") Integer restaurantId, ModelAndView modelAndView){
// 
//        Map<String,Object> parameterMap = new HashMap<String,Object>();
//        JRDataSource jrds = new JRBeanCollectionDataSource(checkService.getAllOpenChecks(restaurantId));
//        
// 
//        parameterMap.put("datasource", jrds);
// 
//        //pdfReport bean has ben declared in the jasper-views.xml file
//        modelAndView = new ModelAndView("pdfReport", parameterMap);
// 
//        return modelAndView;
// 
//    }//generatePdfReport
// 
//	@RequestMapping(method = RequestMethod.GET , value = "xls.xlsx")
//    public ModelAndView generateXlsReport(@RequestParam("restaurantId") Integer restaurantId, ModelAndView modelAndView){ 
//        Map<String,Object> parameterMap = new HashMap<String,Object>();
//        JRDataSource jrds = new JRBeanCollectionDataSource(checkService.getAllOpenChecks(restaurantId));       
//        parameterMap.put("datasource", jrds);
// 
//        //xlsReport bean has ben declared in the jasper-views.xml file
//        modelAndView = new ModelAndView("xlsReport", parameterMap);
// 
//        return modelAndView;
// 
//    }//generatePdfReport
// 
//	 @RequestMapping(method = RequestMethod.GET , value = "csv")
//	    public ModelAndView generateCsvReport(@RequestParam("restaurantId") Integer restaurantId, ModelAndView modelAndView){
//	 
//	        Map<String,Object> parameterMap = new HashMap<String,Object>();
//	        JRDataSource jrds = new JRBeanCollectionDataSource(checkService.getAllOpenChecks(restaurantId));       
//	        parameterMap.put("datasource", jrds);
//	 
//	        //xlsReport bean has ben declared in the jasper-views.xml file
//	        modelAndView = new ModelAndView("csvReport", parameterMap);
//	        return modelAndView;
//	    }//generatePdfReport
}
