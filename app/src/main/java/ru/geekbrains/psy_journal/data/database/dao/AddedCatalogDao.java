package ru.geekbrains.psy_journal.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import ru.geekbrains.psy_journal.data.repositories.model.Category;
import ru.geekbrains.psy_journal.data.repositories.model.Group;
import ru.geekbrains.psy_journal.data.repositories.model.WorkForm;

@Dao
public abstract class AddedCatalogDao {

	@Insert
	abstract void insert(Category category);
	@Query("SELECT * FROM Category WHERE name = :name")
	abstract Category getItemCategory(String name);

	@Insert
	abstract void insert(Group group);
	@Query("SELECT * FROM `Group` WHERE name = :name")
	abstract Group getItemGroup(String name);

	@Insert
	abstract void insert(WorkForm workForm);
	@Query("SELECT * FROM Work_form WHERE name = :name")
	abstract WorkForm getItemWorkForm(String name);

	@Transaction
	public Category getAddedCategoryItem(String name){
		insert(new Category(name));
		return getItemCategory(name);
	}

	@Transaction
	public Group getAddedGroupItem(String name){
		insert(new Group(name));
		return getItemGroup(name);
	}

	@Transaction
	public WorkForm getAddedWorkFormItem(String name){
		insert(new WorkForm(name));
		return getItemWorkForm(name);
	}
}
