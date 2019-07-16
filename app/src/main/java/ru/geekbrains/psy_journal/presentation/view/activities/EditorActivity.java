package ru.geekbrains.psy_journal.presentation.view.activities;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.presentation.view.activities.adapters.EditableListsAdapter;

public class EditorActivity extends MvpAppCompatActivity {

	@BindView(R.id.pages) ViewPager pager;
	@BindView(R.id.tabs) TabLayout tabLayout;

	private EditableListsAdapter editableListsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editor);
		ButterKnife.bind(this);
		editableListsAdapter = new EditableListsAdapter(getSupportFragmentManager());
		pager.setAdapter(editableListsAdapter);
		tabLayout.setupWithViewPager(pager);
	}
}
