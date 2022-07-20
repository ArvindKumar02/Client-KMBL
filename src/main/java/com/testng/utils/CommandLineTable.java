package com.testng.utils;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.selenium.init.Browser;

public class CommandLineTable {

	private static final String HORIZONTAL_SEP = "-";
	private String verticalSep;
	private String joinSep;
	private String[] headers;

	private List<String> rows = new ArrayList<>();
	private boolean rightAlign;

	public CommandLineTable(boolean rightAlign) {
		this.rightAlign = rightAlign;
	}

	public void setShowVerticalLines(boolean showVerticalLines) {
		verticalSep = showVerticalLines ? "|" : "";
		joinSep = showVerticalLines ? "+" : " ";

	}

	public void setHeaders(String... headers) {
		this.headers = headers;
	}

	public void addRow(String... cells) {
		rows.add(cells);
	}

	public void Print() {
		int[] maxWidth = headers != null ? Array.stream(headers).mapToInt(String::length).toArray() : null;

		for (String[] cells : rows) {
			if (maxWidth == null) {
				maxWidth = new int[cells.length];
			}

			if (cells.length != maxWidth.length) {
				throw new IllegalArgumentException("Number of row-cells and headers should be consistent");
			}
			for (int i = 0; i < cells.length; i++) {
				maxWidth[i] = Math.max(maxWidth[i], cells[i].length());
			}
		}

		if (headers != null) {
			printLine(maxWidth);
			printRow(headers, maxWidth);
			printLine(maxWidth);
		}

		for (String[] cells : rows) {
			printRow(cells, maxWidth);
		}
		if (headers != null) {
			printLine(maxWidth);
		}
	}

	public void printLine(int[] columnWidth) {
		for (int i = 0; i < columnWidth.length; i++) {
			String line = String.join("", Collections.nCopies(columnWidth[i], verticalSep.length()+1, HORIZONTAL_SEP));
			
			System.out.println(joinSep + line +(i == columnWidth.length -1 ? joinSep : ""));
		}
		System.out.println();

	}

	public void printRow(String[] cells, int[] maxWidth) {
		for(int i=0;i< cells.length;i++) {
			String s = cells[i];
			String verStrTemp =i ==cells.length -1 ? verticalSep : "";
			if (rightAlign) {
				System.out.printf("%s %" +maxWidth[i]+"s %s",verticalSep,s,verStrTemp);
			} else {
				System.out.printf("%s %-" +maxWidth[i]+"s %s",verticalSep,s,verStrTemp);
			}
		}
		System.out.println();
	}

}
