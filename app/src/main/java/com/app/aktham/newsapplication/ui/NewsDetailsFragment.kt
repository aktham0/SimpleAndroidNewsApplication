package com.app.aktham.newsapplication.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import coil.load
import com.app.aktham.newsapplication.R
import com.app.aktham.newsapplication.databinding.FragmentNewsDetailsBinding
import com.app.aktham.newsapplication.models.NewsModel

class NewsDetailsFragment : Fragment(R.layout.fragment_news_details) {

    private var _binding: FragmentNewsDetailsBinding? = null
    private val binding get() = _binding!!

    private var newsDetails: NewsModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsDetailsBinding.bind(view)

        if (savedInstanceState?.getParcelable<NewsModel>("details") == null) {
            // get news object
            newsDetails = arguments?.getParcelable("newsObject") as NewsModel?
            newsDetails?.let { news ->
                binding.newsDetailsTitle.text = news.newsTitle
                binding.newsDetailsAuthor.text = news.newsAuthor
                binding.newsDetailsBody.text = news.newsContent
                binding.newsDetailsPublishDate.text = news.newsPublishDate
                binding.newsDetailsDescription.text = news.newsDescription
                binding.newsDetailsByPublish.text = news.newsPublishBy
                binding.newsDetailsImage.load(news.newsImageUrl) {
                    placeholder(R.drawable.ic_baseline_image_search_24)
                }

                // Go To News Page Button Click Action
                binding.pageLinkFab.setOnClickListener {
                    Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(news.newsLink)
                        startActivity(this)
                    }
                }
            }
        } else {
            newsDetails = savedInstanceState.getParcelable("details")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // save news model data
        outState.putParcelable("details", newsDetails)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}