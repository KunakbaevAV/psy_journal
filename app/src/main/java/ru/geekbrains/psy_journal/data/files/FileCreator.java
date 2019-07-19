package ru.geekbrains.psy_journal.data.files;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import ru.geekbrains.psy_journal.domain.file.FileSaved;

public class FileCreator implements FileSaved {

	private static final String UNABLE_CREATE_DIRECTORY = "невозможно создать директорию отчеты";
	private static final String WRITE_ERROR = "ошибка записи: %s";
	private File directory;

	public FileCreator(File directory) {
		this.directory = directory;
	}

	@Override
	public File writeExcelFile(HSSFWorkbook workbook, String nameFile) throws IOException {
		if (directory == null) throw new IOException(UNABLE_CREATE_DIRECTORY);
		File file = new File(directory, nameFile);
		try (FileOutputStream out = new FileOutputStream(file)) {
			workbook.write(out);
		} catch (IOException e) {
			throw new IOException(String.format(WRITE_ERROR, e.getMessage()));
		}
		return file;
	}
}
