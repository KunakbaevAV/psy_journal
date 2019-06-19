package ru.geekbrains.psy_journal.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.view.fragment.AddWorkFragment;
import ru.geekbrains.psy_journal.view.fragment.AllWorkFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	public static final String TAG_ADD_WORK = "Tag add work";
	public static final String TAG_ALL_WORK = "Tag all work";

	@BindView(R.id.fab) FloatingActionButton fab;
    @BindDrawable(R.drawable.ic_add_circle_outline_white_24dp) Drawable plus;
    @BindDrawable(R.drawable.ic_done_white_24dp) Drawable done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setImageFab();
        if (savedInstanceState == null) {
        	loadFragment(new AllWorkFragment(), TAG_ALL_WORK);
        }
    }

    private void setImageFab() {
        String tag = getTag();
        if (TAG_ADD_WORK.equals(tag) || getString(R.string.OTF).equals(tag) || getString(R.string.TF).equals(tag) ||
                getString(R.string.TD).equals(tag)) fab.setImageDrawable(done);
        else fab.setImageDrawable(plus);
    }

    private String getTag() {
        if (getSupportFragmentManager().getFragments().size() == 0) return null;
        return getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getFragments().size() - 1).getTag();
    }

    @OnClick({R.id.fab})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            Fragment currentFragment = getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getFragments().size() - 1);
            String tag = currentFragment.getTag();
            if (tag == null) return;
            switch (tag) {
                case TAG_ADD_WORK:
                    AddWorkFragment addWorkFragment = (AddWorkFragment) currentFragment;
                    if (!addWorkFragment.isEmptyDeclaredRequest()) {
	                    addWorkFragment.collectAll();
                        openAllWorkFragment();
                    }
                    break;
                case TAG_ALL_WORK:
                    openAddWorkFragment();
                    break;
            }
        }
    }

    private void openAddWorkFragment() {
        getSupportFragmentManager()
            .beginTransaction()
            .add(R.id.frame_master, new AddWorkFragment(), TAG_ADD_WORK)
            .addToBackStack(TAG_ADD_WORK)
            .commit();
        fab.setImageDrawable(done);
    }

    private void openAllWorkFragment() {
	    getSupportFragmentManager().popBackStack(TAG_ADD_WORK, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        loadFragment(new AllWorkFragment(), TAG_ALL_WORK);
        fab.setImageDrawable(plus);
    }

    private void loadFragment(Fragment fragment, String tag){
	    getSupportFragmentManager()
		    .beginTransaction()
		    .replace(R.id.frame_master, fragment, tag)
		    .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setImageFab();
    }
}
