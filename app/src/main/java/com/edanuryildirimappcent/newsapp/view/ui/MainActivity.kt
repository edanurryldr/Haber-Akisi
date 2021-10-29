package com.edanuryildirimappcent.newsapp.view.ui

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edanuryildirimappcent.newsapp.adapter.RecyclerViewAdapter
import com.edanuryildirimappcent.newsapp.databinding.ActivityMainBinding
import com.edanuryildirimappcent.newsapp.model.Articles
import com.edanuryildirimappcent.newsapp.model.NewsModel
import com.edanuryildirimappcent.newsapp.service.NewsAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(),  RecyclerViewAdapter.Listener  {

    private lateinit var binding: ActivityMainBinding
    private val BASE_URL = "https://newsapi.org/v2/"
    private var newsModels: NewsModel? = null
    private var recyclerViewAdapter: RecyclerViewAdapter? = null

    //ProgressDialog
    lateinit var progerssProgressDialog: ProgressDialog

    //Disposable
    private var compositeDisposable : CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        compositeDisposable = CompositeDisposable()

        progerssProgressDialog=ProgressDialog(this)
        progerssProgressDialog.setTitle("Loading."+"  "+"Please waiting")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                recyclerViewAdapter?.filter?.filter(query)
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                recyclerViewAdapter?.filter?.filter(newText)
                return false
            }
        })

        loadData()
    }

    private fun loadData(){

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(NewsAPI::class.java)

        compositeDisposable?.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleRespone)
        )
    }

    override fun onItemClick(article: Articles) {
        val intent = Intent(applicationContext, DetailActivity::class.java)
        intent.putExtra("data",article)
        startActivity(intent)
    }

    override fun handleRespone(newsModel: NewsModel) {
        var articleList = ArrayList(newsModel.articles)
        articleList?.let { recyclerViewAdapter = RecyclerViewAdapter(articleList,this)
            val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = recyclerViewAdapter
        }
        progerssProgressDialog.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }

}