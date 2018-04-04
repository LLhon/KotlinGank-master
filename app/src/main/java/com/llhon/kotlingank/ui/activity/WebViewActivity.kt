package com.llhon.kotlingank.ui.activity

import android.content.Context
import android.content.Intent
import android.webkit.WebView
import android.webkit.WebViewClient
import com.llhon.kotlingank.R
import com.llhon.kotlingank.base.BaseActivity
import kotlinx.android.synthetic.main.activity_webview.*

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.ui.activity
 * @author LLhon
 * @Date 2018/3/28 16:23
 * @description
 */
class WebViewActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_webview

    override fun initView() {
        var url = intent.getStringExtra(URL)
        setSupportActionBar(toolbar)
        var bar = supportActionBar
//        bar?.title = "Github"
//        bar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        webView.loadUrl(url)
        webView.setWebViewClient(object: WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        })
    }

    companion object {
        val URL = "url"
        fun startActivity(context: Context, url: String) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(URL, url)
            context.startActivity(intent)
        }
    }
}