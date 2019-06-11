package ru.geekbrains.psy_journal.model.file;

import android.os.Environment;
import android.util.Log;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ru.geekbrains.psy_journal.model.data.Journal;

public class Report {

 private final String[] headlines = new String[]{"date", "day", "work direction", "category", "class/group",
	 "FIO", "people", "declared request", "real request", "work form", "hours", "comment"};
 private final ArrayList<Journal> journals;
 private int numberRow;

	public Report(ArrayList<Journal> journals) {
		this.journals = journals;
	}

	public void createInInternal(File file){
		writeDown(create(), file);
	}

	public void createInExternal(){
		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Reports");
		if (!file.exists() && !file.mkdirs()) throw new RuntimeException("directory Reports not created");
		writeDown(create(), file);
	}

	private XSSFWorkbook create(){
		XSSFWorkbook workbook = new XSSFWorkbook();
		String nameSheet = String.format(Locale.getDefault(),"report %s", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
		XSSFSheet sheet = workbook.createSheet(nameSheet);
		createHeadlines(sheet);
		for (int i = 0; i < journals.size(); i++) {
			fillInWithData(sheet, journals.get(i));
		}
		return workbook;
	}

	private void writeDown(XSSFWorkbook workbook, File file) {
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault());
		String nameFile = String.format(Locale.getDefault(),"%s/report %s.xlsx", file.getName(), dateFormat.format(new Date()));
		try (FileOutputStream out = new FileOutputStream(nameFile)) {
			workbook.write(out);
		} catch (IOException e) {
			Log.e("writeDown: ", e.getMessage());
		}
	}

	private void createHeadlines(XSSFSheet sheet){
		Row row = sheet.createRow(numberRow++);
		for (int i = 0; i < headlines.length; i++) {
			row.createCell(i).setCellValue(headlines[i]);
		}
	}

	private void fillInWithData(XSSFSheet sheet, Journal journal){
		int column = 0;
		Row row = sheet.createRow(numberRow++);
		row.createCell(column++).setCellValue(DateFormat.getDateInstance(DateFormat.SHORT).format(new Date(journal.getDate())));
		row.createCell(column++).setCellValue(journal.getDayOfWeek());
//		row.createCell(column++).setCellValue(journal.getIdTd().getCode()); //FIXME Исправить огласно изменениям в классе Journal (объекты заменены на их id)
//		row.createCell(column++).setCellValue(journal.getIdCategory().getName());//FIXME Исправить огласно изменениям в классе Journal (объекты заменены на их id)
//		row.createCell(column++).setCellValue(journal.getIdGroup().getName());//FIXME Исправить огласно изменениям в классе Journal (объекты заменены на их id)
		row.createCell(column++).setCellValue(journal.getName());
		row.createCell(column++).setCellValue(String.valueOf(journal.getQuantityPeople()));
		row.createCell(column++).setCellValue(journal.getDeclaredRequest());
		row.createCell(column++).setCellValue(journal.getRealRequest());
//		row.createCell(column++).setCellValue(journal.getIdWorkForm().getName());//FIXME Исправить огласно изменениям в классе Journal (объекты заменены на их id)
		row.createCell(column++).setCellValue(String.valueOf(journal.getWorkTime()));
		row.createCell(column).setCellValue(journal.getComment());
	}
}
