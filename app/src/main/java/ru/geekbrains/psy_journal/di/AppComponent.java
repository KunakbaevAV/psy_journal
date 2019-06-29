package ru.geekbrains.psy_journal.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.geekbrains.psy_journal.model.database.DataBaseFirstLoader;
import ru.geekbrains.psy_journal.model.database.RoomHelper;
import ru.geekbrains.psy_journal.model.factory.CatalogFactory;
import ru.geekbrains.psy_journal.presenter.AddWorkPresenter;
import ru.geekbrains.psy_journal.presenter.AllWorkPresenter;
import ru.geekbrains.psy_journal.presenter.DialogFunctionPresenter;
import ru.geekbrains.psy_journal.presenter.EditableDialogPresenter;
import ru.geekbrains.psy_journal.presenter.OTFSelectionPresenter;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(RoomHelper roomHelper);

    void inject(DataBaseFirstLoader dataBaseFirstLoader);

    void inject(AllWorkPresenter allWorkPresenter);

    void inject(AddWorkPresenter workPresenter);

    void inject(DialogFunctionPresenter functionPresenter);

    void inject(App app);

    void inject(EditableDialogPresenter presenter);

    void inject(CatalogFactory catalogFactory);
}
