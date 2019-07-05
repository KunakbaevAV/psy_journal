package ru.geekbrains.psy_journal;

import org.junit.Test;

import ru.geekbrains.psy_journal.model.data.Catalog;
import ru.geekbrains.psy_journal.model.data.Group;
import ru.geekbrains.psy_journal.model.factory.CatalogFactory;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void group_constructorNameTest(){
        Group underTest = new Group("Test");
        assertEquals("Test", underTest.getName());
    }

    @Test
    public void group_constructorIdNameTest(){
        Group underTest = new Group(1,"Test");
        assertEquals(1, underTest.getId());
        assertEquals("Test", underTest.getName());
    }




}