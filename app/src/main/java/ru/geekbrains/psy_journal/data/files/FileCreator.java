package ru.geekbrains.psy_journal.data.files;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.inject.Inject;
import ru.geekbrains.psy_journal.domain.file.FileSaved;

/** здесь не должно быть импортов android
 *
 */
public class FileCreator implements FileSaved {

	private static final String PATTERN_NAME_REPORT = "dd_MM_yy HH:mm";
	private File directory;

	@Inject
	public FileCreator(File directory) {
		this.directory = directory;
	}

	@Override
	public File writeExcelFile(HSSFWorkbook workbook) throws IOException {
		if (directory == null) throw new IOException("невозможно создать директорию отчеты");
		SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_NAME_REPORT, Locale.getDefault());
		File file = new File(String.format("%s/отчет %s.xls", directory.getPath(), dateFormat.format(new Date())));
		try (FileOutputStream out = new FileOutputStream(file)) {
			workbook.write(out);
		} catch (IOException e) {
			throw new IOException(String.format("ошибка записи: %s", e.getMessage()));
		}
		return file;
	}
}
