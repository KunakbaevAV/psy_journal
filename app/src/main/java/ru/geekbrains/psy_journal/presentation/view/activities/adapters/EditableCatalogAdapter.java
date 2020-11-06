package ru.geekbrains.psy_journal.presentation.view.activities.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.psy_journal.presentation.view.fragment.EditableCategoryFragment;
import ru.geekbrains.psy_journal.presentation.view.fragment.EditableGroupFragment;
import ru.geekbrains.psy_journal.presentation.view.fragment.EditableWorkFormFragment;
import ru.geekbrains.psy_journal.presentation.view.fragment.Named;

public class EditableCatalogAdapter extends FragmentPagerAdapter {

	private final List<Fragment> listFragment = new ArrayList<>();
	private final Context context;

	public EditableCatalogAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
		super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
		this.context = context;
		listFragment.add(new EditableCategoryFragment());
		listFragment.add(new EditableGroupFragment());
		listFragment.add(new EditableWorkFormFragment());
		notifyDataSetChanged();
	}

	@NonNull
	@Override
	public Fragment getItem(int position) {
		return listFragment.get(position);
	}

	@Override
	public int getCount() {
		return listFragment.size();
	}

	@Nullable
	@Override
	public CharSequence getPageTitle(int position) {
		return ((Named)listFragment.get(position)).getListName(context);
	}
}
