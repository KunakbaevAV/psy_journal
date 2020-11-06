package ru.geekbrains.psy_journal.data.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class TableCleaningDao {

	@Query("DELETE FROM Journal")
	abstract void deleteJournal();

	@Query("DELETE FROM TD")
	abstract void deleteTD();

	@Query("DELETE FROM TF")
	abstract void deleteTF();

	@Query("DELETE FROM OTF")
	abstract void deleteOTF();

	@Transaction
	public void deleteOldProfessionalStandardRecords(){
		deleteJournal();
		deleteTD();
		deleteTF();
		deleteOTF();
	}
}
