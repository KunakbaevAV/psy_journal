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
	public boolean isRoot(File file) {
		return file.getParent() == null;
	}

	@Override
    public List<File> showFiles() {
        return new ArrayList<>(Arrays.asList(currentDirectory.listFiles()));
    }

    @Override
    public List<File> goUp(File currentFolder) {
    	if (isRoot(currentFolder)) return null;
        currentDirectory = currentFolder.getParentFile();
        return new ArrayList<>(Arrays.asList(currentDirectory.listFiles()));
    }

    @Override
    public List<File> goToFolder(File selectFolder) {
        currentDirectory = selectFolder;
        return new ArrayList<>(Arrays.asList(currentDirectory.listFiles()));
    }
}
