package ru.geekbrains.psy_journal.di;

import dagger.Component;
import ru.geekbrains.psy_journal.model.data.RoomHelper;
import ru.geekbrains.psy_journal.model.database.DataBaseFirstLoader;
import ru.geekbrains.psy_journal.presenter.AddWorkPresenter;
import ru.geekbrains.psy_journal.presenter.AllWorkPresenter;

@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(RoomHelper roomHelper);

    void inject(DataBaseFirstLoader dataBaseFirstLoader);

    void inject(AllWorkPresenter allWorkPresenter);

    void inject(AddWorkPresenter workPresenter);
}
