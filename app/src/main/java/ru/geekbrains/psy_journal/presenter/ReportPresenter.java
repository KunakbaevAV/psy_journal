package ru.geekbrains.psy_journal.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import ru.geekbrains.psy_journal.model.database.RoomHelper;
import ru.geekbrains.psy_journal.view.dialogs.adapters.Displayed;
import ru.geekbrains.psy_journal.view.fragment.ReportView;
import ru.geekbrains.psy_journal.view.fragment.adapters.ReportRelated;
import ru.geekbrains.psy_journal.view.fragment.adapters.ReportShown;

@InjectViewState
public class ReportPresenter extends MvpPresenter<ReportView> {

	@Inject	RoomHelper roomHelper;

	private final RecyclePresenter recyclePresenter = new RecyclePresenter();
	private List list;

	public RecyclePresenter getRecyclePresenter() {
		return recyclePresenter;
	}

	public void  initialize(int idOTF, long from, long unto) {
		//Todo здесь метод получения отчета с базы
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
