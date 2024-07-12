package hr.algebra.schengenexplorer.adapter

import android.content.ContentUris
import android.content.ContentValues
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

class ItemPagerAdapter(
    private val context: Context,
    private val items: MutableList<Item>
) : RecyclerView.Adapter<ItemPagerAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivItem = itemView.findViewById<ImageView>(R.id.ivItem)
        val ivRead = itemView.findViewById<ImageView>(R.id.ivRead)

        private val tvCommon = itemView.findViewById<TextView>(R.id.tvCommon)
        private val tvOfficial = itemView.findViewById<TextView>(R.id.tvOfficial)
        private val tvFlagDescription = itemView.findViewById<TextView>(R.id.tvFlagDescription)
        fun bind(item: Item) {
            tvCommon.text = item.commonName
            Picasso.get()
                .load(File(item.picturePath))
                .error(R.drawable.schengen_explorer_icon)
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivItem)

            tvCommon.text=item.commonName
            tvOfficial.text=item.officialName
            tvFlagDescription.text=item.flagDescription
            ivRead.setImageResource(if(item.read) R.drawable.green_flag else R.drawable.red_flag)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_pager, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.ivRead.setOnClickListener {
            updateItem(position)
        }


        holder.bind(items[position])
    }

    private fun updateItem(position: Int) {
        val item = items[position]
        item.read = !item.read
        context.contentResolver.update(
            ContentUris.withAppendedId(SCHENGEN_PROVIDER_CONTENT_URI, item._id!!),
            ContentValues().apply {
                put(Item::read.name, item.read)
            },
            null,
            null
        )
        notifyItemChanged(position)
    }
    }
