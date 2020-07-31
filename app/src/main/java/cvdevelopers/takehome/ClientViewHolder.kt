package cvdevelopers.takehome

import android.app.Application
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import cvdevelopers.githubstalker.R
import cvdevelopers.githubstalker.databinding.ClientItemBinding
import cvdevelopers.takehome.models.Client
import cvdevelopers.takehome.utils.image.ImageLoader

class ClientViewHolder(val view: ClientItemBinding) : RecyclerView.ViewHolder(view.root) {

    fun bind(client: Client) {
        client.picture?.thumbnail?.let {
            ImageLoader(itemView.context.applicationContext as Application).loadCircularImage(
                it, view.imageView
            )
        }
        Log.d("name", itemView.resources.getString(
            R.string.user_name, client.name?.first, client.name?.last
        ))
        view.textView.text = itemView.resources.getString(
            R.string.user_name, client.name?.first, client.name?.last
        )
    }
}