package ru.geekbrains.psy_journal.domain.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.geekbrains.psy_journal.Constants;

public class FileProvider implements DisplayFiles {

	private static final String STORAGE_EMULATED = "/storage/emulated";
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
		return file.getParent().equals(STORAGE_EMULATED);
	}

	@Override
    public List<File> showFiles() {
    	if (isRoot(currentDirectory)){
    		return showRootDirectory(currentDirectory);
	    }
    	return getListFile(currentDirectory);
    }

	@Override
    public List<File> goUp(File currentFolder) {
        currentDirectory = currentFolder.getParentFile();
        return showFiles();
    }

    @Override
    public List<File> goToFolder(File selectFolder) {
        currentDirectory = selectFolder;
        return getListFile(currentDirectory);
    }

    private List<File> getListFile(File directory){
    	List<File> files = new ArrayList<>();
    	files.add(new File(Constants.SUB_LEVEL));
	    files.addAll(Arrays.asList(directory.listFiles()));
    	return files;
    }

	private List<File> showRootDirectory(File currentFolder) {
		return new ArrayList<>(Arrays.asList(currentFolder.listFiles()));
	}
}
