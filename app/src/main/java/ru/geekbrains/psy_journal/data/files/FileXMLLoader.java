package ru.geekbrains.psy_journal.data.files;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import ru.geekbrains.psy_journal.data.repositories.model.OTF;

public class FileXMLLoader {

	private XmlPullParser parser;
	private int idOTF;
	private int idTF;
	private int idTD;
	private int idWorkForm;
	private int idGroup;
	private int idCategory;

	public FileXMLLoader() throws XmlPullParserException{
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		parser = factory.newPullParser();
	}

	public void toParseFile(File file) throws IOException, XmlPullParserException {
		parser.setInput(new FileReader(file));
		while (parser.getEventType() != XmlPullParser.END_DOCUMENT){

			parser.next();
		}
	}

	private void checkOTF() throws IOException, XmlPullParserException {
		OTF otf;
		if (parser.getName().equals("CodeOTF")){
			otf = new OTF(++idOTF);
			otf.setCode(parser.getText());
			parser.next();
			if (parser.getName().equals("NameOTF")) {
				otf.setName(parser.getText());
			}
		}
	}
}
