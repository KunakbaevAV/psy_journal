package ru.geekbrains.psy_journal.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.view.fragment.AddWorkFragment;
import ru.geekbrains.psy_journal.view.fragment.AddWorkView;
import ru.geekbrains.psy_journal.view.fragment.AllWorkFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindDrawable(R.drawable.ic_add_circle_outline_white_24dp)
    Drawable plus;
    @BindDrawable(R.drawable.ic_done_white_24dp)
    Drawable done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setImageFab();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_master, new AllWorkFragment(), "Tag all work")
                    .commit();
        }
    }

    private void setImageFab() {
        String tag = getTag();
        if ("Tag add work".equals(tag) || "Tag OTF".equals(tag) || "Tag TF".equals(tag) ||
                "Tag TD".equals(tag)) fab.setImageDrawable(done);
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
                case "Tag add work":
                    AddWorkFragment addWorkFragment = (AddWorkFragment) currentFragment;
                    if (!addWorkFragment.isEmptyDeclaredRequest()) {
                        openAddWorkFragment((AddWorkView) currentFragment);
                        break;
                    }
                    break;
                case "Tag all work":
                    openAllWorkFragment();
                    break;
            }
        }
    }

    private void openAllWorkFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_master, new AddWorkFragment(), "Tag add work")
                .addToBackStack(null)
                .commit();
        fab.setImageDrawable(done);
    }

    private void openAddWorkFragment(AddWorkView currentFragment) {
        currentFragment.collectAll();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_master, new AllWorkFragment(), "Tag all work")
                .commit();
        fab.setImageDrawable(plus);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setImageFab();
    }
}
