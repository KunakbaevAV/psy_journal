package ru.geekbrains.psy_journal.presentation.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.data.repositories.model.Journal;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.AllWorkPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.AllWorkView;
import ru.geekbrains.psy_journal.presentation.view.activities.MainActivity;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.AdapterAllWork;
import ru.geekbrains.psy_journal.presentation.view.utilities.ItemTouchHelperCallback;

import static ru.geekbrains.psy_journal.Constants.TAG_ADD_WORK;

public class AllWorkFragment extends AbstractFragment implements AllWorkView {

	@InjectPresenter AllWorkPresenter allWorkPresenter;

	private AdapterAllWork adapterAllWork;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_all_work, container, false);
		unbinder = ButterKnife.bind(this, view);
		App.getAppComponent().inject(allWorkPresenter);
		showRecycler();
		return view;
	}

	protected void showRecycler() {
		super.showRecycler();
		adapterAllWork = new AdapterAllWork(allWorkPresenter.getRecyclerAllWorkPresenter());
		recycler.setAdapter(adapterAllWork);
        new ItemTouchHelperCallback(recycler);
	}

	@Override
	public void updateRecyclerView() {
		adapterAllWork.notifyDataSetChanged();
	}

	@Override
	public void deleteItemRecycler(int position) {
		adapterAllWork.notifyItemRemoved(position);
	}

	@Override
	public void showToast(String message) {
		if (getActivity() == null) return;
		Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void openScreenUpdateJournal(Journal journal) {
		MainActivity activity = (MainActivity) getActivity();
		if (activity == null) return;
		activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_master, AddWorkFragment.newInstance(journal), TAG_ADD_WORK).commit();
		activity.setImageFabForTag(TAG_ADD_WORK);
	}
}
