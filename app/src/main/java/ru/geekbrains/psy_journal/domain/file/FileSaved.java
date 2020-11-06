package ru.geekbrains.psy_journal.domain.file;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.IOException;

public interface FileSaved {
    File writeExcelFile(HSSFWorkbook workbook, String nameFile) throws IOException;
}
