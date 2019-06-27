package ru.geekbrains.psy_journal.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.view.dialogs.OTFSelectionDialog;
import ru.geekbrains.psy_journal.view.fragment.AddWorkFragment;
import ru.geekbrains.psy_journal.view.fragment.AllWorkFragment;

import static ru.geekbrains.psy_journal.Constants.TAG_ADD_WORK;
import static ru.geekbrains.psy_journal.Constants.TAG_ALL_WORK;

public class MainActivity extends AppCompatActivity{

	@BindView(R.id.fab) FloatingActionButton fab;
    @BindDrawable(R.drawable.ic_add_circle_outline_white_24dp) Drawable plus;
    @BindDrawable(R.drawable.ic_done_white_24dp) Drawable done;
    @BindView(R.id.main_navigation_drawer)
    DrawerLayout drawer;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setImageFabForTag(getTag());
        if (savedInstanceState == null) {
            loadFragment(new AllWorkFragment(), TAG_ALL_WORK);
        }
        init();
    }

    private void init() {
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
	    fab.setOnClickListener(this::onClick);
    }

    public void setImageFabForTag(String tag) {
        if (TAG_ADD_WORK.equals(tag) || getString(R.string.OTF).equals(tag) || getString(R.string.TF).equals(tag) ||
                getString(R.string.TD).equals(tag)) setImageFab(done);
        else setImageFab(plus);
    }

    private void setImageFab(Drawable image) {
        fab.setImageDrawable(image);
    }

    private String getTag() {
        if (getSupportFragmentManager().getFragments().size() == 0) return null;
        return getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getFragments().size() - 1).getTag();
    }

    private void onClick(View v) {
        if (v.getId() == R.id.fab) {
            Fragment currentFragment = getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getFragments().size() - 1);
            String tag = currentFragment.getTag();
            if (tag == null) return;
            switch (tag) {
                case TAG_ADD_WORK:
                    AddWorkFragment addWorkFragment = (AddWorkFragment) currentFragment;
                    if (addWorkFragment.isCollectedAll()) {
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
        setImageFab(done);
    }

    private void openAllWorkFragment() {
	    getSupportFragmentManager().popBackStack(TAG_ADD_WORK, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        loadFragment(new AllWorkFragment(), TAG_ALL_WORK);
        setImageFab(plus);
    }

    private void loadFragment(Fragment fragment, String tag){
	    getSupportFragmentManager()
		    .beginTransaction()
		    .replace(R.id.frame_master, fragment, tag)
		    .commit();
    }

    @Override
    public void onBackPressed() {
        setImageFabForTag(getTag());
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if (TAG_ADD_WORK.equals(getTag())){
            openAllWorkFragment();
            return;
        }
	    super.onBackPressed();
    }

    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
	    drawer.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
	        case R.id.edit_catalogs:
		        openScreenEditCatalogs();
		        return true;
	        case R.id.select_otf:
		        openScreenSelectOtf();
		        return true;
	        case R.id.get_report:
		        openScreenGettingReport();
		        return true;
	        case R.id.send_email:
		        openScreenSendReportToEmail();
		        return true;
        }
        return false;
    }

    private void openScreenEditCatalogs() {
        //TODO Метод открытия окна для редактирования списков
    }

    private void openScreenSelectOtf() {
	    new OTFSelectionDialog().show(getSupportFragmentManager(), "Tag OTFSelection");
    }

    private void openScreenGettingReport() {
        //TODO Метод открытия окна формирования отчета
    }

    private void openScreenSendReportToEmail() {
        //TODO Метод открытия окна для отправки отчета на электронную почту
    }
}
