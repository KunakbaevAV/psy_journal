package ru.geekbrains.psy_journal.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.R;

public class AddWorkActivity extends AppCompatActivity {

	@BindView(R.id.date_text)	TextInputEditText dateText;



	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_work);
		ButterKnife.bind(this);
		dateText.setHint(DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
	}
}
