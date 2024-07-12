package hr.algebra.schengenexplorer.adapter

import android.content.ContentUris
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.schengenexplorer.ItemPagerActivity
import hr.algebra.schengenexplorer.SCHENGEN_PROVIDER_CONTENT_URI
import hr.algebra.schengenexplorer.R
import hr.algebra.schengenexplorer.Model.Item
import hr.algebra.schengenexplorer.POSITION
import hr.algebra.schengenexplorer.framework.startActivity
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File
import java.util.Locale

class ItemAdapter(

    private val context: Context,
    private var items: MutableList<Item>,
    private var itemsFull: List<Item> = ArrayList(items)
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivItem = itemView.findViewById<ImageView>(R.id.ivItem)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        fun bind(item: Item) {
            tvTitle.text = item.commonName
            Picasso.get()
                .load(File(item.picturePath))
                .error(R.drawable.schengen_explorer_icon)
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivItem)
        }
    }
    fun filter(filterPattern: String) {
        val filteredList = if (filterPattern.isEmpty()) {
            itemsFull
        } else {
            val lowerCasePattern = filterPattern.lowercase(Locale.getDefault())
            itemsFull.filter {
                it.commonName.lowercase(Locale.getDefault()).startsWith(lowerCasePattern)
            }
        }

        items = filteredList.toMutableList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.setOnLongClickListener {
            val item = items[position]
            context.contentResolver.delete(
                ContentUris.withAppendedId(SCHENGEN_PROVIDER_CONTENT_URI, item._id!!),
                null, null
            )
            File(item.picturePath).delete()
            items.removeAt(position)
            notifyDataSetChanged()

            true
        }

        holder.itemView.setOnClickListener {
            context.startActivity<ItemPagerActivity>(POSITION, position)
        }


        holder.bind(items[position])
    }
}