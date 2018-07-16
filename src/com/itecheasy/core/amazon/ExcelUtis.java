package com.itecheasy.core.amazon;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.itecheasy.common.util.DateUtils;
import com.itecheasy.common.util.DeployProperties;

/** 
 * @author wanghw
 * @date 2015-6-1 
 * @description TODO
 * @version
 */
public class ExcelUtis {
	

	public static String  createCancelOrderExcel(List<CancelOrderBO> cancels) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Sheet1");
		// 首行
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell0 = row.createCell(0);
		cell0.setCellValue("TemplateType=OrderCancellation");
		HSSFCell cell1 = row.createCell(1);
		cell1.setCellValue("Version=1.0/1.0.3");
		HSSFCell cell2 = row.createCell(2);
		cell2.setCellValue("This row for Amazon.com use only.  Do not modify or delete.");
		
		HSSFRow row1 = sheet.createRow(1);
		HSSFCell cell1_0 = row1.createCell(0);
		cell1_0.setCellValue("order-id");
		HSSFCell cell1_1 = row1.createCell(1);
		cell1_1.setCellValue("cancellation-reason-code");
		HSSFCell cell1_2 = row1.createCell(2);
		cell1_2.setCellValue("amazon-order-item-code");
		
		HSSFCellStyle style = wb.createCellStyle();// 文本样式
		style.setWrapText(true);// 回绕文本
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 文本居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setLocked(false);
		row.setHeightInPoints(60);
		
		
		for (int i = 0; i < 3; i++) {
			sheet.setColumnWidth(i, 3600);// 设置列宽
			row.getCell( i).setCellStyle(style);
		}
		
		for (int i = 0; i <cancels.size(); i++) {
			CancelOrderBO cancelOrder=cancels.get(i);
			row = sheet.createRow(i + 2);
			System.out.println(i);
			HSSFCell c0 = row.createCell(0);
			c0.setCellValue(cancelOrder.getOrderId());
			c0.setCellStyle(style);
			HSSFCell c1 = row.createCell(1);
			c1.setCellValue(cancelOrder.getCancelReason());
			c1.setCellStyle(style);
			HSSFCell c2 = row.createCell(2);
			c2.setCellValue(cancelOrder.getItemCode());
			c2.setCellStyle(style);
		}
		
		try {
			String file=DeployProperties.getInstance().getTempFile()+"request/"+
		DateUtils.convertDate(new Date(), "yyyyMMddHHmmss")+".xls";
			FileOutputStream fOut = new FileOutputStream(file);
			wb.write(fOut);
			fOut.flush();
			fOut.close();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String createConfirmOrderExcel(List<AmazonUploadTraceTemplate> templates) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Sheet1");
		// 首行
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell0 = row.createCell(0);
		cell0.setCellValue("order-id");
		HSSFCell cell1 = row.createCell(1);
		cell1.setCellValue("order-item-id");
		HSSFCell cell2 = row.createCell(2);
		cell2.setCellValue("quantity");
		HSSFCell cell3 = row.createCell(3);
		cell3.setCellValue("ship-date");
		HSSFCell cell4 = row.createCell(4);
		cell4.setCellValue("carrier-code");
		HSSFCell cell5 = row.createCell(5);
		cell5.setCellValue("carrier-name");
		HSSFCell cell6 = row.createCell(6);
		cell6.setCellValue("tracking-number");
		HSSFCell cell7 = row.createCell(7);
		cell7.setCellValue("ship-method");
		
		HSSFCellStyle style = wb.createCellStyle();// 文本样式
		style.setWrapText(true);// 回绕文本
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 文本居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setLocked(false);
		row.setHeightInPoints(60);
		
		for (int i = 0; i < 8; i++) {
			sheet.setColumnWidth(i, 3600);// 设置列宽
			row.getCell(i).setCellStyle(style);
		}
		
		for (int i = 0; i <templates.size(); i++) {
			AmazonUploadTraceTemplate temp=templates.get(i);
			row = sheet.createRow(i + 1);
			HSSFCell c0 = row.createCell(0);
			c0.setCellValue(temp.getOrderId());
			c0.setCellStyle(style);
			HSSFCell c1 = row.createCell(1);
			c1.setCellValue(temp.getOrderItemId());
			c1.setCellStyle(style);
			HSSFCell c2 = row.createCell(2);
			c2.setCellValue(temp.getQuantity());
			c2.setCellStyle(style);
			HSSFCell c3 = row.createCell(3);
			c3.setCellValue(temp.getShipDate());
			c3.setCellStyle(style);
			HSSFCell c4 = row.createCell(4);
			c4.setCellValue(temp.getCarrierCode());
			c4.setCellStyle(style);
			HSSFCell c5 = row.createCell(5);
			c5.setCellValue(temp.getCarrierName());
			c5.setCellStyle(style);
			HSSFCell c6 = row.createCell(6);
			c6.setCellValue(temp.getTrackingNumber());
			c6.setCellStyle(style);
			HSSFCell c7 = row.createCell(7);
			c7.setCellValue(temp.getShipMethod());
			c7.setCellStyle(style);
		}
		
		try {
			String file=DeployProperties.getInstance().getTempFile()+"request/"+
		DateUtils.convertDate(new Date(), "yyyyMMddHHmmss")+".xls";
			FileUtils.touch(new File(file));
			FileOutputStream fOut = new FileOutputStream(file);
			wb.write(fOut);
			fOut.flush();
			fOut.close();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
