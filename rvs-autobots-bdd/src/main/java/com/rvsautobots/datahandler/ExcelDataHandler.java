package com.rvsautobots.datahandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.rvsautobots.exceptions.AutomationException;

public class ExcelDataHandler {

	public static String cellValue;
	
	/**
	 * @Author AutobotsBDD Cucumber Team
	 * @Description :BDD
	 */
	/**
	 * Method excel Read excel
	 * @param browser
	 * @returns String 
	 **/
	public static String readExceldata(String FileName, String SheetName, int rowNumber, int columnNumber)
			throws IOException, AutomationException {

		try {

			File file = new File(System.getProperty("user.dir") + "/src/test/resources/ExcelData/" + FileName + ".xlsx");
			FileInputStream inputStream = new FileInputStream(file);
			Workbook excelHandlingBook = new XSSFWorkbook(inputStream);

			Sheet sheetDetails = excelHandlingBook.getSheet(SheetName);
			Row rowDetails = sheetDetails.getRow(rowNumber);

			if (rowDetails == null) {
				System.out.println("No data present in the Row, please check the cell coordinates are proper?");
				return "";
			}

			else {
				Cell cellDetails = rowDetails.getCell(columnNumber);
				if (cellDetails == null) {
					System.out.println("No data present in the cell, please check the cell coordinates are proper?");
					return "";
				}

				cellValue = rowDetails.getCell(columnNumber).getStringCellValue();
				System.out.println("Data in the required cell is = " + cellValue);
				inputStream.close();

			}
		} catch (Exception e) {
			throw new AutomationException("Error while reading the excel");
		}
		return cellValue;
	}


	/**
	 * Method excel Write to excel
	 * @param browser
	 * @returns void 
	 **/
	public static void writeExceldata(String FileName, String SheetName, int rowNumber, int columnNumber, String Value)
			throws IOException, AutomationException {

		try {
			File file = new File(System.getProperty("user.dir") + "/src/test/resources/ExcelData/" + FileName + ".xlsx");
			FileInputStream inputStream = new FileInputStream(file);
			Workbook excelHandlingBook = new XSSFWorkbook(inputStream);

			Sheet sheetDetails = excelHandlingBook.getSheet(SheetName);

			Row rowDetails = sheetDetails.getRow(rowNumber);
			if (rowDetails == null) {
				rowDetails = sheetDetails.createRow(rowNumber);
			}

			Cell cellDetails = rowDetails.createCell(columnNumber);
			cellDetails.setCellType(cellDetails.CELL_TYPE_STRING);
			cellDetails.setCellValue(Value);
			FileOutputStream outputStream = new FileOutputStream(file);
			excelHandlingBook.write(outputStream);
			outputStream.close();
			System.out.println("Successfully writen the details in to Excel");
		} catch (Exception e) {
			throw new AutomationException("Error while writting to excel");
		}
	}
	
	/**
	 * Method excel clear the excel data
	 * @param browser
	 * @throws AutomationException 
	 * @returns void 
	 **/
	public static void clearExcel(String FileName) throws IOException, AutomationException {
		
		try {
			
		File file = new File(System.getProperty("user.dir") + "/src/test/resources/ExcelData/" + FileName + ".xlsx");
		FileInputStream inputStream = new FileInputStream(file);

		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sh1 = wb.getSheetAt(0); // Access the worksheet, so that we can update / modify it.

		XSSFWorkbook newWb = new XSSFWorkbook();
		Sheet newSheet = newWb.createSheet();
		Row newRow = newSheet.createRow(0);
		Iterator<Cell> cellIterator = sh1.getRow(0).cellIterator();
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			Cell newCell = newRow.createCell(cell.getColumnIndex());
			newCell.setCellValue(cell.getStringCellValue());
		}

		FileOutputStream outputStream = new FileOutputStream(
				System.getProperty("user.dir") + "/Resources/ExcelData/" + FileName + ".xlsx");
		newWb.write(outputStream);
		outputStream.close();
	}
		 catch (Exception e) {
				throw new AutomationException("Error while clearing the excel");
			}
	}
}
