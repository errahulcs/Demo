/**
 * 
 */
package com.cookedspecially.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFBorderFormatting;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFontFormatting;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.cookedspecially.domain.Check;
import com.cookedspecially.domain.Order;
import com.cookedspecially.domain.OrderDish;
import com.cookedspecially.domain.User;
import com.cookedspecially.enums.check.CheckType;
import com.cookedspecially.utility.StringUtility;

/**
 * @author shashank
 *
 */
public class ExcelReportView extends AbstractExcelView {

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.view.document.AbstractExcelView#buildExcelDocument(java.util.Map, org.apache.poi.hssf.usermodel.HSSFWorkbook, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String,Object> reportDataMap = (Map<String,Object>) model.get("reportData");
		List<String> headers = (List<String>)reportDataMap.get("Headers");
		String reportName = (String)reportDataMap.get("reportName");
		//create a worksheet
		HSSFSheet sheet = workbook.createSheet("Report");
				
		if (StringUtility.isNullOrEmpty(reportName)) {
			return;
		}
		// Fonts
		HSSFCellStyle style=null;

	    HSSFFont defaultFont= workbook.createFont();
	    defaultFont.setFontHeightInPoints((short)15);
	    defaultFont.setFontName(HSSFFont.FONT_ARIAL);
	    defaultFont.setColor(HSSFColor.BLACK.index);
	    defaultFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    defaultFont.setItalic(true);

	    
	    style=workbook.createCellStyle();
	    //style.setFillBackgroundColor(HSSFColor.AUTOMATIC.index);
	    style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
	    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	    //style.setWrapText(true);
	    style.setFont(defaultFont);
		
		
		int rowNum = 0;
		// create restaurant Name
		String restaurantName = (String)reportDataMap.get("restaurantName");
		if (!StringUtility.isNullOrEmpty(restaurantName)) {
			HSSFRow row = sheet.createRow(rowNum++);
			row.setRowStyle(style);
			row.setHeight((short)0);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue(restaurantName);
			cell.setCellStyle(style);
			rowNum++;
			//row.setHeightInPoints(20);

		}
		
		// Create Report Name entry
		HSSFRow row = sheet.createRow(rowNum++);
		row.setRowStyle(style);
		row.setHeight((short)0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(reportName);
		cell.setCellStyle(style);
		//row.setHeightInPoints(-1);
		rowNum++;
		
		HSSFCellStyle headerStyle=workbook.createCellStyle();
	    //style.setFillBackgroundColor(HSSFColor.AUTOMATIC.index);
		headerStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	    //style.setWrapText(true);
		headerStyle.setFont(defaultFont);
		headerStyle.setBorderBottom((short)1);
		//headerStyle.setBorderLeft((short)1);
		headerStyle.setBorderTop((short)1);
		headerStyle.setBorderRight((short)1);
		headerStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		headerStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		headerStyle.setTopBorderColor(HSSFColor.BLACK.index);
		headerStyle.setRightBorderColor(HSSFColor.BLACK.index);
		// Create actual headers
		HSSFRow header = sheet.createRow(rowNum++);
	    int headerNum = 0;
		for (String headerValue : headers) {
			HSSFCell headerCell = header.createCell(headerNum++);	
			headerCell.setCellValue(headerValue);
			headerCell.setCellStyle(headerStyle);
		}
		//header.setHeightInPoints(20);
		//header.setRowStyle(style);
		header.setHeight((short)0);
		if (reportName.equals("checkReport")) {
			createCheckReport(sheet, reportDataMap, rowNum);
		} else if (reportName.equals("Daily Invoice")) {
			createDailyInvoiceReport(sheet, reportDataMap, rowNum);
		} else if (reportName.equals("Daily Sales Summary")) {
			createDailySalesSummaryReport(sheet, reportDataMap, rowNum);
//		} else if(reportName.equals("Weekly Sales Summary Report")) {
//			createWeeklySalesSummaryReport(sheet, reportDataMap, rowNum);
		}

	}

	private Map<String, Double> getDishTypeBillMap(Check check) {
		Map<String, Double> dishTypeBillMap = new HashMap<String, Double>();
		List<Order> orders = check.getOrders();
		for (Order order : orders) {
			List<OrderDish> items = order.getOrderDishes();
			for (OrderDish item: items) {
				if(dishTypeBillMap.containsKey(item.getDishType())) {
					dishTypeBillMap.put(item.getDishType(), dishTypeBillMap.get(item.getDishType()) + (item.getPrice() * item.getQuantity()));
				} else {
					dishTypeBillMap.put(item.getDishType(), (double)item.getPrice() * item.getQuantity());
				}
			}
		}
		return dishTypeBillMap;
	}
	
