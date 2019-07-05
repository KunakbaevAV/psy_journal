package ru.geekbrains.psy_journal;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.geekbrains.psy_journal.model.data.Catalog;
import ru.geekbrains.psy_journal.model.data.Group;
import ru.geekbrains.psy_journal.model.factory.CatalogFactory;
import ru.geekbrains.psy_journal.view.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void test(){
        onView(withId(R.id.fab))
                .perform(click());


    }

    @Test
    public void catalogFactory_getCatalog(){
        CatalogFactory underTest = new CatalogFactory();
        Catalog actual = underTest.getCatalog("NULL");
        assertNull(actual);
    }

    @Test
    public void catalogFactory_getCatalog_(){
        CatalogFactory underTest = new CatalogFactory();
        Catalog actual = underTest.getCatalog("Класс/группа");
        assertEquals(Group.class, actual.getClass());
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("ru.geekbrains.psy_journal", appContext.getPackageName());
    }
}
