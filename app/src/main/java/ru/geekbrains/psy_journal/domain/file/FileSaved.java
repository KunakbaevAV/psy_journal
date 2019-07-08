package ru.geekbrains.psy_journal.domain.file;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public interface FileSaved {
	void writeExcelFile(XSSFWorkbook workbook) throws IOException;
}
