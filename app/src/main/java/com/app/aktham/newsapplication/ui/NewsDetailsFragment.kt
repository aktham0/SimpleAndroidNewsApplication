package com.app.aktham.newsapplication.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import coil.load
import com.app.aktham.newsapplication.R
import com.app.aktham.newsapplication.databinding.FragmentNewsDetailsBinding
import com.app.aktham.newsapplication.models.NewsDetailsModel
import com.app.aktham.newsapplication.ui.viewModels.NewsViewModel

class NewsDetailsFragment : Fragment(R.layout.fragment_news_details) {

    private var _binding: FragmentNewsDetailsBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel: NewsViewModel by activityViewModels()
    private var _newsDetails: NewsDetailsModel? = null
    private val newsDetails get() = _newsDetails!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsDetailsBinding.bind(view)

        arguments?.let {
            val itemPosition = it.getInt("itemPosition")
            _newsDetails = newsViewModel.detailsNewsList[itemPosition]
        }
        binding.newsDetailsTitle.text = newsDetails.title
        binding.newsDetailsAuthor.text = newsDetails.author
        binding.newsDetailsBody.text = newsDetails.newsBody
        binding.newsDetailsPublishDate.text = newsDetails.publishDate
        binding.newsDetailsDescription.text = newsDetails.description
        binding.newsDetailsByPublish.text = newsDetails.publishBy
        binding.newsDetailsImage.load(newsDetails.image){
            placeholder(R.drawable.ic_baseline_image_search_24)
        }

        // Go To News Page Button Click Action
        binding.pageLinkFab.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply{
                data = Uri.parse(newsDetails.pageLink)
            }
            startActivity(intent)
        }

        binding.pageLinkFab.animate().apply {
            translationX(0f)
            duration = 400L
            start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}