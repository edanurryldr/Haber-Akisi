package com.edanuryildirimappcent.newsapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.edanuryildirimappcent.newsapp.R
import com.edanuryildirimappcent.newsapp.model.Articles
import com.edanuryildirimappcent.newsapp.model.NewsModel
import com.edanuryildirimappcent.newsapp.view.ui.DetailActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.view.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.row_layout.view.*
import kotlinx.android.synthetic.main.row_layout.view.txt_authorname

class RecyclerViewAdapter(private val articleList: ArrayList<Articles>, private val listener: Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>(),
    Filterable {

    var articleListFiltered = articleList

    interface Listener {
        fun onItemClick(article: Articles)
        fun handleRespone(newsModel: NewsModel)
    }

    class RowHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(article:Articles, position: Int,listener: Listener){
            itemView.setOnClickListener {
                listener.onItemClick(article)
            }
            itemView.text_name.text = article.author
            itemView.txt_authorname.text=article.title

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        return RowHolder(view)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        //hangi item ne verisi gösterecek onu yazıyoruz
        holder.bind(articleListFiltered[position],position,listener)

        //Image
        val url2 = "${articleList[position].urlToImage}"
        Picasso.get().load(url2).into(holder.itemView.imageNews)
    }

    override fun getItemCount(): Int {
        return articleListFiltered.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) articleListFiltered = articleList else {
                    val filteredList = ArrayList<Articles>()
                    articleList
                        .filter {
                            (it.title.contains(constraint!!)) or
                                    (it.author.contains(constraint))

                        }
                        .forEach { filteredList.add(it) }
                    articleListFiltered = filteredList

                }
                return FilterResults().apply { values = articleListFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                articleListFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Articles>
                notifyDataSetChanged()
            }
        }
    }
}