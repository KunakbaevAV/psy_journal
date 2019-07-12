package ru.geekbrains.psy_journal.presentation.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.psy_journal.data.repositories.model.Functional;
import ru.geekbrains.psy_journal.data.repositories.RoomHelper;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.FunctionView;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.Displayed;

@InjectViewState
public class DialogFunctionPresenter extends MvpPresenter<FunctionView> implements Bindable {

	private final List<Functional> list = new ArrayList<>();
	@Inject
	RoomHelper roomHelper;
	private String title;
	private Disposable disposable;

	public DialogFunctionPresenter() {
	}

	public DialogFunctionPresenter(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void getOTF() {
		disposable = roomHelper.getOTFList()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(otfList -> {
					list.addAll(otfList);
					getViewState().update();
				}, e -> Log.e("getOTF(): ", e.getMessage()));

	}

	public void getTF(int idOTF) {
		disposable = roomHelper.getTFList(idOTF)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(tfs -> {
					list.addAll(tfs);
					getViewState().update();
				}, e -> Log.e("getTF(): ", e.getMessage()));
	}

	public void getTD(int idTF) {
		disposable = roomHelper.getTDList(idTF)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(tds -> {
					list.addAll(tds);
					getViewState().update();
				}, e -> Log.e("getTD: ", e.getMessage()));
	}

	@Override
	public void bindView(Displayed displayed, int position) {
		Functional function = list.get(position);
		displayed.bind(function.getCode(), function.getName());
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	@Override
	public void selectItem(int position) {
		getViewState().openNewFeature(list.get(position));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (disposable != null) disposable.dispose();
	}
}
