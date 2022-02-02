package com.app.aktham.newsapplication.ui.viewModels

import androidx.lifecycle.*
import com.app.aktham.newsapplication.models.NewsModel
import com.app.aktham.newsapplication.models.toNewsEntity
import com.app.aktham.newsapplication.repositories.ConfigRepository
import com.app.aktham.newsapplication.repositories.NewsRepository
import com.app.aktham.newsapplication.retrofit.models.toNewsListModel
import com.app.aktham.newsapplication.room.NewsEntity
import com.app.aktham.newsapplication.room.toNewsModel
import com.app.aktham.newsapplication.utils.Constants
import com.app.aktham.newsapplication.utils.Constants.PAGE_SIZE_MAX
import com.app.aktham.newsapplication.utils.NewsRecourses
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

private const val TAG = "NewsViewModel"

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val configRepository: ConfigRepository,
    private val newsRepository: NewsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _newsLiveData: MutableLiveData<NewsRecourses> = MutableLiveData()
    val newsLiveData: LiveData<NewsRecourses> = _newsLiveData

    private val _newsSearchLiveData: MutableLiveData<NewsRecourses> = MutableLiveData(NewsRecourses.Init)
    val newsSearchLiveData: LiveData<NewsRecourses>  = _newsSearchLiveData

    private val _newsArchiveArticlesLiveData: MutableLiveData<List<NewsModel>> = MutableLiveData()
    val newsArchiveArticlesLiveData: LiveData<List<NewsModel>>  = _newsArchiveArticlesLiveData


    var newsSelectedCategory = Constants.NewsCategories[0]

//    init {
//        // get news data
//        getNewsData(GetNewsDataEvents.TopHeadlinesNews)
//    }

    private fun getTopHeadlinesNews(
        language: String,
        pageSize: Int,
        category: String
    ) {
        viewModelScope.launch {
            // Send Loading State
            _newsLiveData.value = NewsRecourses.Loading

            withContext(Dispatchers.IO) {
                // get data
                try {
                    val response = newsRepository.getTopHeadlinesNews(language, pageSize, category)
                    if (response.isSuccessful && response.body() != null) {
                        response.body()?.let { responseData ->
                            // send success state with data
                            _newsLiveData.postValue(
                                NewsRecourses.Success(
                                    dataList = responseData.toNewsListModel()
                                )
                            )

                            // send no data state
                            if (responseData.articles.isEmpty())
                                _newsLiveData.postValue(
                                    NewsRecourses.Errors(error_message = "Data Not Founded")
                                )
                        }
                    } else {
                        // send error state
                        _newsLiveData.postValue(NewsRecourses.Errors("Failure To Get News Data"))
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    // send error state
                    _newsLiveData.postValue(NewsRecourses.Errors("No Internet Connection !"))
                }
            }
        }
    }

    private fun searchNews(searchQuery: String, language: String) {
        viewModelScope.launch {
            // Send Loading State
            _newsSearchLiveData.value = NewsRecourses.Loading

            withContext(Dispatchers.IO) {
                // get data
                try {
                    val response = newsRepository.searchNews(search = searchQuery, language = language)
                    if (response.isSuccessful && response.body() != null) {
                        response.body()?.let { responseData ->
                            // send success state with data
                            _newsSearchLiveData.postValue(
                                NewsRecourses.Success(
                                    dataList = responseData.toNewsListModel()
                                )
                            )

                            // send no data state
                            if (responseData.articles.isEmpty())
                                _newsSearchLiveData.postValue(
                                    NewsRecourses.Errors(error_message = "Data Not Founded")
                                )
                        }
                    } else {
                        // send error state
                        _newsSearchLiveData.postValue(NewsRecourses.Errors("Failure To Get Search News Data"))
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    // send error state
                    _newsSearchLiveData.postValue(NewsRecourses.Errors("No Internet Connection !"))
                }
            }
        }
    }


    // Get Data Events
    fun setNewsEvent(dataEvent: NewsDataEvents) {
        when (dataEvent) {
            is NewsDataEvents.NewsByCategory -> {
                // Get News By Category
                getTopHeadlinesNews(
                    language = configRepository.loadContentLanguageState(),
                    pageSize = PAGE_SIZE_MAX,
                    category = newsSelectedCategory
                )
            }

            is NewsDataEvents.NewsByQuery -> {
                // Get Searched News
                searchNews(
                    dataEvent.searchQuery,
                    language = configRepository.loadContentLanguageState()
                )
            }

            is NewsDataEvents.ClearSearchLiveData -> {
                // clear search liveData
                _newsSearchLiveData.value = NewsRecourses.Init
            }

            is NewsDataEvents.GetArchiveNews -> getAllArchiveArticles()
            is NewsDataEvents.InsertNewsArticle -> {
                insertNewsArticle(dataEvent.news)
            }
            is NewsDataEvents.DeleteNewsArticle -> {
                deleteNewsArticleFromDB(dataEvent.news)
            }

        }
    }


    // insert news article to room db
    private fun insertNewsArticle(news: NewsModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                newsRepository.insertNewsArticle(newsEntity = news.toNewsEntity())
            }
        }
    }

    // get News Articles Archives
    private fun getAllArchiveArticles() {
        viewModelScope.launch {
            newsRepository.getArchiveNewsArticles().collect { newsEntityList ->
                _newsArchiveArticlesLiveData.value = newsEntityList.map { it.toNewsModel() }
            }
        }
    }

    // delete All News Article From News Archive DataBase
    private fun deleteAllNewsArticleFromDB() {
        viewModelScope.launch { newsRepository.deleteAllNewsArticles() }
    }

    // delete News Article from db By Id
    private fun deleteNewsArticleFromDB(news: NewsModel) {
        viewModelScope.launch {
            newsRepository.deleteNewsArticle(news.toNewsEntity())
        }
    }

    // Application Config Load State
    fun loadContentLanguageState() = configRepository.loadContentLanguageState()
    fun loadCountrySelected() = configRepository.loadCountry()
    fun loadAppStyle() = configRepository.loadAppStyle()

    fun saveContentLanguageState(lang: String) = configRepository.saveContentLanguageState(lang)
    fun changeAppStyle(style: Int) = configRepository.changeAppStyle(style)

    // Get Data Events
    sealed class NewsDataEvents {
        class NewsByCategory(val category: String = "") : NewsDataEvents()
        class NewsByQuery(val searchQuery: String) : NewsDataEvents()
        object ClearSearchLiveData : NewsDataEvents()

        object GetArchiveNews :NewsDataEvents()
        class InsertNewsArticle(val news: NewsModel) : NewsDataEvents()
        class DeleteNewsArticle(val news: NewsModel) : NewsDataEvents()
    }
}