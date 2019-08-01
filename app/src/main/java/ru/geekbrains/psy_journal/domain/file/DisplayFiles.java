package ru.geekbrains.psy_journal.domain.file;

import java.io.File;
import java.util.List;

public interface DisplayFiles {

	File getCurrentDirectory();

	boolean isRoot(File file);

    List<File> showFiles();

	List<File> showFiles(File currentFolder);

    List<File> goUp(File currentFolder);

    List<File> goToFolder(File selectFolder);
}
