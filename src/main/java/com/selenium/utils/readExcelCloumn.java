package com.selenium.utils;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class readExcelCloumn {

	static String filepath;
	static String sheetName;
	static Connection connection = null;
	static Recordset recordset = null;

	public readExcelCloumn(String file, String sheetN) {

		filepath = file;
		sheetName = sheetN;
		Fillo fillo = new Fillo();
		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */

	}

	// By Pasing Column Name
	public static String excelValue(String columnName) throws FilloException {
		String value1 = null;
		// String file = this.filepath;
		// String
		// String file = System.setProperty("user.dir")+"./DataFolder/NPS.Xlsx;
		// EXCELREADPAGE el = new EXCELREADPAGE(null);
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(filepath);
		String strQuery = "Select * from " + sheetName + "WHERE TestCaseIdentifier='Sanity_TCI_NPSI'";
		Recordset rs = connection.executeQuery(strQuery);
//		System.out.println(rs.getCount());

//		while(rs.next()) {
//			rs.next();
		System.out.println(rs.getField(columnName));
//			value1 = rs.getField(columnName);
//		}

		rs.close();
		connection.close();
		return value1;
	}

//	2 :1 st return records present in excelsheet

	public static Recordset getData(String excelPath, String sheetName, String TCid) throws FilloException {
		Fillo fillo = new Fillo();
		Connection connection = null;
		Recordset recordset = null;

		connection = fillo.getConnection(excelPath);
		String Query = "Select * from " + sheetName + "WHERE TestCaseIdentifier='" + TCid + "'"
				+ "and ExecutionFlag='yes'";
		recordset = connection.executeQuery(Query);
		return recordset;
	}

	public static void insertData(String excelPath, String sheetName, String column, String value)
			throws FilloException {
		Fillo fillo = new Fillo();
		Connection connection1;
		connection1 = fillo.getConnection(excelPath);
		String Query = "INSERT INTO " + sheetName + "(" + column + ") Values('" + value + "')";
		connection1.executeQuery(Query);
		connection1.close();
	}

	public static void UpdateData(String excelPath, String sheetName, String colname, String value, String whcolname,
			String whvalue) throws FilloException {
		Fillo fillo = new Fillo();
		Connection connection2;
		connection2 = fillo.getConnection(excelPath);
		String Query = "INSERT INTO " + sheetName + "Set" + colname + "=" + value + "'Where " + whcolname + "="
				+ whvalue + "'";
		connection2.executeQuery(Query);
		connection2.close();
	}

//	close Connection
	public static void closeConnection(String excelPath) throws FilloException {
		Fillo fillo = new Fillo();
		Connection connection = null;
		Recordset recordset = null;
		connection = fillo.getConnection(excelPath);
//		recordset.close();
		connection.close();
	}

	public static void updateReq(String value, String TCID) throws FilloException {
		String Value = null;
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(filepath);
		String Query = null;
		try {
//			String strQuery ="Update \"EmployeeDetails\" Set ReqNo = '123478' where (TestCaseIdentifier='Sanity_TC1_NPS1' and \"Flow\"='Positive')";
			Query = "Update " + sheetName + "set ReqNo= '" + value + " ' WHERE TestCaseIdentifier= '" + TCID + "'";
		} catch (Exception e) {
			System.out.println("Memory Full");
		}
		connection.executeQuery(Query);
		connection.close();

	}

}
