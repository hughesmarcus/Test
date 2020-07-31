package cvdevelopers.takehome.api

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cvdevelopers.takehome.models.Client

@Database(entities = [Client::class], version = 4)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun clientDao(): ClientDao

    companion object {

        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context, AppDatabase::class.java, "database-name"
            ).fallbackToDestructiveMigration().build()
        }
    }
}