	private void createDailySalesSummaryReport(HSSFSheet sheet, Map<String,Object> reportDataMap, int startRowNum) {
		int rowNum = startRowNum;
		List<String> dishTypes = (List<String>)reportDataMap.get("dishTypes");
		List<Check> checks = (List<Check>)reportDataMap.get("data");

		
		//Map<String, Double> dishTypeTotalBillMap = new HashMap<String, Double>();
		Map<String, Map<String, Double>> checkTypevsDishTypeBillMap = new HashMap<String, Map<String,Double>>();
		Map<String, Integer> checkTypeCountMap = new HashMap<String, Integer>();
		Map<String, Double> dishTypeTotalBillMap = new HashMap<String, Double>();
		
		// Initialize
		for (String dishType: dishTypes) {
			dishTypeTotalBillMap.put(dishType, 0.0);
		}
		for(CheckType checkTypeEnum : CheckType.values()) {
			Map<String, Double> dishTypeBillMap = new HashMap<String, Double>();
			for (String dishType : dishTypes) {
				dishTypeBillMap.put(dishType, 0.0);
			}
			checkTypevsDishTypeBillMap.put(checkTypeEnum.toString(), dishTypeBillMap);
			checkTypeCountMap.put(checkTypeEnum.toString(), 0);
		}
		
		for(Check check : checks) {
			Map<String, Double> dishTypeBillMap = getDishTypeBillMap(check);
			for (String dishType : dishTypes) {
				Double dishTypeBill = dishTypeBillMap.containsKey(dishType)?dishTypeBillMap.get(dishType):0;
				Map<String, Double> dishTypePriceMap = checkTypevsDishTypeBillMap.get(check.getCheckType().toString());
				dishTypePriceMap.put(dishType, dishTypePriceMap.get(dishType) + dishTypeBill);
				dishTypeTotalBillMap.put(dishType, dishTypeTotalBillMap.get(dishType) + dishTypeBill);
			}
			checkTypeCountMap.put(check.getCheckType().toString(), checkTypeCountMap.get(check.getCheckType().toString()) + 1);
		}
		
		int cellNo = 0;
		Integer totalCustomer = 0;
		for (CheckType checkTypeEnum : CheckType.values()) {
			HSSFRow row = sheet.createRow(rowNum++);
			cellNo = 0;
			row.createCell(cellNo++).setCellValue(checkTypeEnum.toString());
			Double total = 0.0;
			Map<String, Double> dishTypeBillMap = checkTypevsDishTypeBillMap.get(checkTypeEnum.toString());
			for (String dishType : dishTypes) {
				row.createCell(cellNo++).setCellValue(dishTypeBillMap.get(dishType));
				total += dishTypeBillMap.get(dishType);
			}	
			row.createCell(cellNo++).setCellValue(total);
			Integer customers = checkTypeCountMap.get(checkTypeEnum.toString());
			row.createCell(cellNo++).setCellValue(customers);
			totalCustomer += customers;
			row.createCell(cellNo++).setCellValue(customers>0?total/customers:0);
		}
		
		// final row
		HSSFRow row = sheet.createRow(rowNum++);
		cellNo = 0;
		row.createCell(cellNo++).setCellValue("Gross Sales");
		Double total = 0.0;
		for (String dishType : dishTypes) {
			row.createCell(cellNo++).setCellValue(dishTypeTotalBillMap.get(dishType));
			total += dishTypeTotalBillMap.get(dishType);
		}	
		row.createCell(cellNo++).setCellValue(total);
		row.createCell(cellNo++).setCellValue(totalCustomer);
		row.createCell(cellNo++).setCellValue(totalCustomer>0?total/totalCustomer:0);
	}

//	private void createWeeklySalesSummaryReport(HSSFSheet sheet, Map<String,Object> reportDataMap, int startRowNum) {
//		int rowNum = startRowNum;
//		List<String> dishTypes = (List<String>)reportDataMap.get("dishTypes");
//		List<Check> checks = (List<Check>)reportDataMap.get("data");
//
//		
//		//Map<String, Double> dishTypeTotalBillMap = new HashMap<String, Double>();
//		Map<String, Map<String, Double>> checkTypevsDishTypeBillMap = new HashMap<String, Map<String,Double>>();
//		Map<String, Integer> checkTypeCountMap = new HashMap<String, Integer>();
//		Map<String, Double> dishTypeTotalBillMap = new HashMap<String, Double>();
//		
//		// Initialize
//		for (String dishType: dishTypes) {
//			dishTypeTotalBillMap.put(dishType, 0.0);
//		}
//		for(CheckType checkTypeEnum : CheckType.values()) {
//			Map<String, Double> dishTypeBillMap = new HashMap<String, Double>();
//			for (String dishType : dishTypes) {
//				dishTypeBillMap.put(dishType, 0.0);
//			}
//			checkTypevsDishTypeBillMap.put(checkTypeEnum.toString(), dishTypeBillMap);
//			checkTypeCountMap.put(checkTypeEnum.toString(), 0);
//		}
//		
//		for(Check check : checks) {
//			Map<String, Double> dishTypeBillMap = getDishTypeBillMap(check);
//			for (String dishType : dishTypes) {
//				Double dishTypeBill = dishTypeBillMap.containsKey(dishType)?dishTypeBillMap.get(dishType):0;
//				Map<String, Double> dishTypePriceMap = checkTypevsDishTypeBillMap.get(check.getCheckType().toString());
//				dishTypePriceMap.put(dishType, dishTypePriceMap.get(dishType) + dishTypeBill);
//				dishTypeTotalBillMap.put(dishType, dishTypeTotalBillMap.get(dishType) + dishTypeBill);
//			}
//			checkTypeCountMap.put(check.getCheckType().toString(), checkTypeCountMap.get(check.getCheckType().toString()) + 1);
//		}
//		
//		int cellNo = 0;
//		Integer totalCustomer = 0;
//		for (CheckType checkTypeEnum : CheckType.values()) {
//			HSSFRow row = sheet.createRow(rowNum++);
//			cellNo = 0;
//			row.createCell(cellNo++).setCellValue(checkTypeEnum.toString());
//			Double total = 0.0;
//			Map<String, Double> dishTypeBillMap = checkTypevsDishTypeBillMap.get(checkTypeEnum.toString());
//			for (String dishType : dishTypes) {
//				row.createCell(cellNo++).setCellValue(dishTypeBillMap.get(dishType));
//				total += dishTypeBillMap.get(dishType);
//			}	
//			row.createCell(cellNo++).setCellValue(total);
//			Integer customers = checkTypeCountMap.get(checkTypeEnum.toString());
//			row.createCell(cellNo++).setCellValue(customers);
//			totalCustomer += customers;
//			row.createCell(cellNo++).setCellValue(customers>0?total/customers:0);
//		}
//		
//		// final row
//		HSSFRow row = sheet.createRow(rowNum++);
//		cellNo = 0;
//		row.createCell(cellNo++).setCellValue("Gross Sales");
//		Double total = 0.0;
//		for (String dishType : dishTypes) {
//			row.createCell(cellNo++).setCellValue(dishTypeTotalBillMap.get(dishType));
//			total += dishTypeTotalBillMap.get(dishType);
//		}	
//		row.createCell(cellNo++).setCellValue(total);
//		row.createCell(cellNo++).setCellValue(totalCustomer);
//		row.createCell(cellNo++).setCellValue(totalCustomer>0?total/totalCustomer:0);
//	}
	
