package ru.geekbrains.psy_journal.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.collection.ArraySet;
import androidx.fragment.app.FragmentManager;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.model.data.Functional;
import ru.geekbrains.psy_journal.model.data.Journal;
import ru.geekbrains.psy_journal.presenter.AddWorkPresenter;
import ru.geekbrains.psy_journal.view.AdapterTextWatcher;
import ru.geekbrains.psy_journal.view.dialogs.DateSettingDialog;
import ru.geekbrains.psy_journal.view.dialogs.FunctionDialog;
import ru.geekbrains.psy_journal.view.dialogs.TimeSettingDialog;

public class AddWorkFragment extends MvpAppCompatFragment implements
        AddWorkView,
        View.OnClickListener {

    private static final String PATTERN = "dd.MM.yy";

	public static AddWorkFragment newInstance(Journal journal) {
		AddWorkFragment addWorkFragment = new AddWorkFragment();
		Bundle args = new Bundle();
		args.putParcelable("journal", journal);
		addWorkFragment.setArguments(args);
		return addWorkFragment;
	}
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN, Locale.getDefault());

    @BindView(R.id.declared_request_layout) TextInputLayout declaredReqoestLayout;
    @BindView(R.id.date_text) TextInputEditText dateText;
    @BindView(R.id.work_time_count) TextInputEditText workTimeText;
    @BindView(R.id.name_text) AppCompatAutoCompleteTextView nameText;
    @BindView(R.id.quantity_people_count) TextInputEditText quantityPeople;
    @BindView(R.id.declared_request_text) TextInputEditText declaredRequestText;
    @BindView(R.id.real_request_text) TextInputEditText realRequestText;
    @BindView(R.id.code_tf_text) TextInputEditText codeTfText;
    @BindView(R.id.comment_text) TextInputEditText commentText;
    @BindView(R.id.quantity_people_layout) TextInputLayout quantityPeopleLayout;

    private Unbinder unbinder;
    private Journal journal;

	@InjectPresenter
	public AddWorkPresenter workPresenter;

    @ProvidePresenter
    AddWorkPresenter providePresenter() {
    	if (getArguments() != null) journal = getArguments().getParcelable("journal");
        AddWorkPresenter workPresenter = new AddWorkPresenter(journal);
        App.getAppComponent().inject(workPresenter);
        return workPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_work, container, false);
        unbinder = ButterKnife.bind(this, view);
        dateText.setHint(dateFormat.format(new Date()));
        workTimeText.setText("1.0");
        if (getContext() != null)
            nameText.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, getNames()));
        quantityPeople.addTextChangedListener(new AdapterTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s == null) return;
                checkForNumber(s.toString());
            }
        });
        return view;
    }

    private List<String> getNames() {
        if (getContext() == null) return null;
        SharedPreferences preferences = getContext().getSharedPreferences("names options", Context.MODE_PRIVATE);
        Set<String> set = preferences.getStringSet("names", null);
        if (set == null) return null;
        return new ArrayList<>(set);
    }

    private boolean checkForNumber(String string) {
        if (string.equals("")) return false;
        string = string.trim();
        String errorMessage = null;
        try {
            int num = Integer.parseInt(string);
            if (num < 0) errorMessage = "less than zero";
        } catch (NumberFormatException e) {
            errorMessage = "not a number";
        }
        quantityPeopleLayout.setError(errorMessage);
        return errorMessage == null;
    }

    private void saveNames(String name) {
        if (getContext() != null) {
            SharedPreferences preferences = getContext().getSharedPreferences("names options", Context.MODE_PRIVATE);
            Set<String> set = preferences.getStringSet("names", new ArraySet<>());
            if (set != null) {
                set.add(name);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putStringSet("names", set);
                editor.apply();
            }
        }
    }

    @OnClick({R.id.date_text, R.id.work_time_count, R.id.code_tf_text})
    @Override
    public void onClick(View v) {
        if (getActivity() == null) return;
        switch (v.getId()) {
            case R.id.date_text:
                new DateSettingDialog().show(getActivity().getSupportFragmentManager(), "Tag date picker");
                break;
            case R.id.work_time_count:
                new TimeSettingDialog().show(getActivity().getSupportFragmentManager(), "Tag time picker");
                break;
            case R.id.code_tf_text:
                FunctionDialog.newInstance(getString(R.string.OTF), 0).show(getActivity().getSupportFragmentManager(), getString(R.string.OTF));
                break;
        }
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

	}

	@Override
	public void showGroup(String nameGroup) {

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

	}

	@Override
	public void showTd(Functional functional) {
		if (functional.getCode().equals(Constants.CODE_OF_OTHER_ACTIVITY))
			codeTfText.setText(functional.getName());
		else {
			codeTfText.setText(functional.getCode());
			workPresenter.getJournal().setIdTd(functional.getId());
		}
	}

	@Override
	public void showComment(String comment) {
		commentText.setText(comment);
	}

	@Override
    public void collectAll() {
        Editable editable = nameText.getText();
        if (editable != null) {
            String name = editable.toString();
            if (!name.equals("")) {
                saveNames(name);
//				workPresenter.setGroup(name);
            }
        }
        if (quantityPeople.getText() != null) {
            String quantity = quantityPeople.toString();
            if (checkForNumber(quantity))
                workPresenter.getJournal().setQuantityPeople(Integer.parseInt(quantity));
        }
        if (declaredRequestText.getText() != null)
            workPresenter.getJournal().setDeclaredRequest(declaredRequestText.getText().toString());
        if (realRequestText.getText() != null)
            workPresenter.getJournal().setRealRequest(realRequestText.getText().toString());
        if (commentText.getText() != null)
            workPresenter.getJournal().setComment(commentText.getText().toString());
        workPresenter.addWorkIntoDatabase();
    }

    @Override
    public void openDialogue(String title, int id) {
        if (getActivity() != null) {
            FunctionDialog.newInstance(title, id).show(getActivity().getSupportFragmentManager(), title);
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

    public boolean isEmptyDeclaredRequest() {
        if (declaredRequestText.getText() == null || declaredRequestText.getText().toString().equals("")) {
            Toast.makeText(this.getContext(), R.string.fill_declared_request, Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.Shake).playOn(declaredReqoestLayout);
            return true;
        } else {
            return false;
        }
    }

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}
}
