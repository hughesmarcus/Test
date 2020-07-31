package cvdevelopers.takehome.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cvdevelopers.takehome.models.Client

@Dao
interface ClientDao {
    @Query("SELECT * FROM client") suspend fun getAll(): List<Client>

    @Insert suspend fun insertAll(clients: List<Client>)

    @Query("DELETE FROM client") suspend fun clear()
}
