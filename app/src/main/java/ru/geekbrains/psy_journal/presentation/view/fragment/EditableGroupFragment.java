package ru.geekbrains.psy_journal.presentation.view.fragment;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.OnClick;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.EditableGroupPresenter;
import ru.geekbrains.psy_journal.presentation.view.dialogs.AddCatalogItemDialog;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.EditableListsAdapter;
import ru.geekbrains.psy_journal.presentation.view.utilities.ItemTouchHelperCallback;

public class EditableGroupFragment extends EditableCatalogFragment{

	@InjectPresenter EditableGroupPresenter presenter;

	@ProvidePresenter
	EditableGroupPresenter providePresenter(){
		EditableGroupPresenter presenter = new EditableGroupPresenter();
		App.getAppComponent().inject(presenter);
		presenter.getGroup();
		return presenter;
	}

	@Override
	public String getListName(){
		return "Группа";
	}

	protected void showRecycler() {
		super.showRecycler();
		adapter = new EditableListsAdapter(presenter.getAdapterPresenter());
		recycler.setAdapter(adapter);
		new ItemTouchHelperCallback(recycler);
	}

    @OnClick(R.id.add_catalog_item)
    void openAddDialog() {
        AddCatalogItemDialog dialog = new AddCatalogItemDialog(presenter);
        if (getActivity() != null) {
            dialog.show(getActivity().getSupportFragmentManager(), "Добавление в справочник " + getListName());
        }
    }
}
