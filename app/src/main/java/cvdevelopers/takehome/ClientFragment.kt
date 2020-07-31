package cvdevelopers.takehome

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cvdevelopers.githubstalker.databinding.FragmentClientBinding
import javax.inject.Inject

class ClientFragment : Fragment() {
    @Inject lateinit var clientViewModel: ClientViewModel
    private lateinit var swipeContainer: SwipeRefreshLayout
    private lateinit var clientListAdapter: ClientListAdapter

    private var _binding: FragmentClientBinding? = null

    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as LuminaryTakeHomeApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClientBinding.inflate(inflater, container, false)
        val view = binding.root
        setUpClientList()
        return view
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        swipeContainer = binding.swipeContainer
        swipeContainer.setOnRefreshListener {
            clientViewModel.reloadClients()
        }

        clientViewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is ClientViewModel.ClientState.Loaded -> {
                    clientListAdapter.update(state.clients)
                    swipeContainer.isRefreshing = false
                    binding.progress.hide()
                }
                is ClientViewModel.ClientState.Error -> {
                    swipeContainer.isRefreshing = false
                    binding.progress.hide()
                }
                is ClientViewModel.ClientState.Refreshed -> {
                    clientListAdapter.update(state.clients)
                    swipeContainer.isRefreshing = false
                    binding.progress.hide()
                }
                is ClientViewModel.ClientState.Loading -> {
                    binding.progress.show()
                }
            }
        })
    }

    private fun setUpClientList() {
        clientListAdapter = ClientListAdapter()
        binding.clientList.apply {
            adapter = clientListAdapter
            layoutManager = LinearLayoutManager(
                this@ClientFragment.context,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    companion object {
        @JvmStatic fun newInstance() = ClientFragment()
    }
}