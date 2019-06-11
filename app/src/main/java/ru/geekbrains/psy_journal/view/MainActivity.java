package ru.geekbrains.psy_journal.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
import ru.geekbrains.psy_journal.view.fragment.Added;
import ru.geekbrains.psy_journal.view.fragment.AllWorkFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	@BindView(R.id.fab) FloatingActionButton fab;
	@BindDrawable(R.drawable.ic_add_circle_outline_white_24dp) Drawable plus;
	@BindDrawable(R.drawable.ic_done_white_24dp) Drawable done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
	    ButterKnife.bind(this);
	    if ("Tag add work".equals(getTag())) fab.setImageDrawable(done);
	    else fab.setImageDrawable(plus);
        if(savedInstanceState == null){
        	getSupportFragmentManager()	    else fab.setImageDrawable(plus);
        if(savedInstanceState == null){
        	getSupportFragmentManager()
		        .beginTransaction()
		        .replace(R.id.frame_master, new AllWorkFragment(), "Tag all work")
		        .commit();
        }
    }

    private String getTag(){
    	if (getSupportFragmentManager().getFragments().size() == 0) return null;
    	return getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getFragments().size() - 1).getTag();
    }

    @OnClick({R.id.fab})
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.fab){
			Fragment currentFragment = getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getFragments().size() - 1);
			String tag = currentFragment.getTag();
			if (tag == null) return;
			Fragment fragment = null;
			switch (tag){
				case "Tag add work":
					Added added = (Added) currentFragment;
					added.collectAll();
					fragment = new AllWorkFragment();
					tag = "Tag all work";
					fab.setImageDrawable(plus);
					break;
				case "Tag all work":
					fragment = new AddWorkFragment();
					tag = "Tag add work";
					fab.setImageDrawable(done);
					break;
			}
			if (fragment != null) getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.frame_master, fragment, tag)
				.commit();
		}
	}
}
