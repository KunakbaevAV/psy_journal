package ru.geekbrains.psy_journal.presentation.view.activities;

import android.net.Uri;

import java.io.File;
import java.util.ArrayList;

public interface SelectableFile {
    void selectFileXML(File file);
    void selectReportFiles(ArrayList<Uri> files);
}
