package ru.geekbrains.psy_journal.domain.file;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import ru.geekbrains.psy_journal.domain.models.ReportingJournal;

public class ExcelReport implements CreatedByExcel{

	private final FileSaved fileSaved;
	private final String[] headlines;
	private int numberRow;

	@Inject
	public ExcelReport(String[] headlines, FileSaved fileSaved) {
		this.headlines = headlines;
		this.fileSaved = fileSaved;
	}

	@Override
	public void create(List<ReportingJournal> journals) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		String nameSheet = String.format(Locale.getDefault(),"report %s", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
		XSSFSheet sheet = workbook.createSheet(nameSheet);
		createHeadlines(sheet);
		for (int i = 0; i < journals.size(); i++) {
			fillInWithData(sheet, journals.get(i));
		}
		fileSaved.writeExcelFile(workbook);
	}

	private void createHeadlines(XSSFSheet sheet){
		Row row = sheet.createRow(numberRow++);
		for (int i = 0; i < headlines.length; i++) {
			row.createCell(i).setCellValue(headlines[i]);
		}
	}

	private void fillInWithData(XSSFSheet sheet, ReportingJournal journal){
		int column = 0;
		Row row = sheet.createRow(numberRow++);
		row.createCell(column++).setCellValue(DateFormat.getDateInstance(DateFormat.SHORT).format(new Date(journal.getDate())));
		row.createCell(column++).setCellValue(journal.getDayOfWeek());
		row.createCell(column++).setCellValue(journal.getCodeTd());
		row.createCell(column++).setCellValue(journal.getNameCategory());
		row.createCell(column++).setCellValue(journal.getNameGroup());
		row.createCell(column++).setCellValue(journal.getName());
		row.createCell(column++).setCellValue(String.valueOf(journal.getQuantityPeople()));
		row.createCell(column++).setCellValue(journal.getDeclaredRequest());
		row.createCell(column++).setCellValue(journal.getRealRequest());
		row.createCell(column++).setCellValue(journal.getNameWorkForm());
		row.createCell(column++).setCellValue(String.valueOf(journal.getWorkTime()));
		row.createCell(column).setCellValue(journal.getComment());
	}
}
