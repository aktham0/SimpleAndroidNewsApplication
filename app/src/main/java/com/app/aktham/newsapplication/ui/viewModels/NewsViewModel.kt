package com.app.aktham.newsapplication.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.aktham.newsapplication.models.NewsDetailsModel
import com.app.aktham.newsapplication.repositories.ConfigRepository
import com.app.aktham.newsapplication.repositories.NewsRepository
import com.app.aktham.newsapplication.retrofit.models.NewsMainResponse
import com.app.aktham.newsapplication.utils.Constants.PAGE_SIZE_MAX
import com.app.aktham.newsapplication.utils.DataMapping_Imp
import com.app.aktham.newsapplication.utils.NewsRecourses
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

private const val TAG = "NewsViewModel"

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val configRepository: ConfigRepository,
    private val newsRepository: NewsRepository,
    private val dataMappingImp: DataMapping_Imp
) : ViewModel() {

    private val _newsLiveData: MutableLiveData<NewsRecourses> = MutableLiveData()
    val newsLiveData: LiveData<NewsRecourses> = _newsLiveData

    private val _newsSearchLiveData: MutableLiveData<NewsRecourses> = MutableLiveData(NewsRecourses.Empty)
    val newsSearchLiveData: LiveData<NewsRecourses> = _newsSearchLiveData

    val detailsNewsList = mutableListOf<NewsDetailsModel>()

    init {
        getNewsData(GetNewsDataEvents.TopHeadlinesNews())
    }

    // Get News
    private fun getTopHeadlinesNews(
        country: String = "",
        language: String = "",
        category: String = "",
        pageSize: Int = 100,
    ) {
        // Loading
        _newsLiveData.value = NewsRecourses.Loading
        newsRepository.getTopHeadlinesNews(
            countryName = country,
            language = language,
            pageSize = pageSize,
            category = category
        ).enqueue(object : Callback<NewsMainResponse> {
            override fun onResponse(
                call: Call<NewsMainResponse>,
                response: Response<NewsMainResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.articles.isEmpty()){
                        // Success But Not Data Founds
                        _newsLiveData.value = NewsRecourses.Empty
                        detailsNewsList.clear()
                    } else {
                        _newsLiveData.value = NewsRecourses.Success(
                            dataMappingImp.responseToModel(response = response.body()!!)
                        )
                        detailsNewsList.clear()
                        detailsNewsList.addAll(
                            dataMappingImp.responseToDetailNewsModel(response = response.body()!!)
                        )
                    }
                } else {
                    _newsLiveData.value =
                        NewsRecourses.Errors("Some Thing Wrong!")
                }
            }

            override fun onFailure(call: Call<NewsMainResponse>, t: Throwable) {
                _newsLiveData.value = NewsRecourses.Errors("No Internet Connection")

                Log.e(TAG, "Error", t)
            }
        })
    }


    // Search News
    private fun searchNews(search: String) {
        // Loading
        _newsSearchLiveData.value = NewsRecourses.Loading

        newsRepository.searchNews(search, configRepository.loadContentLanguageState())
            .enqueue(object : Callback<NewsMainResponse> {
                override fun onResponse(
                    call: Call<NewsMainResponse>,
                    response: Response<NewsMainResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        if (response.body()!!.articles.isEmpty()){
                            // Success Response But No Data Found
                            _newsSearchLiveData.value = NewsRecourses.Empty
                        } else {
                            // Success
                            _newsSearchLiveData.value = NewsRecourses.Success(
                                dataMappingImp.responseToSearchModel(response = response.body()!!)
                            )
                        }

                    } else {
                        // Failure
                        _newsSearchLiveData.value = NewsRecourses.Errors("Some Thing Wrong!")
                    }
                }

                override fun onFailure(call: Call<NewsMainResponse>, t: Throwable) {
                    // Failure (Not Internet Connection)
                    _newsSearchLiveData.value = NewsRecourses.Errors("No Internet Connection")

                    Log.e(TAG, "Error", t)
                }
            })
    }


    // Get Data Events
    fun getNewsData(dataEvent: GetNewsDataEvents) {
        when (dataEvent) {
            is GetNewsDataEvents.TopHeadlinesNews -> {
                // Get News TopHeadLine News
                getTopHeadlinesNews(
                    language = configRepository.loadContentLanguageState(),
                    pageSize = PAGE_SIZE_MAX
                )
            }

            is GetNewsDataEvents.NewsByCategory -> {
                // Get News By Category
                getTopHeadlinesNews(
                    language = configRepository.loadContentLanguageState(),
                    pageSize = PAGE_SIZE_MAX,
                    category = dataEvent.category
                )
            }

            is GetNewsDataEvents.NewsByQuery -> {
                // Get Searched News
                searchNews(dataEvent.searchQuery)
            }
        }
    }

    // Application Config Load State
    fun loadContentLanguageState() = configRepository.loadContentLanguageState()
    fun loadCountrySelected() = configRepository.loadCountry()
    fun loadAppStyle() = configRepository.loadAppStyle()

    // Get Data Events
    sealed class GetNewsDataEvents() {
        class TopHeadlinesNews() : GetNewsDataEvents()
        class NewsByCategory(val category: String) : GetNewsDataEvents()
        class NewsByQuery(val searchQuery: String) : GetNewsDataEvents()
    }
}