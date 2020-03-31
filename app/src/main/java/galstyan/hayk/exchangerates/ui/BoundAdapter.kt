package galstyan.hayk.exchangerates.ui

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


/**
 * Delegates binding logic to ViewHolders
 */
abstract class BoundViewHolderAdapter : RecyclerView.Adapter<BoundViewHolder>() {
    override fun onBindViewHolder(holder: BoundViewHolder, position: Int) {
        holder.bind()
    }
}


/**
 * Delegates binding logic to ViewHolders
 */
abstract class BoundViewHolderListAdapter<T>(differ: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, BoundViewHolder>(differ) {

    override fun onBindViewHolder(holder: BoundViewHolder, position: Int) {
        holder.bind()
    }
}


/**
 * Base class for bound ViewHolders
 */
abstract class BoundViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind()
}