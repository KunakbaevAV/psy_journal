package ru.geekbrains.psy_journal.presentation.view.activities;

import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.google.android.material.tabs.TabLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.presentation.view.activities.adapters.EditableCatalogAdapter;

public class EditorActivity extends MvpAppCompatActivity {

	@BindView(R.id.pages) ViewPager pager;
	@BindView(R.id.tabs) TabLayout tabLayout;

	private Unbinder unbinder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editor);
		unbinder = ButterKnife.bind(this);
		pager.setAdapter(new EditableCatalogAdapter(getSupportFragmentManager()));
		tabLayout.setupWithViewPager(pager);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbinder.unbind();
	}
}
