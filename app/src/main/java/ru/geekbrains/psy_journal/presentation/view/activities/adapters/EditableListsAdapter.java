package ru.geekbrains.psy_journal.presentation.view.activities.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class EditableListsAdapter extends FragmentPagerAdapter {

	private final List<Fragment> listFragment = new ArrayList<>();
	private final List<String> listTitles = new ArrayList<>();

	public EditableListsAdapter(@NonNull FragmentManager fm) {
		super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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
		return listTitles.get(position);
	}
}
