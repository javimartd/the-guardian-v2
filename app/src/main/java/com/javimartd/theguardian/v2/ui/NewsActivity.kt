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
import com.javimartd.theguardian.v2.databinding.ActivityNewsBinding
import com.javimartd.theguardian.v2.ui.adapter.NewsAdapter
import com.javimartd.theguardian.v2.ui.common.DialogActions
import com.javimartd.theguardian.v2.ui.common.LoadingDialog
import com.javimartd.theguardian.v2.ui.model.NewsItemUiState
import com.javimartd.theguardian.v2.ui.model.NewsUiState
import com.javimartd.theguardian.v2.ui.viewmodel.NewsUiEvent
import com.javimartd.theguardian.v2.ui.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private lateinit var viewModel: NewsViewModel
    //private val viewModel: NewsViewModel by viewModels()
    private lateinit var loading: DialogActions
    private lateinit var newsAdapter: NewsAdapter

    private val contentObserver = Observer<NewsUiState> { uiState ->
        when (uiState) {
            is NewsUiState.Loading -> showLoading()
            is NewsUiState.ShowSections -> {
                hideLoading()
                showSections(uiState.sections)
            }
            is NewsUiState.ShowNews -> {
                hideLoading()
                showNews(uiState.news)
            }
            is NewsUiState.ShowError -> {
                hideLoading()
                showError(getString(uiState.message))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        viewModel.uiState.observe(this, contentObserver)

        loading = LoadingDialog(this)

        newsAdapter = NewsAdapter { openUrl(it) }
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

    private fun showNews(news: List<NewsItemUiState>) {
        newsAdapter.items = news
    }

    private fun showSections(sections: List<String>) {
        val sectionsAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            sections
        )
        sectionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        (binding.autoCompleteTextView as? AutoCompleteTextView)?.setAdapter(sectionsAdapter)
        binding.autoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                viewModel.onEvent(NewsUiEvent.SectionSelected(sections[position]))
            }
    }

    private fun openUrl(webUrl: String) {
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