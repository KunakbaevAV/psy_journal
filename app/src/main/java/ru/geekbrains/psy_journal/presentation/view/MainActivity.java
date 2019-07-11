package ru.geekbrains.psy_journal.presentation.view;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.MainPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.Informed;
import ru.geekbrains.psy_journal.presentation.view.dialogs.ReportSelectionDialog;
import ru.geekbrains.psy_journal.presentation.view.fragment.AddWorkFragment;
import ru.geekbrains.psy_journal.presentation.view.fragment.AllWorkFragment;

import static ru.geekbrains.psy_journal.Constants.TAG_ADD_WORK;
import static ru.geekbrains.psy_journal.Constants.TAG_ALL_WORK;

public class MainActivity extends MvpAppCompatActivity implements Informed {

	@BindView(R.id.main_navigation_drawer) DrawerLayout drawer;
	@BindView(R.id.navigation_view) NavigationView navigationView;
	@BindView(R.id.bottomAppBar) BottomAppBar bottomAppBar;
	@BindView(R.id.fab) FloatingActionButton fab;
    @BindDrawable(R.drawable.ic_add_circle_outline_white_24dp) Drawable plus;
    @BindDrawable(R.drawable.ic_done_white_24dp) Drawable done;

    @InjectPresenter MainPresenter mainPresenter;

	private static final int REQUEST_PERMISSION_EXTERNAL_STORAGE = 1;
	private static final int REQUEST_FILES_GET = 2;

	@ProvidePresenter
	MainPresenter providePresenter(){
		MainPresenter mainPresenter = new MainPresenter();
		App.getAppComponent().inject(mainPresenter);
		return mainPresenter;
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(bottomAppBar);
        setImageFabForTag(getTag());
        if (savedInstanceState == null) {
            loadFragment(new AllWorkFragment(), TAG_ALL_WORK);
        }
        init();
    }

    private void init() {
		setToggle();
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
	    fab.setOnClickListener(this::onClick);
    }

    private void setToggle(){
	    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
		    this, drawer, bottomAppBar, R.string.int_navigation_drawer_open, R.string.int_navigation_drawer_close);
	    drawer.addDrawerListener(toggle);
	    toggle.syncState();
	    toggle.setDrawerSlideAnimationEnabled(true);
	    toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
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
	        case R.id.create_excel_report_file:
	        	createExcelReportFile();
	        	return true;
	        case R.id.send_email:
		        getFiles();
		        return true;
        }
        return false;
    }

    private void openScreenEditCatalogs() {
        //TODO Метод открытия окна для редактирования списков
    }

    private void openScreenSelectOtf() {

    }

    private void openScreenGettingReport() {
        new ReportSelectionDialog().show(getSupportFragmentManager(), Constants.TAG_OTF_SELECTION);
    }

    private void createExcelReportFile(){
		checkPermissions();
    }

    private void getFiles(){
	    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
	    intent.setType("application/vnd.ms-excel");
	    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
	    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
	    startActivityForResult(intent, REQUEST_FILES_GET);
    }

    private ArrayList<Uri> getAttachment(Intent data){
	    ClipData clipData = data.getClipData();
	    if (clipData == null) return null;
		ArrayList<Uri> arrayList = new ArrayList<>(clipData.getItemCount());
	    for (int i = 0; i < clipData.getItemCount(); i++) {
		    arrayList.add(clipData.getItemAt(i).getUri());
		}
	    return arrayList;
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_FILES_GET && resultCode == RESULT_OK && data != null) {
			ArrayList<Uri> uris = getAttachment(data);
			Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
			intent.setType("multipart/*");
			intent.putExtra(Intent.EXTRA_STREAM, uris);
			if (intent.resolveActivity(getPackageManager()) != null) {
				startActivity(intent);
			}
		}
	}

	private void checkPermissions(){
		if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
			ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_EXTERNAL_STORAGE);
		} else {			
			mainPresenter.createExcelFile();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
		if (requestCode == REQUEST_PERMISSION_EXTERNAL_STORAGE && permissions.length == 2 && (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
			grantResults[1] == PackageManager.PERMISSION_GRANTED)){
				mainPresenter.createExcelFile();
		} else {
			showMessage("Отсутствует разрешение на запись файла");
		}
	}

	private void showMessage(String message) {
		Snackbar.make(bottomAppBar, message, Snackbar.LENGTH_INDEFINITE).show();
	}

	@Override
	public void showEmpty() {
		showMessage("база пуста");
	}

	@Override
	public void showGood(String message) {
		showMessage(String.format("%s записан в DOCUMENTS/Отчеты", message));
	}

	@Override
	public void showBad(String error) {
		showMessage(String.format("отчет.xls не создан, %s", error));
	}
}
