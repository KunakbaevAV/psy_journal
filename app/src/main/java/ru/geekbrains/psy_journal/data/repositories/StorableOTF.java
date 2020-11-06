package ru.geekbrains.psy_journal.data.repositories;

import java.util.List;

import io.reactivex.Single;
import ru.geekbrains.psy_journal.data.repositories.model.OTF;

public interface StorableOTF {

	Single<List<OTF>> getOTFList();
}
