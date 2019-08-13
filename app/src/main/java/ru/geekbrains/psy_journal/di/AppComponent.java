package ru.geekbrains.psy_journal.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.geekbrains.psy_journal.data.repositories.Mapping;
import ru.geekbrains.psy_journal.data.repositories.RoomHelper;
import ru.geekbrains.psy_journal.presentation.presenter.activity.MainPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.DialogFunctionPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.EditableDialogPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.OpenFileDialogPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.AddWorkPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.AllWorkPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.EditableCatalogPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.ReportPresenter;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.EditableListsAdapter;
import ru.geekbrains.psy_journal.presentation.view.utilities.ItemTouchHelperCallback;

@Singleton
@Component(modules = {AppModule.class, FileModule.class})
public interface AppComponent {

    void inject(RoomHelper roomHelper);

    void inject(AllWorkPresenter allWorkPresenter);

    void inject(AddWorkPresenter workPresenter);

    void inject(DialogFunctionPresenter functionPresenter);

    void inject(App app);

    void inject(EditableDialogPresenter presenter);

    void inject(ReportPresenter presenter);

	void inject(MainPresenter presenter);

    void inject(Mapping mapping);

	void inject(EditableCatalogPresenter presenter);

	void inject(EditableListsAdapter.ViewHolder viewHolder);

    void inject(OpenFileDialogPresenter presenter);

    void inject(ItemTouchHelperCallback itemTouchHelperCallback);
}