	private void createDailyInvoiceReport(HSSFSheet sheet, Map<String,Object> reportDataMap, int startRowNum) {
		int rowNum = startRowNum;
		List<String> dishTypes = (List<String>)reportDataMap.get("dishTypes");
		List<Check> checks = (List<Check>)reportDataMap.get("data");
		User user = (User)reportDataMap.get("user");
		int sno = 1;
		Double totalBill = 0.0;
		Map<String, Double> dishTypeTotalBillMap = new HashMap<String, Double>();
		
		// Initialize
		for (String dishType : dishTypes) {
			dishTypeTotalBillMap.put(dishType, 0.0);
		}
		
		DateFormat formatter;
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone(user.getTimeZone()));
		
		int cellNo = 0;
		for(Check check : checks) {
			//create the row data
			HSSFRow row = sheet.createRow(rowNum++);
			cellNo = 0;
			row.createCell(cellNo++).setCellValue(sno++);
			row.createCell(cellNo++).setCellValue(check.getInvoiceId());
			
			row.createCell(cellNo++).setCellValue(formatter.format(check.getOpenTime()));
			row.createCell(cellNo++).setCellValue(formatter.format(check.getCloseTime()));
			row.createCell(cellNo++).setCellValue(check.getCheckType().toString());
			totalBill += (double)check.getBill();
			row.createCell(cellNo++).setCellValue(check.getBill());
			Map<String, Double> dishTypeBillMap = getDishTypeBillMap(check);
			for (String dishType : dishTypes) {
				Double dishTypeBill = dishTypeBillMap.containsKey(dishType)?dishTypeBillMap.get(dishType):0;
				row.createCell(cellNo++).setCellValue(dishTypeBill);
				dishTypeTotalBillMap.put(dishType, dishTypeTotalBillMap.get(dishType) + dishTypeBill);
			}
		}
		
		/// final line
		HSSFRow finalRow = sheet.createRow(rowNum++);
		cellNo = 0;
		finalRow.createCell(cellNo++).setCellValue("Total");
		finalRow.createCell(cellNo++).setCellValue("");
		finalRow.createCell(cellNo++).setCellValue("");
		finalRow.createCell(cellNo++).setCellValue("");
		finalRow.createCell(cellNo++).setCellValue("");
		finalRow.createCell(cellNo++).setCellValue(totalBill);
		for (String dishType : dishTypes) {
			finalRow.createCell(cellNo++).setCellValue(dishTypeTotalBillMap.get(dishType));
		}
	}
		
	private void createCheckReport(HSSFSheet sheet, Map<String,Object> reportDataMap, int startRowNum) {
		int rowNum = startRowNum;
		List<Check> checks = (List<Check>)reportDataMap.get("checks");
		for (Check check : checks) {
			//create the row data
			HSSFRow row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(check.getCheckId());
			row.createCell(1).setCellValue(check.getBill());
		}
	}
}
