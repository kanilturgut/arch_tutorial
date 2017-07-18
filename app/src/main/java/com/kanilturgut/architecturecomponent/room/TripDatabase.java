package com.kanilturgut.architecturecomponent.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Author   : kanilturgut
 * Date     : 18/07/2017
 * Time     : 21:09
 */

@Database(entities = {Trip.class}, version = 1)
public abstract class TripDatabase extends RoomDatabase {

    public abstract TripDAO tripDao();

    private static final String DB_NAME = "trips.db";
    private static volatile TripDatabase instance = null;

    synchronized static TripDatabase get(Context context) {
        if (null == instance) {
            instance = create(context, false);
        }
        return instance;
    }

    public static TripDatabase create(Context context, boolean memoryOnly) {

        RoomDatabase.Builder<TripDatabase> builder;

        if (memoryOnly) {
            /*
                it creates an in-memory SQLite database, useful for instrumentation tests
                where you do not necessarily need to persist the data for a user
             */
            builder = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                    TripDatabase.class);
        } else {
            builder = Room.databaseBuilder(context.getApplicationContext(), TripDatabase.class,
                    DB_NAME);
        }

        return builder.build();
    }
}
