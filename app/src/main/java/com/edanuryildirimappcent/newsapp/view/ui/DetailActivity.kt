package com.edanuryildirimappcent.newsapp.view.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.edanuryildirimappcent.newsapp.R
import com.edanuryildirimappcent.newsapp.databinding.ActivityDetailBinding
import com.edanuryildirimappcent.newsapp.databinding.ActivityMainBinding
import com.edanuryildirimappcent.newsapp.model.Articles
import com.edanuryildirimappcent.newsapp.model.NewsModel
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private var newsModels : ArrayList<NewsModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val selectedNews = intent.getSerializableExtra("data") as Articles

        binding.buttonIntent.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,WebViewActivity::class.java)
            intent.putExtra("data",selectedNews)
            startActivity(intent)
        })

        //casting
        binding.txtAuthorname.text = selectedNews.author
        binding.txtDate.text = selectedNews.publishedAt
        binding.txtInfo.text = selectedNews.description
        binding.txtOzet.text = selectedNews.title
        //val url2 = "${articleList[position].urlToImage}"
        val url2 = selectedNews.urlToImage
        Picasso.get().load(url2).into(binding.newsImage)

    }

    fun Share(view: android.view.View) {
        binding.shareButton.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,"News Author Name : ${binding.txtAuthorname.text}" +
                    "\n" + "News Title : ${binding.txtInfo.text}" + "\n" + "News Descprition : ${binding.txtOzet.text}" +
                    "\n" + "News PublishedAt : ${binding.txtDate.text}")
            intent.putExtra(Intent.EXTRA_SUBJECT,"NEWSPAPER MAIL INFORMATION")
            startActivity(Intent.createChooser(intent,"Share text via"))
        })
    }

    fun Fav(view: View) {

        val selectedNews = intent.getSerializableExtra("data") as Articles

        val authorName = selectedNews.author
        val title = selectedNews.title
        val url2 = selectedNews.urlToImage
        Picasso.get().load(url2).into(binding.newsImage)


        try{
            val database = this.openOrCreateDatabase("News", MODE_PRIVATE,null)
            database.execSQL("CREATE TABLE IF NOT EXISTS news(authorName VARCHAR,title VARCHAR,image BLOB)")

            val sqlString = "INSERT INTO news (authorName,title,image) VALUES(?,?,?)"
            val statement = database.compileStatement(sqlString)
            statement.bindString(1,authorName)
            statement.bindString(2,title)
            statement.bindString(3,url2)
            statement.execute()

        }catch (e:Exception){
            e.printStackTrace()
        }
        val intent = Intent(this@DetailActivity,FavoriActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("data",authorName)
        intent.putExtra("title",title)
        intent.putExtra("image",url2)
        startActivity(intent)
    }
}