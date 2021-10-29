package com.edanuryildirimappcent.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edanuryildirimappcent.newsapp.R
import com.edanuryildirimappcent.newsapp.model.Articles
import kotlinx.android.synthetic.main.fav_row_layout.view.*
import kotlinx.android.synthetic.main.row_layout.view.*


class SQLiteAdapter(private val articleList: ArrayList<Articles>,val clickLambda: (Int)->(Unit)) : RecyclerView.Adapter<SQLiteAdapter.FavRowHolder>() {

    class FavRowHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textAuthorName : TextView = itemView.text_fav_name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavRowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fav_row_layout,parent,false)
        return FavRowHolder(view)
    }

    override fun onBindViewHolder(holder: FavRowHolder, position: Int) {
        val currentItem = articleList[position]
        holder.textAuthorName.text = currentItem.title
        }

    override fun getItemCount(): Int {
        return articleList.size

    }


}
