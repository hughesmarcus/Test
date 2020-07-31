package cvdevelopers.takehome.api

import cvdevelopers.takehome.models.Client
import cvdevelopers.takehome.utils.Resource
import javax.inject.Inject

class ClientRepository @Inject constructor(
    private val randomUserApiEndpoint: RandomUserApiEndpoint,
    private val dao: ClientDao
) {
    suspend fun getClients(refresh: Boolean): Resource<List<Client>> {
        return try {
            val cachedClients = dao.getAll()
            if (cachedClients.isNullOrEmpty() || refresh) {
                val clients = randomUserApiEndpoint.getClient("1")
                    .body()?.results ?: listOf()
                dao.clear()
                dao.insertAll(clients)
                Resource.update(clients)
            } else {
                Resource.update(cachedClients)
            }
        } catch (e: Throwable) {
            Resource.error(e.localizedMessage)
        }
    }
}