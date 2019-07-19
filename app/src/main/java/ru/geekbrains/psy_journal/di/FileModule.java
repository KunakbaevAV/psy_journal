package ru.geekbrains.psy_journal.di;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.domain.file.CreatedByExcel;
import ru.geekbrains.psy_journal.domain.file.ExcelReport;
import ru.geekbrains.psy_journal.domain.file.FileSaved;
import ru.geekbrains.psy_journal.data.files.FileCreator;

@Module
class FileModule {

	private Context context;

	FileModule(Context context) {
		this.context = context;
	}

	@Singleton
	@Provides
	File getDirectory(){
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
		if (!file.exists() && !file.mkdirs()) return null;
		return file;
	}

	@Singleton
	@Provides
	String[] getHeadlines(){
		return context.getResources().getStringArray(R.array.headlines);
	}

	@Singleton
	@Provides
	FileSaved provideFileCreator(File directory){
		return new FileCreator(directory);
	}

	@Singleton
	@Provides
	CreatedByExcel provideExcelReport(String[] headlines, FileSaved fileSaved){
		return new ExcelReport(headlines, fileSaved);
	}
}
