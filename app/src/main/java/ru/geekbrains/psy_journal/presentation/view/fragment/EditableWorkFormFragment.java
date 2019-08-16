package ru.geekbrains.psy_journal.presentation.view.fragment;

import android.content.Context;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.OnClick;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.EditableWorkFormPresenter;
import ru.geekbrains.psy_journal.presentation.view.dialogs.AddCatalogItemDialog;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.EditableListsAdapter;
import ru.geekbrains.psy_journal.presentation.view.utilities.ItemTouchHelperCallback;

public class EditableWorkFormFragment extends EditableCatalogFragment {

	@InjectPresenter EditableWorkFormPresenter presenter;

	@ProvidePresenter
	EditableWorkFormPresenter providePresenter(){
		EditableWorkFormPresenter presenter = new EditableWorkFormPresenter();
		App.getAppComponent().inject(presenter);
		presenter.getWorkForm();
		return presenter;
	}

	@Override
	public String getListName(Context context){
		return context.getResources().getString(R.string.choose_work_form);
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
            dialog.show(getActivity().getSupportFragmentManager(), getListName(getActivity()));
        }
    }
}
