package ru.geekbrains.psy_journal.data.database;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.data.files.LoadableDataBase;
import ru.geekbrains.psy_journal.data.repositories.Loadable;
import ru.geekbrains.psy_journal.data.repositories.model.Category;
import ru.geekbrains.psy_journal.data.repositories.model.Group;
import ru.geekbrains.psy_journal.data.repositories.model.OTF;
import ru.geekbrains.psy_journal.data.repositories.model.TD;
import ru.geekbrains.psy_journal.data.repositories.model.TF;
import ru.geekbrains.psy_journal.data.repositories.model.WorkForm;

import static ru.geekbrains.psy_journal.Constants.CODE_OF_OTHER_ACTIVITY_SUFFIX;
import static ru.geekbrains.psy_journal.Constants.NAME_OF_OTHER_ACTIVITY;

/**
 * класс загрузки данных в базу
 */
public class DataBaseLoader implements LoadableDataBase {

    private final List<OTF> otfList = new ArrayList<>();
    private final List<TF> tfList = new ArrayList<>();
    private final List<TD> tdList = new ArrayList<>();
    private final List<Category> categoryList = new ArrayList<>();
    private final List<Group> groupList = new ArrayList<>();
    private final List<WorkForm> workFormList = new ArrayList<>();
	private final Loadable loadable;
	private final Context context;

	@Override
	public List<OTF> getOtfList() {
		return otfList;
	}

	@Override
	public List<TF> getTfList() {
		return tfList;
	}

	@Override
	public List<TD> getTdList() {
		return tdList;
	}

	public DataBaseLoader(Context context, Loadable loadable) {
		this.context = context;
        this.loadable = loadable;
    }

	@Override
    public void initDataBase() {
        loadable.initializeOTF(otfList);
        loadable.initializeTF(tfList);
        loadable.initializeTD(tdList);

        initCategoryList();
        loadable.initializeCategory(categoryList);
        initGroupList();
        loadable.initializeGroup(groupList);
        initWorkFormList();
        loadable.initializeWorkForms(workFormList);
        addOtherWorkActivities();
    }

	//не использовать цикл foreach
    private void initWorkFormList() {
	    int id = 0;
	    String[] workForms = context.getResources().getStringArray(R.array.workForm);
	    for (int i = 0; i < workForms.length; i++) {
		    workFormList.add(new WorkForm(workForms[i]));
	    }
    }

	//не использовать цикл foreach
    private void initGroupList() {
	    int id = 0;
	    String[] groups = context.getResources().getStringArray(R.array.group);
	    for (int i = 0; i < groups.length; i++) {
		    groupList.add(new Group(groups[i]));
	    }
    }

	//не использовать цикл foreach
    private void initCategoryList() {
		int id = 0;
		String[] categories = context.getResources().getStringArray(R.array.category);
	    for (int i = 0; i < categories.length; i++) {
		    categoryList.add(new Category(categories[i]));
	    }
    }

    //метод добавления иной деятельности в трудовые функции
    private void addOtherWorkActivities() {
        int tfSize = tfList.size();
        for (int i = 0; i < otfList.size(); i++) {
            String codeOtherWorkActivity = getCodeOtherWorkActivity(i);
            tfList.add(new TF(++tfSize,
                    codeOtherWorkActivity,
                    NAME_OF_OTHER_ACTIVITY,
                    i + 1));
            addOtherTD(tfSize, codeOtherWorkActivity);
        }
    }

    private String getCodeOtherWorkActivity(int i) {
        return otfList.get(i).getCode().concat(CODE_OF_OTHER_ACTIVITY_SUFFIX);
    }

    private void addOtherTD(int idTF, String codeOtherWorkActivity) {
        int tdSize = tdList.size();
        tdList.add(new TD(++tdSize,
                codeOtherWorkActivity,
                NAME_OF_OTHER_ACTIVITY,
                idTF));
    }
}
