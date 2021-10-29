package com.edanuryildirimappcent.newsapp.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.edanuryildirimappcent.newsapp.adapter.SQLiteAdapter
import com.edanuryildirimappcent.newsapp.databinding.ActivityFavoriBinding
import com.edanuryildirimappcent.newsapp.model.Articles
import com.squareup.picasso.Picasso

class FavoriActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriBinding
    private var sqliteAdapter: SQLiteAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /*
        binding.favrecyclerView.layoutManager = LinearLayoutManager(this)
        binding.favrecyclerView.adapter = sqliteAdapter
        */

        val selectedAuthorName = intent.getSerializableExtra("data") as String
        val selectedtitle = intent.getSerializableExtra("title") as String
        val image = intent.getSerializableExtra("image") as String

        try{

            val database = this.openOrCreateDatabase("News", MODE_PRIVATE,null)

            val cursor = database.rawQuery("SELECT * FROM news",null)
            val authorNameIx= cursor.getColumnIndex("authorName")

            while(cursor.moveToNext()){
                val name = cursor.getString(authorNameIx)
            }
            binding.textFavName.text = selectedAuthorName
            binding.txtAuthorname.text = selectedtitle
             val url2 = image
        Picasso.get().load(url2).into(binding.imageNews)

            sqliteAdapter?.notifyDataSetChanged()

            cursor.close()


        }
        catch (e:Exception){
            e.printStackTrace()
        }
        binding.deleteData.setOnClickListener(View.OnClickListener {
            //database.delete("News",null,null)
            val intent = Intent(this@FavoriActivity,DetailActivity::class.java)
            startActivity(intent)

        })

    }
}