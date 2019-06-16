package ru.geekbrains.psy_journal.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.psy_journal.model.data.Functional;
import ru.geekbrains.psy_journal.model.data.TD;
import ru.geekbrains.psy_journal.view.dialogs.Updated;
import ru.geekbrains.psy_journal.view.dialogs.adapters.Displayed;

@InjectViewState
public class DialogFunctionPresenter extends MvpPresenter<Updated> implements Bindable {

	private final List<Functional> list = new ArrayList<>();
	private Settable settable;
	private String title;

	public DialogFunctionPresenter(Settable settable, String title, int id) {
		this.settable = settable;
		this.title = title;
		switch (title){
			case "OTF":
				getOTF();
				break;
			case "TF":
				getTF(id);
				break;
			case "TD":
				getTD(id);
				break;
		}

	}

	private void getOTF(){
		Disposable disposable = settable.getOTF()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(otfList -> {
					list.addAll(otfList);
					getViewState().update();
				}, e -> Log.e("getOTF(): ", e.getMessage()));

	}

	private void getTF(int idOTF){
		Disposable disposable = settable.getTF(idOTF)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(tfs -> {
					list.addAll(tfs);
					getViewState().update();
				}, e -> Log.e("getTF(): ", e.getMessage()));
	}

	private void getTD(int idTF){
		Disposable disposable = settable.getTD(idTF)
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
		Functional function = list.get(position);
		switch (title){
			case "OTF":
				settable.openNewFunction("TF", function.getId());
				break;
			case "TF":
				settable.openNewFunction("TD", function.getId());
				break;
			case "TD":
				settable.saveTD((TD) function);
				break;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		settable = null;
	}
}
