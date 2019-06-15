package com.hemingwaywest.utiliserve.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.Executors;

@Database(entities = {FormListEntry.class, Forms.class, FormField.class, FormType.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "UserForms";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context){
        if (sInstance == null){
            synchronized (LOCK){
                Log.d(LOG_TAG, "Creating a new database instance");
                sInstance = buildDatabase(context);
                //Loading fake data from method below
                //TODO Move fake load to somewhere else
                /*sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();*/
            }
        }
        Log.d(LOG_TAG, "Getting the Database Instance");
        return sInstance;
    }

    //Table List
    public abstract FormListDao formListDao();
    public abstract FormsDao formsDao();
    public abstract FormTypeDao formTypeDao();
    public abstract FormFieldDao formFieldDao();

    //Using this to load a demo forms list
    private static AppDatabase buildDatabase(final Context context){
        return Room.databaseBuilder(context,
                AppDatabase.class,
                DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                getInstance(context).formsDao().insertAll(Forms.prepopulateFormsData());
                            }
                        });
                    }
                })
                .build();
    }
}
