package ru.geekbrains.psy_journal.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.geekbrains.psy_journal.data.database.DataBaseFirstLoader;
import ru.geekbrains.psy_journal.data.repositories.Mapping;
import ru.geekbrains.psy_journal.data.repositories.RoomHelper;
import ru.geekbrains.psy_journal.presentation.presenter.activity.MainPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.DialogFunctionPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.EditableDialogPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.AddWorkPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.AllWorkPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.ReportPresenter;

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

    void inject(Mapping mapping);
}
