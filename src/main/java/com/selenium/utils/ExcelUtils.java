package com.selenium.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	final static Logger logger = Logger.getLogger(ExcelUtils.class);

	@SuppressWarnings("description")
	public static boolean isRowEmptyInExcel(Row row) {
		try {
			for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
				Cell cell = row.getCell(c);
				if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
					return false;
				}
			}

		} catch (Exception e) {
		}
		return true;
	}

//	return total valid col number
	@SuppressWarnings("description")
	public static int returnToolcol(Row row) {
		int totalcol = 1;
		try {
			for (int c = row.getFirstCellNum(); c < row.getLastCellNum() - 1; c++) {
				Cell cell = row.getCell(c);
				if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
					totalcol++;
				}
			}
		} catch (Exception e) {
//			System.out.println("col Exception "+e.getMessage());
		}
		return totalcol;
	}
	
	public static int returnTotalExcelRow(XSSFSheet Wsheet_obj) {
		int introw = 0;
//		Row Count Operation
		int Row_c = Wsheet_obj.getLastRowNum()+1; // Total number of rows
		//Loop through all the rows in the excel sheet and check if there is a blank row if there ia a blank row then reduces the count of the rows
		for(int f =1;f<Row_c;f++) {
			boolean rw_rs = isRowEmptyInExcel(Wsheet_obj.getRow(f));
			
			if (rw_rs != true) {
				introw++;
			}
		}
		return introw;
		
	}
	
	//Return Excel Column Heading
	public static ArrayList<String> returnColumnHeadingFromExcel(String file, String sheetname){
		XSSFWorkbook Workbook_obj = null;
		ArrayList<String > labels = new ArrayList<String>();
		
		try {
			FileInputStream FIS = new FileInputStream(file);
			Workbook_obj = new XSSFWorkbook(FIS);
			XSSFSheet sheet_obj = Workbook_obj.getSheet(sheetname);
//			sorting all labels in arraylist
//			sorting columns
			
			XSSFRow row_obj = sheet_obj.getRow(0);
			int cellnum = row_obj.getLastCellNum();
			
			for(int i=0;i<cellnum;i++) {
				XSSFCell cellobj = row_obj.getCell(i);
				String label = cellobj.getStringCellValue();
				labels.add(label);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Workbook_obj.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return labels;
	}
	
	public static void createReportWithColumnHeader(String file, String sheetname, ArrayList<String> colNam) {
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet spreadsheet;
		try {
			
			boolean fileexist = isFileExist();
			
			if (fileexist != false) {
				logger.info(file+"exit.. so deleting file..");
				File difile = new File(file);
				difile.delete();
			}
			XSSFRow row;
			logger.info(file + "creating a new file");
			System.out.println(file+"Creating a new file");
			
			workbook = new XSSFWorkbook();
			//creating a blank sheet
			spreadsheet = workbook.createSheet(sheetname);
			int rowid = 0;
			row = spreadsheet.createRow(rowid);
			
			for (int i=0;i<colNam.size();i++) {
				String colName= colNam.get(i).trim();
				System.out.println("Cloumn Name :"+colName);
				Cell cell = row.getCell(i);
				if (cell == null) {
					cell=row.createCell(i);
				}
				cell.setCellValue(colName);
			}
			
//			Write the workbook is file system
			
			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
			System.out.println(file+" Written Successfully");
		} catch (Exception e) {
			logger.error("createReportWithColumnHeader error ::"+ e);
		}
		
	}

//	return column label heading cell number
	public static int returnColumnHeadingNumberFromExcel(String file, String sheetname, ArrayList<String> colName) {
		
		XSSFWorkbook workbook_obj = null;
		Interger colNm = null;
		try {
			FileInputStream FIS = new FileInputStream(file);
			workbook_obj = new XSSFWorkbook(FIS);
			XSSFSheet sheet_obj = workbook_obj.getSheet(sheetname);
//			sorting all labels in arraylist
//			sorting columns
			
			XSSFRow row_obj = sheet_obj.getRow(0);
			int cellnum = row_obj.getLastCellNum();
			
			for(int i=0;i<cellnum;i++) {
				XSSFCell cellobj = row_obj.getCell(i);
				String label = cellobj.getStringCellValue();
				if (colName.equalsIgnoreCase(label)){
					colNm =i;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				workbook_obj.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return colNm;
		
	}
	
//	  Store Labels and cell number in a map
	
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
