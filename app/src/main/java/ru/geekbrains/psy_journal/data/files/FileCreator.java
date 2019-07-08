package ru.geekbrains.psy_journal.data.files;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import ru.geekbrains.psy_journal.domain.file.FileSaved;

public class FileCreator implements FileSaved {

	private File directory;

	@Inject
	public FileCreator(File directory) {
		this.directory = directory;
	}

	@Override
	public void writeExcelFile(XSSFWorkbook workbook) throws IOException {
		if (directory == null) throw new IOException("directory Reports not created");
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault());
		String nameFile = String.format(Locale.getDefault(),"%s/report %s.xlsx", directory.getName(), dateFormat.format(new Date()));
		try (FileOutputStream out = new FileOutputStream(nameFile)) {
			workbook.write(out);
		} catch (IOException e) {
			throw new IOException(String.format("writeDown: %s", e.getMessage()));
		}
	}
}
