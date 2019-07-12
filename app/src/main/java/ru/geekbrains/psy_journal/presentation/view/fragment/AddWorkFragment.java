package ru.geekbrains.psy_journal.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.FragmentManager;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.data.repositories.model.Functional;
import ru.geekbrains.psy_journal.data.repositories.model.Journal;
import ru.geekbrains.psy_journal.presentation.presenter.AddWorkPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByDate;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByFunction;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.AddWorkView;
import ru.geekbrains.psy_journal.presentation.presenter.Collectable;
import ru.geekbrains.psy_journal.presentation.view.dialogs.DateSettingDialog;
import ru.geekbrains.psy_journal.presentation.view.dialogs.EditableDialog;
import ru.geekbrains.psy_journal.presentation.view.dialogs.FunctionDialog;
import ru.geekbrains.psy_journal.presentation.view.dialogs.OTFDialog;
import ru.geekbrains.psy_journal.presentation.view.dialogs.TDDialog;
import ru.geekbrains.psy_journal.presentation.view.dialogs.TFDialog;
import ru.geekbrains.psy_journal.presentation.view.dialogs.TimeSettingDialog;

public class AddWorkFragment extends MvpAppCompatFragment implements
	AddWorkView,
	Collectable,
		GivenBySettableDate,
        GivenBySettableFunction {

	private static final String DEFAULT_WORK_TIME = "1.0";
	private static final String KEY_JOURNAL = "key journal";
	private static final String TAG_TIME_PICKER = "Tag time picker";

	static AddWorkFragment newInstance(Journal journal) {
		AddWorkFragment addWorkFragment = new AddWorkFragment();
		Bundle args = new Bundle();
		args.putParcelable(KEY_JOURNAL, journal);
		addWorkFragment.setArguments(args);
		return addWorkFragment;
	}

    private final SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.PATTERN_DATE, Locale.getDefault());
	private final TextView.OnEditorActionListener editorActionListener = (v, actionId, event) -> {
		if (actionId == EditorInfo.IME_ACTION_DONE) {
			closeKeyBoard();
			v.clearFocus();
			return true;
		}
		return false;
	};

    @BindView(R.id.declared_request_layout)
    TextInputLayout declaredRequestLayout;
    @BindView(R.id.date_text) TextInputEditText dateText;
    @BindView(R.id.work_time_count) TextInputEditText workTimeText;
	@BindView(R.id.category_text) TextInputEditText categoryText;
	@BindView(R.id.group_text) TextInputEditText groupText;
    @BindView(R.id.name_text) AppCompatAutoCompleteTextView nameText;
    @BindView(R.id.quantity_people_count) TextInputEditText quantityPeople;
    @BindView(R.id.declared_request_text) TextInputEditText declaredRequestText;
    @BindView(R.id.real_request_text) TextInputEditText realRequestText;
	@BindView(R.id.work_form_text) TextInputEditText workFormText;
    @BindView(R.id.code_tf_text) TextInputEditText codeTfText;
    @BindView(R.id.comment_text) TextInputEditText commentText;
    @BindView(R.id.quantity_people_layout) TextInputLayout quantityPeopleLayout;

    private Unbinder unbinder;
    private Journal journal;

	@InjectPresenter
	public AddWorkPresenter workPresenter;

    @ProvidePresenter
    AddWorkPresenter providePresenter() {
    	if (getArguments() != null) journal = getArguments().getParcelable(KEY_JOURNAL);
        AddWorkPresenter workPresenter = new AddWorkPresenter();
        App.getAppComponent().inject(workPresenter);
        workPresenter.initialize(journal);
        return workPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_work, container, false);
        unbinder = ButterKnife.bind(this, view);
        dateText.setHint(dateFormat.format(new Date()));
        workTimeText.setText(DEFAULT_WORK_TIME);
        initialize();
        return view;
    }

    private void initialize(){
    	if (getActivity() == null) return;
	    dateText.setOnClickListener(v -> DateSettingDialog.newInstance(Constants.TAG_ADD_WORK).show(getActivity().getSupportFragmentManager(), Constants.TAG_DATE_PICKER));
        quantityPeople.setOnEditorActionListener(editorActionListener);
	    workTimeText.setOnClickListener(v -> new TimeSettingDialog().show(getActivity().getSupportFragmentManager(), TAG_TIME_PICKER));
	    categoryText.setOnClickListener(v -> EditableDialog.newInstance(getString(R.string.choose_category)).show(getActivity().getSupportFragmentManager(), getString(R.string.choose_category)));
	    groupText.setOnClickListener(v -> EditableDialog.newInstance(getString(R.string.choose_group)).show(getActivity().getSupportFragmentManager(), getString(R.string.choose_group)));
        declaredRequestText.setOnEditorActionListener(editorActionListener);
        realRequestText.setOnEditorActionListener(editorActionListener);
	    workFormText.setOnClickListener(v -> EditableDialog.newInstance(getString(R.string.choose_work_form)).show(getActivity().getSupportFragmentManager(), getString(R.string.choose_work_form)));
	    codeTfText.setOnClickListener(v -> openDialogue(OTFDialog.newInstance(Constants.TAG_ADD_WORK) , getString(R.string.OTF)));
    }

    private void setNumber(String string) {
    	int num;
        if (string == null || string.equals("")){
	        num = 0;
        } else {
	        try {
		        num = Integer.parseInt(string);
		        if (num < 0) num = 0;
	        } catch (NumberFormatException e) {
		        num = 0;
	        }
        }
	    workPresenter.getJournal().setQuantityPeople(num);
    }

    private void checkNames(){
	    Editable editable = nameText.getText();
	    if (editable != null) {
		    String name = editable.toString();
		    if (!name.equals("")) {
			    workPresenter.getJournal().setName(name);
		    }
	    }
    }

    private void checkQuantityPeople(){
	    if (quantityPeople.getText() != null) {
			setNumber(quantityPeople.getText().toString().trim());
	    }
    }

	private String checkDeclaredRequest() {
		if (declaredRequestText.getText() == null || declaredRequestText.getText().toString().equals("")) {
			Toast.makeText(this.getContext(), R.string.fill_declared_request, Toast.LENGTH_SHORT).show();
			YoYo.with(Techniques.Shake).playOn(declaredRequestLayout);
			return null;
		} else {
			return declaredRequestText.getText().toString();
		}
	}

	private void checkRealRequestText(){
		if (realRequestText.getText() != null)
			workPresenter.getJournal().setRealRequest(realRequestText.getText().toString());
	}

	private void checkCommentText(){
		if (commentText.getText() != null)
			workPresenter.getJournal().setComment(commentText.getText().toString());
	}

	private void openDialogue(FunctionDialog dialog, String title) {
		if (getActivity() != null) {
			dialog.show(getActivity().getSupportFragmentManager(), title);
		}
	}

	private boolean checkDialog(String tag){
    	if (getActivity() == null) return false;
    	FunctionDialog dialog = (FunctionDialog) getActivity().getSupportFragmentManager().findFragmentByTag(tag);
    	return dialog != null;
	}

    private void closeKeyBoard() {
        if (getActivity() == null) return;
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

	@Override
	public void getNames(List<String> names) {
		if (getContext() != null)
			nameText.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, names));
	}

    @Override
    public void showDate(long date) {
        dateText.setText(dateFormat.format(new Date(date)));
    }

	@Override
	public void showNumberOfPeople(int number) {
		quantityPeople.setText(String.valueOf(number));
	}

	@Override
    public void showHours(float hours) {
        workTimeText.setText(String.format(Locale.getDefault(), "%.2f", hours));
    }

	@Override
	public void showCategory(String nameCategory) {
		categoryText.setText(nameCategory);
	}

	@Override
	public void showGroup(String nameGroup) {
		groupText.setText(nameGroup);
	}

	@Override
	public void showName(String name) {
		nameText.setText(name);
	}

	@Override
	public void showDeclaredRequest(String declaredRequest) {
		declaredRequestText.setText(declaredRequest);
	}

	@Override
	public void showRealRequest(String realRequest) {
		realRequestText.setText(realRequest);
	}

	@Override
	public void showWorkForm(String nameWorkForm) {
		workFormText.setText(nameWorkForm);
	}

	@Override
	public void showTd(String code) {
		codeTfText.setText(code);
	}

	@Override
	public void showComment(String comment) {
		commentText.setText(comment);
	}

	@Override
    public boolean isCollectedAll() {
    	String declaredRequest = checkDeclaredRequest();
		if (declaredRequest == null) return false;
		workPresenter.getJournal().setDeclaredRequest(declaredRequest);
		checkQuantityPeople();
        checkNames();
        checkRealRequestText();
        checkCommentText();
        closeKeyBoard();
        workPresenter.addWorkIntoDatabase();
        return true;
    }

	@Override
	public void openDialogue(Functional function) {
		if (checkDialog(getString(R.string.OTF)) && !checkDialog(getString(R.string.TF))){
			openDialogue(TFDialog.newInstance(function.getId(), Constants.TAG_ADD_WORK), getString(R.string.TF));
			return;
		}
		if (checkDialog(getString(R.string.TF))){
			openDialogue(TDDialog.newInstance(function.getId(), Constants.TAG_ADD_WORK), getString(R.string.TD));
		}
	}

    @Override
    public void closeDialogs() {
        if (getActivity() != null) {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            for (int i = 0; i < manager.getFragments().size(); i++) {
                if (getString(R.string.TD).equals(manager.getFragments().get(i).getTag()) ||
                        getString(R.string.TF).equals(manager.getFragments().get(i).getTag()) ||
                        getString(R.string.OTF).equals(manager.getFragments().get(i).getTag()))
                    manager.beginTransaction().remove(manager.getFragments().get(i)).commit();
            }
        }
    }

    @Override
    public void showToast(String message) {
        if (getActivity() == null) return;
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

	@Override
	public SettableByDate getSettableByDate() {
		return workPresenter;
	}

	@Override
	public SettableByFunction getSettableByFunction() {
		return workPresenter;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}
}
