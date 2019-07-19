package ru.geekbrains.psy_journal.data.files;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.psy_journal.data.repositories.model.OTF;
import ru.geekbrains.psy_journal.data.repositories.model.TD;
import ru.geekbrains.psy_journal.data.repositories.model.TF;

public class FileXMLLoader {

	private static final String CODE_OTF = "CodeOTF";
	private static final String CODE_TF = "CodeTF";
	private static final String LABOR_ACTIONS = "LaborActions";
	private static final String NAME_OTF = "NameOTF";
	private static final String NAME_TF = "NameTF";
	private static final String NAME_TD = "LaborAction";
	private static final String CODE_TD = "%s.%d";
	private final LoadableDataBase loadableDataBase;
	private final XmlPullParser parser;
	private int idOTF;
	private int idTF;
	private int idTD;
	private int countTD;
	private String codeTF;

	public FileXMLLoader(LoadableDataBase loadableDataBase, XmlPullParser parser) {
		this.loadableDataBase = loadableDataBase;
		this.parser = parser;
	}

	public FileXMLLoader(LoadableDataBase loadableDataBase) throws XmlPullParserException{
		this.loadableDataBase = loadableDataBase;
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		parser = factory.newPullParser();
	}

	public Completable toParseFile(File file) {
		return Completable.fromAction(() -> {
			parser.setInput(new FileReader(file));
			loadByDefault();
		}).subscribeOn(Schedulers.io());
	}

	public void loadByDefault() throws XmlPullParserException, IOException {
		while (parser.getEventType() != XmlPullParser.END_DOCUMENT){
			if (isFoundTagStart() && isTagMatchesName(CODE_OTF)){
				checkOTF();
			}
			if (isFoundTagStart() && isTagMatchesName(CODE_TF)){
				checkTF();
			}
			if (isFoundTagStart() && isTagMatchesName(LABOR_ACTIONS)){
				parsingTD();
			}
			parser.next();
		}
		loadableDataBase.initDataBase();
	}

	private boolean isTagMatchesName(String name){
		return parser.getName().equals(name);
	}

	private boolean isFoundTagStart() throws XmlPullParserException {
		return parser.getEventType() == XmlPullParser.START_TAG;
	}

	private boolean isFoundText() throws XmlPullParserException {
		return parser.getEventType() == XmlPullParser.TEXT;
	}

	private boolean isFoundTagEnd() throws XmlPullParserException {
		return parser.getEventType() == XmlPullParser.END_TAG;
	}

	private void checkOTF() throws IOException, XmlPullParserException {
		String codeOTF = null, nameOTF = null;
		while (!(isFoundTagEnd() && isTagMatchesName(CODE_OTF))){
			parser.next();
			if (isFoundText()) codeOTF = parser.getText();
		}
		while (!(isFoundTagEnd() && isTagMatchesName(NAME_OTF))){
			parser.next();
			if (isFoundText()) nameOTF = parser.getText();
		}
		loadableDataBase.getOtfList().add(new OTF(++idOTF, codeOTF, nameOTF));
	}

	private void checkTF() throws IOException, XmlPullParserException {
		String nameTF = null;
		while (!(isFoundTagEnd() && isTagMatchesName(CODE_TF))){
			parser.next();
			if (isFoundText()) codeTF = parser.getText();
		}
		while (!(isFoundTagEnd() && isTagMatchesName(NAME_TF))){
			parser.next();
			if (isFoundText()) nameTF = parser.getText();
		}
		loadableDataBase.getTfList().add(new TF(++idTF, codeTF, nameTF, idOTF));
		countTD = 0;
	}

	private void parsingTD() throws XmlPullParserException, IOException {
		while (!(isFoundTagEnd() && isTagMatchesName(LABOR_ACTIONS))){
			parser.next();
			if (isFoundTagStart() && isTagMatchesName(NAME_TD)) checkTD();
		}
	}

	private void checkTD() throws XmlPullParserException, IOException {
		String nameTD = null;
		while (!(isFoundTagEnd() && isTagMatchesName(NAME_TD))) {
			parser.next();
			if (isFoundText()) nameTD = parser.getText();
		}
		String codeTD = createCodeTD(++countTD);
		loadableDataBase.getTdList().add(new TD(++idTD, codeTD, nameTD, idTF));
	}

	private String createCodeTD(int count){
		return String.format(Locale.getDefault(), CODE_TD, codeTF, count);
	}
}
