package com.hemingwaywest.utiliserve.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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
