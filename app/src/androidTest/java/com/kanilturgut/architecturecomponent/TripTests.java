package com.kanilturgut.architecturecomponent;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.kanilturgut.architecturecomponent.room.Trip;
import com.kanilturgut.architecturecomponent.room.TripDAO;
import com.kanilturgut.architecturecomponent.room.TripDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Author   : kanilturgut
 * Date     : 18/07/2017
 * Time     : 21:21
 */

@RunWith(AndroidJUnit4.class)
public class TripTests {

    TripDatabase db;
    TripDAO dao;

    @Before
    public void setUp() {
        db = TripDatabase.create(InstrumentationRegistry.getTargetContext(), true);
        dao = db.tripDao();
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void basics() {
        assertEquals(0, dao.selectAll().size());

        Trip first = new Trip("Foo", 2888);

        assertNotNull(first.id);
        assertNotEquals(0, first.id.length());
        dao.insert(first);

        assertTrip(first);

        Trip updated = new Trip(first.id, "Bar", 1512);

        dao.update(updated);
        assertTrip(updated);

        Trip trip = dao.findById(first.id);
        assertEquals("Bar", trip.title);
        assertEquals(1512, trip.duration);

        dao.delete(trip);
        assertEquals(0, dao.selectAll().size());

    }

    private void assertTrip(Trip trip) {
        List<Trip> results = dao.selectAll();
        assertNotNull(results);
        assertEquals(1, results.size());
        assertTrue(areIdentical(trip, results.get(0)));
    }

    private boolean areIdentical(Trip one, Trip two) {
        return(one.id.equals(two.id) &&
                one.title.equals(two.title) &&
                one.duration==two.duration);
    }
}
