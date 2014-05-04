/**
 * 
 */
package com.cookedspecially.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
import com.cookedspecially.domain.Customer;
import com.cookedspecially.domain.Dish;
import com.cookedspecially.domain.Order;
import com.cookedspecially.domain.OrderDish;
import com.cookedspecially.domain.User;
import com.cookedspecially.enums.check.CheckType;
import com.cookedspecially.enums.check.Status;
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
			//row.setHeight((short)0);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue(restaurantName);
			cell.setCellStyle(style);
			rowNum++;
			
			row.setHeightInPoints(-1);

		}
		
		// Create Report Name entry
		HSSFRow row = sheet.createRow(rowNum++);
		row.setRowStyle(style);
		//row.setHeight((short)0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(reportName);
		cell.setCellStyle(style);
		row.setHeightInPoints(-1);
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
		header.setHeightInPoints(-1);
		//header.setRowStyle(style);
		//header.setHeight((short)0);
		if (reportName.equals("checkReport")) {
			createCheckReport(sheet, reportDataMap, rowNum);
		} else if (reportName.equals("Daily Invoice")) {
			createDailyInvoiceReport(sheet, reportDataMap, rowNum);
		} else if (reportName.equals("Daily Sales Summary")) {
			createDailySalesSummaryReport(sheet, reportDataMap, rowNum);
//		} else if(reportName.equals("Weekly Sales Summary Report")) {
//			createWeeklySalesSummaryReport(sheet, reportDataMap, rowNum);
		} else if (reportName.equals("Customers")) {
			createCustomerReport(sheet, reportDataMap, rowNum);
		} else if (reportName.equals("Top Dishes")) {
			createTopDishesReport(sheet, reportDataMap, rowNum);
		} else if (reportName.equals("Detailed Invoice")) {
			createDetailedInvoiceReport(sheet, reportDataMap, rowNum);
		} 

	}

	private Map<String, Double> getDishTypeBillMap(Check check) {
		Map<String, Double> dishTypeBillMap = new HashMap<String, Double>();
		List<Order> orders = check.getOrders();
		for (Order order : orders) {
			if (order.getStatus() == com.cookedspecially.enums.order.Status.CANCELLED) {
				continue;
			}
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
		Set<String> dishTypes = (Set<String>)reportDataMap.get("dishTypes");
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
			if (check.getStatus() != Status.Paid) {
				continue;
			}
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
		for (int i = 0; i < cellNo; i++) {
			sheet.autoSizeColumn(i);
		}
	}

	class DishData {
		public int count;
		public double price;
	}
	
	private void createTopDishesReport(HSSFSheet sheet, Map<String,Object> reportDataMap, int startRowNum) {
		int rowNum = startRowNum;
		List<Dish> dishes = (List<Dish>)reportDataMap.get("dishes");
		List<Check> checks = (List<Check>)reportDataMap.get("data");
		
		Map<Integer, Dish> dishMap = new HashMap<Integer, Dish>();
		for(Dish dish : dishes) {
			dishMap.put(dish.getDishId(), dish);
		}
		
		Map<Integer, DishData> dishDataMap = new HashMap<Integer, DishData>();
		
		for(Check check : checks) {
			if (check.getStatus() != Status.Paid) {
				continue;
			}
			List<Order> orders = check.getOrders();
			for (Order order : orders) {
				if (order.getStatus() == com.cookedspecially.enums.order.Status.CANCELLED) {
					continue;
				}
				List<OrderDish> items = order.getOrderDishes();
				for (OrderDish item: items) {
					
					if(dishDataMap.containsKey(item.getDishId())) {
						DishData dishData = dishDataMap.get(item.getDishId());
						dishData.count += item.getQuantity();
						dishData.price += (item.getPrice() * item.getQuantity());
					} else {
						DishData dishData = new DishData();
						dishData.count += item.getQuantity();
						dishData.price += ((double)item.getPrice() * item.getQuantity());
						dishDataMap.put(item.getDishId(), dishData);
					}
				}
			}
		}
		
		int cellNo = 0;
		for(Entry<Integer, DishData> dishDataMapEntry : dishDataMap.entrySet()) {
			HSSFRow row = sheet.createRow(rowNum++);
			cellNo = 0;
			Integer dishId = dishDataMapEntry.getKey();
			Dish dish = dishMap.remove(dishId);
			row.createCell(cellNo++).setCellValue(dishId);
			row.createCell(cellNo++).setCellValue(dish.getName());
			row.createCell(cellNo++).setCellValue(dishDataMapEntry.getValue().count);
			row.createCell(cellNo++).setCellValue(dishDataMapEntry.getValue().price);
		}
		for (Dish dish : dishMap.values()) {
			HSSFRow row = sheet.createRow(rowNum++);
			cellNo = 0;
			row.createCell(cellNo++).setCellValue(dish.getDishId());
			row.createCell(cellNo++).setCellValue(dish.getName());
			row.createCell(cellNo++).setCellValue(0);
			row.createCell(cellNo++).setCellValue(0.0);
		}
		for (int i = 0; i < cellNo; i++) {
			sheet.autoSizeColumn(i);
		}
	}

	private void createCustomerReport(HSSFSheet sheet, Map<String,Object> reportDataMap, int startRowNum) {
		int rowNum = startRowNum;
		List<Customer> customers = (List<Customer>)reportDataMap.get("data");
		int cellNo = 0;		
		for(Customer customer : customers) {
			HSSFRow row = sheet.createRow(rowNum++);
			cellNo = 0;
			row.createCell(cellNo++).setCellValue(customer.getFirstName());
			row.createCell(cellNo++).setCellValue(customer.getLastName());
			row.createCell(cellNo++).setCellValue(customer.getAddress());
			row.createCell(cellNo++).setCellValue(customer.getCity());
			row.createCell(cellNo++).setCellValue(customer.getPhone());
			row.createCell(cellNo++).setCellValue(customer.getEmail());
		}
		
		for (int i = 0; i < cellNo; i++) {
			sheet.autoSizeColumn(i);
		}
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
		Set<String> dishTypes = (Set<String>)reportDataMap.get("dishTypes");
		List<Check> checks = (List<Check>)reportDataMap.get("data");
		User user = (User)reportDataMap.get("user");
		int sno = 1;
		Double totalBill = 0.0;
		double totalBillWithTaxes = 0.0;
		double totalTax1 = 0.0;
		double totalTax2 = 0.0;
		double totalTax3 = 0.0;
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
			if(!StringUtility.isNullOrEmpty(user.getAdditionalChargesName1())) {
				totalTax1 += (double)check.getAdditionalChargesValue1();
				row.createCell(cellNo++).setCellValue(check.getAdditionalChargesValue1());
			}
			if(!StringUtility.isNullOrEmpty(user.getAdditionalChargesName2())) {
				totalTax2 += (double)check.getAdditionalChargesValue2();
				row.createCell(cellNo++).setCellValue(check.getAdditionalChargesValue2());
			}
			if(!StringUtility.isNullOrEmpty(user.getAdditionalChargesName3())) {
				totalTax3 += (double)check.getAdditionalChargesValue3();
				row.createCell(cellNo++).setCellValue(check.getAdditionalChargesValue3());
			}
			double billWithTaxes = (double)(check.getBill() + check.getAdditionalChargesValue1() + check.getAdditionalChargesValue2() + check.getAdditionalChargesValue3());
			totalBillWithTaxes += billWithTaxes;
			row.createCell(cellNo++).setCellValue(billWithTaxes);
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
		if(!StringUtility.isNullOrEmpty(user.getAdditionalChargesName1())) {
			finalRow.createCell(cellNo++).setCellValue(totalTax1);
		}
		if(!StringUtility.isNullOrEmpty(user.getAdditionalChargesName2())) {
			finalRow.createCell(cellNo++).setCellValue(totalTax2);
		}
		if(!StringUtility.isNullOrEmpty(user.getAdditionalChargesName3())) {
			finalRow.createCell(cellNo++).setCellValue(totalTax3);
		}
		finalRow.createCell(cellNo++).setCellValue(totalBillWithTaxes);
		for (String dishType : dishTypes) {
			finalRow.createCell(cellNo++).setCellValue(dishTypeTotalBillMap.get(dishType));
		}
		
		for (int i = 0; i < cellNo; i++) {
			sheet.autoSizeColumn(i);
		}
	}

	private void createDetailedInvoiceReport(HSSFSheet sheet, Map<String,Object> reportDataMap, int startRowNum) {
		int rowNum = startRowNum;
		List<Check> checks = (List<Check>)reportDataMap.get("data");
		User user = (User)reportDataMap.get("user");
		
		DateFormat formatter;
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone(user.getTimeZone()));
		
		int cellNo = 0;
		for(Check check : checks) {
			//create the row data
			HSSFRow row = sheet.createRow(rowNum++);
			cellNo = 0;
//			"Invoice#", "Opening Time", "Closing Time", 
			row.createCell(cellNo++).setCellValue(check.getInvoiceId());
			
			row.createCell(cellNo++).setCellValue(formatter.format(check.getOpenTime()));
			row.createCell(cellNo++).setCellValue(formatter.format(check.getCloseTime()));

			// "Delivery Address" , "Delivery Area"
			row.createCell(cellNo++).setCellValue(check.getDeliveryAddress());
			row.createCell(cellNo++).setCellValue(check.getDeliveryArea());

			//"Tax", "Total With taxes and Discount", "Sub Total without Tax and Discount", "Discount", 
			double checkTotalTax = 0;
			if(!StringUtility.isNullOrEmpty(user.getAdditionalChargesName1())) {
				checkTotalTax += check.getAdditionalChargesValue1(); 
			}
			if(!StringUtility.isNullOrEmpty(user.getAdditionalChargesName2())) {
				checkTotalTax += (double)check.getAdditionalChargesValue2();
			}
			if(!StringUtility.isNullOrEmpty(user.getAdditionalChargesName3())) {
				checkTotalTax += (double)check.getAdditionalChargesValue3();
			}
			row.createCell(cellNo++).setCellValue(checkTotalTax);
			double discount = (double)(check.getDiscountAmount());
			if (discount == 0 && check.getDiscountPercent() > 0) {
				discount = check.getDiscountPercent() * check.getBill() / 100;
			}
			
			row.createCell(cellNo++).setCellValue((double)(check.getBill()) + checkTotalTax - discount);
			row.createCell(cellNo++).setCellValue(check.getBill());			
			row.createCell(cellNo++).setCellValue(discount);
			
			// "Dish Name", "Total Dishes Cost", "Dish Quantity",
			HashMap<String, DishData> dishDataMap = new HashMap<String, DishData>();
			List<Order> orders = check.getOrders();
			for(Order order : orders) {
				if (order.getStatus() == com.cookedspecially.enums.order.Status.CANCELLED) {
					continue;
				}
				
				List<OrderDish> items = order.getOrderDishes();
				for(OrderDish item : items) {
					
					if(!dishDataMap.containsKey(item.getName())) {
						dishDataMap.put(item.getName(), new DishData());
					} 
					
					DishData dishData = dishDataMap.get(item.getName());
					dishData.count += item.getQuantity();
					dishData.price += item.getQuantity() * item.getPrice();
				}
				
			}
			
			if(dishDataMap.size() == 0) {
				row.createCell(cellNo++).setCellValue("Cancelled Check");
				row.createCell(cellNo++).setCellValue(0);
				row.createCell(cellNo++).setCellValue(0);
				
			} else {
				boolean firstEntry = true;
				for(Entry<String, DishData> dishDataMapEntry : dishDataMap.entrySet()) {
					if(!firstEntry) {
						row = sheet.createRow(rowNum++);
						cellNo = 0;
						row.createCell(cellNo++).setCellValue(check.getInvoiceId());
						
						row.createCell(cellNo++).setCellValue("");
						row.createCell(cellNo++).setCellValue("");
						row.createCell(cellNo++).setCellValue("");
						row.createCell(cellNo++).setCellValue("");
						row.createCell(cellNo++).setCellValue(0);
						row.createCell(cellNo++).setCellValue(0);
						row.createCell(cellNo++).setCellValue(0);
						row.createCell(cellNo++).setCellValue(0);
					}
					
					row.createCell(cellNo++).setCellValue(dishDataMapEntry.getKey());
					row.createCell(cellNo++).setCellValue(dishDataMapEntry.getValue().price);
					row.createCell(cellNo++).setCellValue(dishDataMapEntry.getValue().count);
					
					firstEntry = false;
				}	
			}
			
			
			
		}
				
		for (int i = 0; i < cellNo; i++) {
			sheet.autoSizeColumn(i);
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
