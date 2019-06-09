package ru.geekbrains.psy_journal.di;

import dagger.Component;
import ru.geekbrains.psy_journal.model.data.RoomHelper;

@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(RoomHelper roomHelper);

}
