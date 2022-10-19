package com.javimartd.theguardian.v2.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.javimartd.theguardian.v2.R
import com.javimartd.theguardian.v2.ui.extensions.*
import com.javimartd.theguardian.v2.ui.model.NewsUi

class NewsAdapter(private val onReadMoreClickListener: (String) -> Unit)
    : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var items = listOf<NewsUi>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, onReadMoreClickListener)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val view = parent.inflate(R.layout.item_recycler_view)
                return ViewHolder(view)
            }
        }

        private val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        private val textDate: TextView = itemView.findViewById(R.id.textDate)
        private val buttonReadMore: Button = itemView.findViewById(R.id.buttonReadMore)
        private val textDescription: TextView = itemView.findViewById(R.id.textDescription)
        private val image: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(
            item: NewsUi,
            onReadMoreClickListener: (String) -> Unit
        ) {
            Glide.with(image.ctx)
                .load(item.thumbnail)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(image)
            textTitle.text = item.title
            textDate.text = item.date.toLong(FORMAT_DATE_TIME_API).toDateString()
            textDescription.text = item.body
            buttonReadMore.setOnClickListener {
                onReadMoreClickListener(item.webUrl)
            }
        }
    }
}