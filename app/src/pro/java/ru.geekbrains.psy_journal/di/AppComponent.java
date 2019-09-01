package ru.geekbrains.psy_journal.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.geekbrains.psy_journal.data.repositories.Mapping;
import ru.geekbrains.psy_journal.data.repositories.RoomHelper;
import ru.geekbrains.psy_journal.presentation.presenter.activity.MainPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.DialogOTFPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.DialogTDPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.DialogTFPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.OpenFileDialogPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.SelectFileDialogPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.AddWorkPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.AllWorkPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.EditableCategoryPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.EditableGroupPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.EditableWorkFormPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.ReportPresenter;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.EditableListsAdapter;

@Singleton
@Component(modules = {AppModule.class, FileModule.class})
public interface AppComponent {

    void inject(RoomHelper roomHelper);

    void inject(AllWorkPresenter allWorkPresenter);

    void inject(AddWorkPresenter workPresenter);

	void inject(DialogOTFPresenter functionPresenter);

	void inject(DialogTFPresenter functionPresenter);

	void inject(DialogTDPresenter functionPresenter);

    void inject(App app);

    void inject(ReportPresenter presenter);

	void inject(MainPresenter presenter);

    void inject(Mapping mapping);

	void inject(EditableCategoryPresenter presenter);

	void inject(EditableGroupPresenter presenter);

	void inject(EditableWorkFormPresenter presenter);

	void inject(EditableListsAdapter.ViewHolder viewHolder);

    void inject(SelectFileDialogPresenter presenter);

	void inject(OpenFileDialogPresenter presenter);
}
