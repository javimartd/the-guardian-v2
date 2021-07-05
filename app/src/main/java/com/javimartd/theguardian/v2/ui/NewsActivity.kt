package com.javimartd.theguardian.v2.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.javimartd.theguardian.v2.databinding.ActivityNewsBinding
import com.javimartd.theguardian.v2.ui.adapter.NewsAdapter
import com.javimartd.theguardian.v2.ui.common.DialogActions
import com.javimartd.theguardian.v2.ui.common.LoadingDialog
import com.javimartd.theguardian.v2.ui.factory.NewsViewModelFactory
import com.javimartd.theguardian.v2.ui.model.News
import com.javimartd.theguardian.v2.ui.state.NewsViewState


class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private lateinit var viewModelFactory: NewsViewModelFactory
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var loading: DialogActions

    private val contentObserver = Observer<NewsViewState> { state ->
        when (state) {
            is NewsViewState.Loading -> showLoading()
            is NewsViewState.LoadData -> loadData(state.sections, state.news)
            is NewsViewState.ShowNews -> showNews(state.news)
            is NewsViewState.Error -> TODO()
        }
    }

    private fun loadData(sections: List<String>, news: List<News>) {
        hideLoading()
        showSections(sections)
        showNews(news)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelFactory = NewsViewModelFactory(ServiceLocator.getTheGuardianApi())
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)

        viewModel.content.observe(this, contentObserver)

        loading = LoadingDialog(this)

        newsAdapter = NewsAdapter { openLink(it) }
        binding.recycler.adapter = newsAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        loading.onDetach()
    }

    private fun showLoading() {
        loading.showDialog()
    }

    private fun hideLoading() {
        loading.hideDialog()
    }

    private fun showNews(news: List<News>) {
        newsAdapter.items = news
    }

    private fun showSections(sections: List<String>) {
        val sectionsAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sections)
        sectionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        (binding.autoCompleteTextView as? AutoCompleteTextView)?.setAdapter(sectionsAdapter)
        binding.autoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                viewModel.fetchNews(sections[position])
            }
    }

    private fun openLink(webUrl: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
        startActivity(browserIntent)
    }
}