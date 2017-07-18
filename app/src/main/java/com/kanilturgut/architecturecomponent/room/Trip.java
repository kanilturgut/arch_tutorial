package com.kanilturgut.architecturecomponent.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.UUID;

/**
 * Author   : kanilturgut
 * Date     : 18/07/2017
 * Time     : 20:56
 * <p>
 * <p>
 * fields must be public or have getter/setter. If a field is final (as final field will lack setters)
 * Room will try to find a constructor to use to populate the fields
 */

@Entity(tableName = "trips")
public class Trip {

    @PrimaryKey
    public final String id;

    public final String title;
    public final int duration;

    @Ignore
    public Trip(String title, int duration) {
        this(UUID.randomUUID().toString(), title, duration);
    }

    public Trip(String id, String title, int duration) {
        this.id = id;
        this.title = title;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return (title);
    }
}
