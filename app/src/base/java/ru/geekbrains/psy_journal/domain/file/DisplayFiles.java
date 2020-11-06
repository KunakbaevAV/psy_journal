package ru.geekbrains.psy_journal.domain.file;

import java.io.File;
import java.util.List;

public interface DisplayFiles {

	File getCurrentDirectory();

	List<File> showRootDirectory(File rootFolder);
}
