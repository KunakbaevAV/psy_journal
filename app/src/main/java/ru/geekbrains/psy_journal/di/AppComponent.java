package ru.geekbrains.psy_journal.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.geekbrains.psy_journal.data.database.DataBaseFirstLoader;
import ru.geekbrains.psy_journal.data.repositories.RoomHelper;
import ru.geekbrains.psy_journal.presentation.presenter.AddWorkPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.AllWorkPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.DialogFunctionPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.EditableDialogPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.MainPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.ReportPresenter;

@Singleton
@Component(modules = {AppModule.class, FileModule.class})
public interface AppComponent {

    void inject(RoomHelper roomHelper);

    void inject(DataBaseFirstLoader dataBaseFirstLoader);

    void inject(AllWorkPresenter allWorkPresenter);

    void inject(AddWorkPresenter workPresenter);

    void inject(DialogFunctionPresenter functionPresenter);

    void inject(App app);

    void inject(EditableDialogPresenter presenter);

    void inject(ReportPresenter presenter);

	void inject(MainPresenter presenter);
}
