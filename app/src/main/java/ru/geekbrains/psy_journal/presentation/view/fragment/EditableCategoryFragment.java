package ru.geekbrains.psy_journal.presentation.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.data.repositories.model.Category;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.EditableListPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.ChangableView;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.Named;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.EditableListsAdapter;
import ru.geekbrains.psy_journal.presentation.view.utilities.ItemTouchHelperCallback;

public class EditableCategoryFragment extends MvpAppCompatFragment implements
	ChangableView,
	Named {

	@BindView(R.id.recycler_all_work) RecyclerView recycler;
	@BindView(R.id.progress_bar) ProgressBar progressBar;

	@InjectPresenter
	EditableListPresenter editableListPresenter;

	protected Unbinder unbinder;
	protected EditableListsAdapter adapter;

	@ProvidePresenter
	EditableListPresenter providePresenter(){
		EditableListPresenter presenter = new EditableListPresenter();
		App.getAppComponent().inject(presenter);
		presenter.getCategory();
		return presenter;
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_all_work, container, false);
		unbinder = ButterKnife.bind(this, view);
		showRecycler();
		return view;
	}

	protected void showRecycler() {
		recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
		recycler.setHasFixedSize(true);
		adapter = new EditableListsAdapter(editableListPresenter.getAdapterPresenter());
		recycler.setAdapter(adapter);
		new ItemTouchHelper(new ItemTouchHelperCallback(adapter)).attachToRecyclerView(recycler);
	}

	@Override
	public void updateRecyclerView() {
		adapter.notifyDataSetChanged();
	}

	@Override
	public void showProgressBar() {
		progressBar.setVisibility(ProgressBar.VISIBLE);
	}

	@Override
	public void hideProgressBar() {
		progressBar.setVisibility(ProgressBar.INVISIBLE);
	}

	@Override
	public String getListName(){
		return "Категория";
	}

	@Override
	public void changeName(Catalog catalog, int position) {
		editableListPresenter.changeNameCategory((Category) catalog, position);
	}

	@Override
	public void remove(Catalog catalog) {
		editableListPresenter.removeCategory((Category) catalog);
	}



	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}
}
