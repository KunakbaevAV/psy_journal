package ru.geekbrains.psy_journal.domain.file;

import java.io.File;
import java.util.List;

public interface DisplayFiles {
	
	List<File> showFiles();
	List<File> goUp(File currentFolder);
	List<File> goToFolder(File selectFolder);
}
