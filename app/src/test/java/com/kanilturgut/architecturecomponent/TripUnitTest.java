package com.kanilturgut.architecturecomponent;

import com.kanilturgut.architecturecomponent.room.Trip;
import com.kanilturgut.architecturecomponent.room.TripDAO;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;

/**
 * Author   : kanilturgut
 * Date     : 18/07/2017
 * Time     : 21:47
 */

public class TripUnitTest {

    private TripDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = Mockito.mock(TripDAO.class);

        final HashMap<String, Trip> trips = new HashMap<>();


        // answer to select all
        doAnswer(new Answer() {

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ArrayList<Trip> result = new ArrayList<>(trips.values());

                Collections.sort(result, new Comparator<Trip>() {
                    @Override
                    public int compare(Trip left, Trip right) {
                        return left.title.compareTo(right.title);
                    }
                });

                return result;
            }
        }).when(dao).selectAll();


        // answer to findByID
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String id = (String) invocation.getArguments()[0];
                return (trips.get(id));
            }
        }).when(dao).findById(any(String.class));


        // INSERT
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                for (Object rawTrip : invocation.getArguments()) {
                    Trip trip = (Trip) rawTrip;
                    trips.put(trip.id, trip);
                }
                return (null);
            }
        }).when(dao).insert(Matchers.<Trip>anyVararg());

        // UPDATE
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                for (Object rawTrip : invocation.getArguments()) {
                    Trip trip = (Trip) rawTrip;
                    trips.put(trip.id, trip);
                }
                return (null);
            }
        }).when(dao).update(Matchers.<Trip>anyVararg());


        // DELETE
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                for (Object rawTrip : invocation.getArguments()) {
                    Trip trip = (Trip) rawTrip;
                    trips.remove(trip.id);
                }
                return (null);
            }
        }).when(dao).delete(Matchers.<Trip>anyVararg());
    }

    @Test
    public void basics() {
        Assert.assertEquals(0, dao.selectAll().size());

        Trip first = new Trip("Foo", 2888);

        assertNotNull(first.id);
        assertNotEquals(0, first.id.length());
        dao.insert(first);

        assertTrip(first);

        Trip updated = new Trip(first.id, "Bar", 1512);

        dao.update(updated);
        assertTrip(updated);

        Trip trip = dao.findById(first.id);
        Assert.assertEquals("Bar", trip.title);
        Assert.assertEquals(1512, trip.duration);

        dao.delete(trip);
        Assert.assertEquals(0, dao.selectAll().size());

    }

    private void assertTrip(Trip trip) {
        List<Trip> results = dao.selectAll();
        assertNotNull(results);
        Assert.assertEquals(1, results.size());
        assertTrue(areIdentical(trip, results.get(0)));
    }

    private boolean areIdentical(Trip one, Trip two) {
        return(one.id.equals(two.id) &&
                one.title.equals(two.title) &&
                one.duration==two.duration);
    }
}
