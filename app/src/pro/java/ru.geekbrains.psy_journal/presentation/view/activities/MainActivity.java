package ru.geekbrains.psy_journal.presentation.view.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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

import java.io.File;
import java.util.ArrayList;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.activity.MainPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.activity.InformedView;
import ru.geekbrains.psy_journal.presentation.view.dialogs.FileSelectionDialog;
import ru.geekbrains.psy_journal.presentation.view.dialogs.MessageDialog;
import ru.geekbrains.psy_journal.presentation.view.dialogs.OpenFileDialog;
import ru.geekbrains.psy_journal.presentation.view.dialogs.ReportSelectionDialog;
import ru.geekbrains.psy_journal.presentation.view.fragment.AddWorkFragment;
import ru.geekbrains.psy_journal.presentation.view.fragment.AllWorkFragment;
import ru.geekbrains.psy_journal.presentation.view.utilities.BehaviorFAB;

import static ru.geekbrains.psy_journal.Constants.INTENT_TYPE_MULTIPART;
import static ru.geekbrains.psy_journal.Constants.TAG_ADD_WORK;
import static ru.geekbrains.psy_journal.Constants.TAG_ALL_WORK;

public class MainActivity extends MvpAppCompatActivity implements
	InformedView,
    SelectableFile,
    Responded {

	@BindView(R.id.main_navigation_drawer) DrawerLayout drawer;
    @BindView(R.id.navigation_view) NavigationView navigationView;
    @BindView(R.id.bottomAppBar) BottomAppBar bottomAppBar;
    @BindView(R.id.fab)FloatingActionButton fab;
    @BindDrawable(R.drawable.ic_add_24dp) Drawable plus;
    @BindDrawable(R.drawable.ic_done_white_24dp)Drawable done;

    @InjectPresenter MainPresenter mainPresenter;

	private static final int REQUEST_PERMISSION_READ_FILE_XML = 2;
	private static final String TAG_SELECT_XML = "Tag select XML";
	private static final String XML = ".xml";
	private static final String TAG_MESSAGE = "Tag message";
	private boolean isShowFileXML;
	private boolean isShowFilesXLS;

    @ProvidePresenter
    MainPresenter providePresenter() {
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
            loadFragment(new AllWorkFragment());
        }
        init();
    }

    private void init() {
        setToggle();
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        fab.setOnClickListener(this::onClick);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        layoutParams.setBehavior(new BehaviorFAB());
        fab.setLayoutParams(layoutParams);
    }

    private void setToggle() {
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
        loadFragment(new AllWorkFragment());
        setImageFab(plus);
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_master, fragment, Constants.TAG_ALL_WORK)
                .commit();
    }

    @Override
    public void onBackPressed() {
        setImageFabForTag(getTag());
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if (TAG_ADD_WORK.equals(getTag())) {
            openAllWorkFragment();
            return;
        }
        super.onBackPressed();
    }

    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.load_database:
                loadDataBaseFromXMLFile();
                return true;
            case R.id.edit_catalogs:
                openScreenEditCatalogs();
                return true;
            case R.id.get_report:
                openScreenGettingReport();
                return true;
            case R.id.create_excel_report_file:
                createExcelReportFile();
                return true;
            case R.id.send_email:
                sendToMailReport();
                return true;
	        case R.id.feedback:
				toSendLetter();
	        	return true;
        }
        return false;
    }

    private void loadDataBaseFromXMLFile() {
        if (checkPermissions(REQUEST_PERMISSION_READ_FILE_XML)) {
            openFileDialog();
        }
    }

    private void openFileDialog() {
        OpenFileDialog dialog = new OpenFileDialog();
        dialog.show(getSupportFragmentManager(), TAG_SELECT_XML);
    }

    private void openScreenEditCatalogs() {
        startActivity(new Intent(getBaseContext(), EditorActivity.class));
    }

    private void openScreenGettingReport() {
        new ReportSelectionDialog().show(getSupportFragmentManager(), Constants.TAG_OTF_SELECTION);
    }

    private void createExcelReportFile() {
        if (checkPermissions(Constants.REQUEST_PERMISSION_CREATE_FILE_XLS)) mainPresenter.createExcelFile(getString(R.string.report));
    }

    private void sendToMailReport() {
        if (checkPermissions(Constants.REQUEST_PERMISSION_READ_FILE_XLS)) getFiles();
    }

    private void toSendLetter(){
	    Intent intent = new Intent(Intent.ACTION_SENDTO);
	    intent.setData(Uri.parse(Constants.MAILTO));
	    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{Constants.PSYJOURNAL_GMAIL_COM});
	    intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.recall));
		sendOut(intent);
    }

    private void getFiles() {
		new FileSelectionDialog().show(getSupportFragmentManager(), Constants.TAG_FILES_XLS);
    }

    private Intent prepareFiles(ArrayList<Uri> files) {
	    Intent intent;
    	if (files.size() == 1){
		    intent = new Intent(Intent.ACTION_SEND);
		    intent.putExtra(Intent.EXTRA_STREAM, files.get(0));
	    } else {
		    intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
		    intent.putExtra(Intent.EXTRA_STREAM, files);
	    }
	    intent.setType(INTENT_TYPE_MULTIPART);
        return intent;
    }

    private void sendToMail(ArrayList<Uri> files) {
        sendOut(prepareFiles(files));
    }

    private void sendOut(Intent intent){
	    if (intent.resolveActivity(getPackageManager()) != null) {
		    startActivity(intent);
	    } else {
	    	showMessage(getString(R.string.need_mail_client));
	    }
    }

    private boolean checkPermissions(int request) {
        if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, request);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions.length == 2 && (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            switch (requestCode) {
                case Constants.REQUEST_PERMISSION_CREATE_FILE_XLS:
                    mainPresenter.createExcelFile(getString(R.string.report));
                    break;
                case REQUEST_PERMISSION_READ_FILE_XML:
                    isShowFileXML = true;
                    break;
                case Constants.REQUEST_PERMISSION_READ_FILE_XLS:
                    isShowFilesXLS = true;
                    break;
            }
        } else {
            showMessage(getString(R.string.no_permission_file));
        }
    }

	@Override
	protected void onPostResume() {
		super.onPostResume();
		if (isShowFileXML){
			openFileDialog();
			isShowFileXML = false;
			return;
		}
		if (isShowFilesXLS){
			getFiles();
			isShowFilesXLS = false;
		}
	}

	private void showMessage(String message) {
        Snackbar snackbar = Snackbar.make(bottomAppBar, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAnchorView(fab);
        snackbar.show();
    }

    private void updateFragment(){
	    if (TAG_ALL_WORK.equals(getTag())){
		    AllWorkFragment workFragment = (AllWorkFragment) getSupportFragmentManager().findFragmentByTag(TAG_ALL_WORK);
		    if (workFragment != null) {
			    workFragment.getUpdated().update();
		    }
	    }
    }

    @Override
    public void selectFileXML(File file) {
        if (file.getName().endsWith(XML)) {
			mainPresenter.takeFile(file);
			String message = getString(R.string.updating_standard_previous_entries_want_save, file.getName());
	        MessageDialog.newInstance(message).show(getSupportFragmentManager(), TAG_MESSAGE);
        } else {
        	showMessage(getResources().getString(R.string.no_xml_selected));
        }
    }

	@Override
	public void selectReportFiles(ArrayList<Uri> files) {
    	if (files == null) return;
		sendToMail(files);
	}

	@Override
	public void showStatusLoadDataBase(String error) {
		String message;
		if(error == null){
			message = getResources().getString(R.string.database_loaded_new_standard);
			updateFragment();
		} else {
			message = getString(R.string.failure_loading_database, error);
		}
		showMessage(message);
	}

	@Override
    public void showStatusClearDatabase(String error) {
		String message;
		if(error == null){
			message = getResources().getString(R.string.base_cleared);
		} else {
			message = getString(R.string.failure_cleaning_database_restored, error);
		}
        showMessage(message);
    }

    @Override
    public void showStatusWriteReport(String nameFile, String error) {
    	String message = null;
    	if(nameFile == null && error == null){
    		message = getString(R.string.db_empty);
	    }
    	if(nameFile == null && error != null){
    		message = String.format(getString(R.string.file_write_error), error);
	    }
    	if (nameFile != null && error == null){
    		message = String.format(getString(R.string.file_write_to), nameFile);
	    }
        showMessage(message);
    }

	@Override
	public void showStatusReadXml(String nameFile, String error) {
		showMessage(getString(R.string.unable_read_file, nameFile, error));
	}

	@Override
	public void toCancel() {
		showMessage(getResources().getString(R.string.standard_update_canceled));
		mainPresenter.takeFile(null);
	}

	@Override
	public void refuse() {
		mainPresenter.updateWithoutSaving();
	}

	@Override
	public void toAccept() {
		mainPresenter.saveOldDataBase(getResources().getString(R.string.archive));
	}
}
