package cvdevelopers.takehome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cvdevelopers.githubstalker.databinding.ClientItemBinding
import cvdevelopers.takehome.models.Client
import java.util.*

class ClientListAdapter() : RecyclerView.Adapter<ClientViewHolder>() {

    private val items: MutableList<Client> = ArrayList<Client>()

    fun update(newItems: List<Client>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClientViewHolder {
        return ClientViewHolder(ClientItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(
        holder: ClientViewHolder,
        position: Int
    ) {
        val client = items[position]
        holder.bind(client)
    }

}