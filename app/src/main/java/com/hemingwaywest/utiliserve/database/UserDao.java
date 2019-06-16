package com.hemingwaywest.utiliserve.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Project: Utiliserve
 * HemingwayWest, LLC. Copyright (c) 2019  All rights reserved.
 * User: Samitoo
 * Date: 6/15/2019
 * url: www.HemingwayWest.com
 * Notes:
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM User")
    List<User> getAllUsers();

    @Insert
    void insertUser(User user);

    @Insert
    void insertAll(User ...users);

    @Update(onConflict = OnConflictStrategy.ABORT)
    void updateUser(User user);

    @Delete
    void deleteUser(User user);
}
