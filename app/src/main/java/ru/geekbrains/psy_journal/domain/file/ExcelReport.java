package ru.geekbrains.psy_journal.domain.file;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import ru.geekbrains.psy_journal.Constants;
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
	public File create(List<ReportingJournal> journals) throws IOException {
		numberRow = 0;
		HSSFWorkbook workbook = new HSSFWorkbook();
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.PATTERN_DATE, Locale.getDefault());
		String nameSheet = dateFormat.format(new Date());
		HSSFSheet sheet = workbook.createSheet(nameSheet);
		createHeadlines(sheet);
		for (int i = 0; i < journals.size(); i++) {
			fillInWithData(sheet, journals.get(i));
		}
		return fileSaved.writeExcelFile(workbook);
	}

	private void createHeadlines(HSSFSheet sheet){
		Row row = sheet.createRow(numberRow++);
		for (int i = 0; i < headlines.length; i++) {
			row.createCell(i).setCellValue(headlines[i]);
		}
	}

	private void fillInWithData(HSSFSheet sheet, ReportingJournal journal){
		int column = 0;
		Row row = sheet.createRow(numberRow++);
		row.createCell(column++).setCellValue(journal.getDate());
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
