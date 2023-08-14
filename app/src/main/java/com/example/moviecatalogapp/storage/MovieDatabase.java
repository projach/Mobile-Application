package com.example.moviecatalogapp.storage;

import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//change the version every time that the database is changed
@Database(entities = {SQLSchema.class, SQLSchemaDetails.class}, version = 13)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract Dao movieDao();
    public abstract DaoDetails movieDaoDetails();

    private static volatile MovieDatabase INSTANCE;

    public static MovieDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovieDatabase.class) {
                if (INSTANCE == null) {
                    // fallback is used to destroy the whole database and create it from scratch when we change something(there is data loss)
                    // allowMainThread is used to allow the database to run in the same thread as the main action(maybe change it in the end if the actual
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MovieDatabase.class, "movie_database").fallbackToDestructiveMigration().allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
