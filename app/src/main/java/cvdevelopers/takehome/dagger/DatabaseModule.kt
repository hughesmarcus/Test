package cvdevelopers.takehome.dagger

import android.app.Application
import cvdevelopers.takehome.api.AppDatabase
import cvdevelopers.takehome.api.ClientDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun providesDatabase(application: Application): AppDatabase {
        return AppDatabase.getDatabase(application)
    }

    @Singleton
    @Provides
    fun providesFollowDao(database: AppDatabase): ClientDao {
        return database.clientDao()
    }
}