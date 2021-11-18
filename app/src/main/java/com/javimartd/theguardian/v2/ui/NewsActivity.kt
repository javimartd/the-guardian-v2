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
import com.google.android.material.snackbar.Snackbar
import com.javimartd.theguardian.v2.R
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
            is NewsViewState.ShowNewsAndSections -> {
                hideLoading()
                showNewsAndSections(state.sections, state.news)
            }
            is NewsViewState.ShowNews -> {
                hideLoading()
                showNews(state.news)
            }
            is NewsViewState.ShowNetworkError -> {
                hideLoading()
                showError(getString(R.string.network_error))
            }
            is NewsViewState.ShowGenericError -> {
                hideLoading()
                showError(getString(R.string.generic_error))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelFactory = NewsViewModelFactory(ServiceLocator.getRepository())
        viewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]

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

    private fun showNewsAndSections(sections: List<String>, news: List<News>) {
        showSections(sections)
        showNews(news)
    }

    private fun showSections(sections: List<String>) {
        val sectionsAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sections)
        sectionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        (binding.autoCompleteTextView as? AutoCompleteTextView)?.setAdapter(sectionsAdapter)
        binding.autoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                viewModel.onSelectedSection(sections[position])
            }
    }

    private fun openLink(webUrl: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
        startActivity(browserIntent)
    }

    private fun showError(message: String) {
        Snackbar.make(
            binding.newsLayout,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }
}