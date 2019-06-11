package ru.geekbrains.psy_journal.model.data.converters;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;

import ru.geekbrains.psy_journal.model.data.Category;
import ru.geekbrains.psy_journal.model.data.Group;
import ru.geekbrains.psy_journal.model.data.TD;
import ru.geekbrains.psy_journal.model.data.WorkForm;

public class JournalConverter {

    @TypeConverter
    public String fromTD(TD td) {
        String id = String.valueOf(td.getId());
        String code = td.getCode();
        String name = td.getName();
        return id + "," + code + "," + name;
    }

    @TypeConverter
    public TD toTD(String data) {
        List<String> list = Arrays.asList(data.split(","));
        return new TD(Integer.parseInt(list.get(0)), list.get(1), list.get(2), Integer.parseInt(list.get(3)));
    }

    @TypeConverter
    public String fromCategory(Category category) {
        String id = String.valueOf(category.getId());
        String name = category.getName();
        return id + "," + name;
    }

    @TypeConverter
    public Category toCategory(String data) {
        List<String> list = Arrays.asList(data.split(","));
        return new Category(Integer.parseInt(list.get(0)), list.get(1));
    }

    @TypeConverter
    public String fromGroup(Group group) {
        String id = String.valueOf(group.getId());
        String name = group.getName();
        return id + "," + name;
    }

    @TypeConverter
    public Group toGroup(String data) {
        List<String> list = Arrays.asList(data.split(","));
        return new Group(Integer.parseInt(list.get(0)), list.get(1));
    }

    @TypeConverter
    public String fromWorkForm(WorkForm workForm) {
        String id = String.valueOf(workForm.getId());
        String name = workForm.getName();
        return id + "," + name;
    }

    @TypeConverter
    public WorkForm toWorkForm(String data) {
        List<String> list = Arrays.asList(data.split(","));
        return new WorkForm(Integer.parseInt(list.get(0)), list.get(1));
    }

}
