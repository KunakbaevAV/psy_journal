package ru.geekbrains.psy_journal.model.data.converters;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Arrays;
import java.util.List;

import ru.geekbrains.psy_journal.model.data.OTF;
import ru.geekbrains.psy_journal.model.data.TF;

public class TFConverter {

    @TypeConverters(OTFConverter.class)
    private OTF otf;

    @TypeConverter
    public String fromTF(TF tf) {
        String id = String.valueOf(tf.getId());
        String code = tf.getCode();
        String name = tf.getName();
        return id + "," + code + "," + name;
    }

    @TypeConverter
    public TF toTF(String data) {
        List<String> list = Arrays.asList(data.split(","));
        return new TF(Integer.parseInt(list.get(0)), list.get(1), list.get(2), Integer.parseInt(list.get(3)));
    }
}
