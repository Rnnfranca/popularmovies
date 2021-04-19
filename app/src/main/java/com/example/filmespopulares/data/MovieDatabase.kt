package com.example.filmespopulares.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/**
 * This class contains the database holder and servers as the main access point for the underlying
 * connection to the app persisted, relational data.
 * This need to be abstract
 */
@Database(entities = [MovieEntity::class], version = 1, exportSchema = false) //specified that it's a database and which are the entities. In this case there is only one
abstract class MovieDatabase : RoomDatabase(){

    // A function that will return the userDao created into UserDao interface
    abstract fun movieDao(): MovieDao

    companion object{
        @Volatile // this means that writes to this field are immediately made visible to other threads
        private var INSTANCE: MovieDatabase? = null

        // function to get or create the database
        fun getDatabase(context: Context): MovieDatabase {
            val tempInstance = INSTANCE

            // check if database already exists
            if (tempInstance != null) {
                return tempInstance
            }

            // if not exist an instance for our database, so is created a new one

            // synchronized means that everything within that block will be protected from a
            // current execution by multiple threads and within this block we are creating
            // and instance of our room database
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,

                    MovieDatabase::class.java,
                    "movie_database" // database name
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}