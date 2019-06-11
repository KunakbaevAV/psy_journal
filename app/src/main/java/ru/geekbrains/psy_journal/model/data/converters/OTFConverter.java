package ru.geekbrains.psy_journal.model.data.converters;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;

import ru.geekbrains.psy_journal.model.data.OTF;

public class OTFConverter {

    @TypeConverter
    public String fromOTF(OTF otf) {
        String id = String.valueOf(otf.getId());
        String code = otf.getCode();
        String name = otf.getName();
        return id + "," + code + "," + name;
    }

    @TypeConverter
    public OTF toOTF(String data) {
        List<String> list = Arrays.asList(data.split(","));
        return new OTF(Integer.parseInt(list.get(0)), list.get(1), list.get(2));
    }

}
