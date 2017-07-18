package com.kanilturgut.architecturecomponent.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Author   : kanilturgut
 * Date     : 18/07/2017
 * Time     : 21:05
 */

@Dao
public interface TripDAO {

    @Query("select * from trips order by title")
    List<Trip> selectAll();

    @Query("select * from trips where id=:id")
    Trip findById(String id);

    @Insert
    void insert(Trip... trips);

    @Update
    void update(Trip... trips);

    @Delete
    void delete(Trip... trips);
}
