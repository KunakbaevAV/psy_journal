package ru.geekbrains.psy_journal.data.repositories;

import io.reactivex.Completable;

public interface Cleanable {
	Completable clearDatabases();
}
