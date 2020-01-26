package com.hemingwaywest.utiliserve.database;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.Executors;

@Database(entities = {User.class, Forms.class, FormField.class}, version = 1, exportSchema = false)
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
    public abstract UserDao userDao();
    public abstract FormsDao formsDao();
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
                               // getInstance(context).formsDao().insertAll(Forms.prepopulateFormsData());
                                //getInstance(context).formFieldDao().insertAll(FormField.prepopulateFormFields());
                            }
                        });
                    }
                })
                .build();
    }

    public static void loadDatabase(final Context context){
        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
            @Override
            public void run() {
                getInstance(context).formsDao().insertAll(Forms.prepopulateFormsData());
            }
        });
    }
}
