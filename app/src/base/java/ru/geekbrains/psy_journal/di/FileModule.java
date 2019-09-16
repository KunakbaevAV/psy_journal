package ru.geekbrains.psy_journal.di;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.data.files.FileCreator;
import ru.geekbrains.psy_journal.domain.file.CreatedByExcel;
import ru.geekbrains.psy_journal.domain.file.ExcelReport;
import ru.geekbrains.psy_journal.domain.file.FileSaved;

@Module
class FileModule {

	private final Context context;

	FileModule(Context context) {
		this.context = context;
	}

	@Named(Constants.REPORTS)
	@Singleton
	@Provides
	File provideReportsDirectory(){
		String nameDirectory = context.getString(R.string.reports);
		File file = new File(context.getFilesDir(), nameDirectory);
		if (!file.exists()){
			file.mkdir();
		}
		return file;
	}

	@Singleton
	@Provides
	String[] getHeadlines(){
		return context.getResources().getStringArray(R.array.headlines);
	}

	@Singleton
	@Provides
	FileSaved provideFileCreator(@Named(Constants.REPORTS) @NonNull File directory){
		return new FileCreator(directory);
	}

	@Singleton
	@Provides
	CreatedByExcel provideExcelReport(String[] headlines, FileSaved fileSaved){
		return new ExcelReport(headlines, fileSaved);
	}
}
