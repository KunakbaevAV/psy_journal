package ru.geekbrains.psy_journal.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.model.data.Journal;
import ru.geekbrains.psy_journal.presenter.AddWorkPresenter;
import ru.geekbrains.psy_journal.view.dialogs.DateSettingDialog;

public class AddWorkFragment extends MvpAppCompatFragment implements Displayed, View.OnClickListener {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
	@BindView(R.id.date_text) TextInputEditText dateText;

    @InjectPresenter
    public AddWorkPresenter workPresenter;

    @ProvidePresenter
	AddWorkPresenter providePresenter(){
    	return new AddWorkPresenter(new Journal());
    }

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_add_work, container, false);
		ButterKnife.bind(this, view);
		dateText.setHint(dateFormat.format(new Date()));

		return view;
	}

	@OnClick({R.id.date_text})
	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.date_text:
				if (getActivity() != null){
					new DateSettingDialog().show(getActivity().getSupportFragmentManager(), "Tag date picker");
				}
				break;
		}
	}

	@Override
	public void showDate(long date) {
		dateText.setText(dateFormat.format(new Date(date)));
	}
}
