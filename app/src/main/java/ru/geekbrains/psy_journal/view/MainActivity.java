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
import butterknife.OnClick;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.view.fragment.AddWorkFragment;
import ru.geekbrains.psy_journal.view.fragment.AllWorkFragment;

import static ru.geekbrains.psy_journal.Constants.TAG_ADD_WORK;
import static ru.geekbrains.psy_journal.Constants.TAG_ALL_WORK;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

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
        initNavigationDrawer();
    }

    private void initNavigationDrawer() {
        navigationView.setNavigationItemSelectedListener(this);
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
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.edit_catalogs) {
            openScreenEditCatalogs();
        } else if (id == R.id.select_otf) {
            openScreenSelectOtf();
        } else if (id == R.id.get_report) {
            openScreenGettingReport();
        } else if (id == R.id.send_email) {
            openScreenSendReportToEmail();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openScreenEditCatalogs() {
        //TODO Метод открытия окна для редактирования списков
    }

    private void openScreenSelectOtf() {
        //TODO Метод открытия окна для выбора ОТФ
    }

    private void openScreenGettingReport() {
        //TODO Метод открытия окна формирования отчета
    }

    private void openScreenSendReportToEmail() {
        //TODO Метод открытия окна для отправки отчета на электронную почту
    }
}
