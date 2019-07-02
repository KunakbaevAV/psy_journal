package ru.geekbrains.psy_journal.presenter;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.psy_journal.model.data.Journal;
import ru.geekbrains.psy_journal.model.database.RoomHelper;
import ru.geekbrains.psy_journal.view.fragment.ReportView;
import ru.geekbrains.psy_journal.view.fragment.adapters.ReportRelated;
import ru.geekbrains.psy_journal.view.fragment.adapters.ReportShown;

@InjectViewState
public class ReportPresenter extends MvpPresenter<ReportView> {

	@Inject	RoomHelper roomHelper;

	private final RecyclePresenter recyclePresenter = new RecyclePresenter();
	private List<Journal> list;
	private Disposable disposable;

	public RecyclePresenter getRecyclePresenter() {
		return recyclePresenter;
	}

	public void  initialize(int idOTF, long from, long unto) {
		disposable = roomHelper.getLaborFunctionReport(idOTF, from, unto)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(journals -> list = journals,
				e -> Log.e("initialize: ", e.getMessage()));
	}

	private class RecyclePresenter implements ReportRelated {

		@Override
		public void bindView(ReportShown reportShown, int position) {
//			reportShown.bind();
		}

		@Override
		public int getItemCount() {
			return list.size();
		}

		@Override
		public void selectItem(int position) {

		}
	}
}
