package ru.geekbrains.psy_journal.di;

import dagger.Component;
import ru.geekbrains.psy_journal.model.data.RoomHelper;
import ru.geekbrains.psy_journal.model.database.DataBaseFirstLoader;

@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(RoomHelper roomHelper);

    void inject(DataBaseFirstLoader dataBaseFirstLoader);

}
