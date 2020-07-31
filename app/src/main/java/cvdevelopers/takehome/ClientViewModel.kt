package cvdevelopers.takehome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import cvdevelopers.takehome.api.ClientRepository
import cvdevelopers.takehome.models.Client
import cvdevelopers.takehome.utils.Resource
import cvdevelopers.takehome.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ClientViewModel @Inject constructor(
    private val clientRepository: ClientRepository
) : ScopedViewModel() {

    private val _clientLiveData = MutableLiveData<Resource<List<Client>>>()

    private val clientLiveData: LiveData<Resource<List<Client>>>
        get() = _clientLiveData

    private val stateLiveDate = MediatorLiveData<ClientState>()

    val state: LiveData<ClientState>
        get() = stateLiveDate

    private val clientResourceObserver = Observer<Resource<List<Client>>> { resource ->
        when (resource?.status) {
            Status.LOADING -> {
                dispatchState(ClientState.Loading)
            }
            Status.ERROR -> {
                dispatchState(ClientState.Error(resource.message))
            }
            Status.SUCCESS -> {
                resource.data?.let {
                    dispatchState(ClientState.Loaded(it))
                }
            }
            Status.UPDATED -> {
                resource.data?.let {
                    dispatchState(ClientState.Refreshed(it))
                }
            }
        }
    }

    init {
        stateLiveDate.addSource(_clientLiveData, clientResourceObserver)
        launch {
            val clients = clientRepository.getClients(refresh = false)
            _clientLiveData.postValue(clients)
        }
    }

    private fun dispatchState(state: ClientState) {
        stateLiveDate.value = state
    }

    fun reloadClients() {
        launch {
            val clients = withContext(Dispatchers.IO) {
                clientRepository.getClients(refresh = true)
            }
            clients.let {
                _clientLiveData.value = it
            }
        }
    }

    fun dispose() {
        _clientLiveData.removeObserver(clientResourceObserver)
    }

    @ExperimentalCoroutinesApi override fun onCleared() {
        super.onCleared()
        dispose()
    }

    sealed class ClientState {
        object Loading : ClientState()
        data class Refreshed(val clients: List<Client>) : ClientState()
        data class Loaded(val clients: List<Client>) : ClientState()
        data class Error(val error: String?) : ClientState()
    }
}