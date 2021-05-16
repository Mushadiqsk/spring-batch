package com.spring.batch.csv.SpringBatchCSV.config.writers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.spring.batch.csv.SpringBatchCSV.model.Order;

@Component("OrderExcelWriter")
@Scope("step")
public class OrderExcelWriter implements ItemWriter<Order>{



	private int currRow = 1;
	String excelFilePath ;
	private static final String[] HEADERS = { "anzsic06", "area", "year","geo_count","ec_count" };
	  Workbook workbook = null;



	private void createHeaderRow(Sheet sheet) {
		 
	    CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
	    Font font = sheet.getWorkbook().createFont();
	    font.setBold(true);
	    cellStyle.setFont(font);
	 
	    Row row = sheet.createRow(0);
	    Cell cellAnzsic06 = row.createCell(0);
	 
	    cellAnzsic06.setCellStyle(cellStyle);
	    cellAnzsic06.setCellValue("Anzsic06");
	 
	    Cell cellArea = row.createCell(1);
	    cellArea.setCellStyle(cellStyle);
	    cellArea.setCellValue("Area");
	 
	    Cell cellYear = row.createCell(2);
	    cellYear.setCellStyle(cellStyle);
	    cellYear.setCellValue("Year");
	    
	    Cell cellGeo_count = row.createCell(3);
	    cellGeo_count.setCellStyle(cellStyle);
	    cellGeo_count.setCellValue("Geo_count");
	    
	    Cell cellEc_count = row.createCell(4);
	    cellEc_count.setCellStyle(cellStyle);
	    cellEc_count.setCellValue("Ec_count");
	}

	
	@Override
	public void write(List<? extends Order> orders) throws Exception {
		// TODO Auto-generated method stub
		  workbook = new XSSFWorkbook();
		  
		    Sheet sheet = workbook.createSheet("order");
		    createHeaderRow(sheet);
		    excelFilePath =  File.createTempFile("Order", ".xlsx").getAbsolutePath();

		    orders.stream().forEach((order) -> {
		    	  Row row = sheet.createRow(currRow);
		          writeBook(order, row);
		          currRow++;
		    });

		 
			/*
			 * try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
			 * workbook.write(outputStream); }
			 */
		    
	}




	private void writeBook(Order order, Row row) {
		// TODO Auto-generated method stub
		Cell cell = row.createCell(0);
	    cell.setCellValue(order.getAnzsic06());
	 
	    cell = row.createCell(1);
	    cell.setCellValue(order.getArea());
	 
	    cell = row.createCell(2);
	    cell.setCellValue(order.getArea());
	    
	    cell = row.createCell(3);
	    cell.setCellValue(order.getGeo_count());
	    
	    cell = row.createCell(4);
	    cell.setCellValue(order.getEc_count());
	}


	@AfterStep
	public void afterStep(StepExecution stepExecution) throws IOException {
		FileOutputStream fos = new FileOutputStream(excelFilePath);
		workbook.write(fos);
		fos.close();
	}

}
