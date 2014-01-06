/**
 * 
 */
package com.cookedspecially.view;

import java.util.List;
import java.util.Map;

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
		//create a wordsheet
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
		} else {
			createDailySalesReport(sheet, reportDataMap, rowNum);
		}

	}

	private void createDailySalesReport(HSSFSheet sheet, Map<String,Object> reportDataMap, int startRowNum) {
		int rowNum = startRowNum;
		List<String> categories = (List<String>)reportDataMap.get("Cat");
		List list = (List)reportDataMap.get("data");
		
		for(Object result : list) {
			//create the row data
			HSSFRow row = sheet.createRow(rowNum++);
			Object[] rowData = (Object[])result;
			row.createCell(0).setCellValue(((CheckType)rowData[1]).toString());
			row.createCell(1).setCellValue((Double)rowData[0]);
		}
//		for (String cat : categories) {
//			//create the row data
//			HSSFRow row = sheet.createRow(rowNum++);
//			row.createCell(0).setCellValue(cat);
//			
//		}
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
