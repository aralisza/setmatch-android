package setmatch.setmatch;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunListener;

import static org.junit.Assert.*;

import setmatch.setmatch.GpsPosition;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("setmatch.setmatch", appContext.getPackageName());
    }

//    @Test
//    public void getLocation() {
//        GpsPosition pos = new GpsPosition();
//
//        assertNotEquals(pos.getLocation(), null);
//        System.out.println(pos.getLocation());
//    }
}
