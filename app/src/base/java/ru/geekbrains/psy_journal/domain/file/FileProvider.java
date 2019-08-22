package ru.geekbrains.psy_journal.domain.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileProvider implements DisplayFiles {

	private File currentDirectory;

    public FileProvider(File currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

	@Override
	public File getCurrentDirectory() {
		return currentDirectory;
	}

	@Override
	public List<File> showRootDirectory(File rootFolder) {
		return new ArrayList<>(Arrays.asList(rootFolder.listFiles()));
	}
}
