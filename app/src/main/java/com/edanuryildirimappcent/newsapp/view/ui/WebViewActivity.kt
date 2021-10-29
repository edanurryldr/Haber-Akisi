package com.edanuryildirimappcent.newsapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.edanuryildirimappcent.newsapp.databinding.ActivityWebViewBinding
import com.edanuryildirimappcent.newsapp.model.Articles
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val WebedNews = intent.getSerializableExtra("data") as Articles

        binding.webViewApp.webViewClient = WebViewClient()

        webViewApp.loadUrl(WebedNews.url)

        webViewApp.settings.javaScriptEnabled = true

        webViewApp.settings.setSupportZoom(true)
    }

    override fun onBackPressed()
    {
        if(webViewApp.canGoBack())
        {
            webViewApp.goBack()
        }else
        {
            super.onBackPressed()
        }
    }
}