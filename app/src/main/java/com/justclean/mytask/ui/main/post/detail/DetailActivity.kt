package com.justclean.mytask.ui.main.post.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.justclean.mytask.R
import com.justclean.mytask.data.db.entity.FavoritesData
import com.justclean.mytask.databinding.FragmentDetailsBinding
import com.justclean.mytask.ui.MainActivity
import com.justclean.mytask.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class DetailActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var detailsAdapter: DetailsAdapter
    private lateinit var networkConnection: NetworkConnection
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_details)
        setupRecyclerView()
        val data = intent.getIntExtra("id", 0)
        job = Job()
        networkConnection = NetworkConnection(this)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setTitle("")
        supportActionBar?.setSubtitle("")
        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java).apply {
            setIndex(data ?: 1)
        }
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        binding.progressBar.visibility = View.VISIBLE

        networkConnection.observe(this, Observer { isConnected ->
            if (!isConnected) {
                binding.contentLayout.snackbar(getString(R.string.no_internet))
                binding.addFavourite.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.contentLayout.visibility = View.VISIBLE
                detailViewModel.text.observe(this, Observer {
                    binding.addFavourite.setOnClickListener { view ->
                        if (!binding.recyclerView.isEmpty()) {
                            savingToFavourite(it)
                        } else {
                            binding.contentLayout.snackbar(getString(R.string.no_internet))
                        }

                    }
                })

            } else {

                binding.progressBar.visibility = View.GONE
                binding.contentLayout.visibility = View.VISIBLE
                detailViewModel.text.observe(this, Observer {
                    binding.addFavourite.setOnClickListener { view ->
                        savingToFavourite(it)

                    }
                    networkCall(it)
                })

            }

        })


    }

    private fun networkCall(it: Int) {
        val constraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val request = OneTimeWorkRequestBuilder<MyWorkerTask>().setConstraints(constraint).build()
        WorkManager.getInstance().enqueue(request)
        WorkManager.getInstance().getWorkInfoByIdLiveData(request.id)
            .observe(this, Observer { workInfo ->
                if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                    WorkManager.getInstance().cancelAllWork()
                    launch {
                        withContext(Dispatchers.Main) {
                            try {
                                val detailResponse = detailViewModel.getCommentList(it)
                                if (!detailResponse.isEmpty()) {
                                    detailsAdapter.differ.submitList(detailResponse)
                                    binding.recyclerView.visibility = View.VISIBLE
                                    binding.progressBar.visibility = View.GONE
                                    binding.contentLayout.visibility = View.VISIBLE
                                    binding.addFavourite.visibility = View.VISIBLE
                                    binding.noData.visibility = View.GONE
                                } else {
                                    binding.progressBar.visibility = View.GONE
                                    binding.contentLayout.visibility = View.VISIBLE
                                    binding.noData.visibility = View.VISIBLE
                                }
                            } catch (e: APIExceptions) {
                                binding.progressBar.visibility = View.GONE
                                binding.contentLayout.visibility = View.VISIBLE
                                binding.noData.visibility = View.VISIBLE
                                e.printStackTrace()
                            } catch (e: NoInternetException) {
                                binding.contentLayout.snackbar(e.message!!)
                                binding.progressBar.visibility = View.GONE
                                binding.contentLayout.visibility = View.VISIBLE
                                binding.noData.visibility = View.VISIBLE
                                e.printStackTrace()
                            }


                        }
                    }
                }
            })
    }


    private fun savingToFavourite(it: Int) {
        detailViewModel.getPostDataById(it).observe(this, Observer { postData ->
            if (postData != null) {
                val favData = FavoritesData()
                favData.id = postData.id
                favData.body = postData.body
                favData.title = postData.title
                favData.userId = postData.userId
                launch {
                    withContext(Dispatchers.IO) {
                        detailViewModel.insertToFavourites(favData)
                        callingIntent()
                    }
                }
            }

        })
    }

    private fun callingIntent() {
        Intent(this@DetailActivity, MainActivity::class.java).also {
            it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(it)
            finish()
        }
    }

    private fun setupRecyclerView() {
        detailsAdapter =
            DetailsAdapter()
        binding.recyclerView.apply {
            isNestedScrollingEnabled = false
            setHasFixedSize(false)
            adapter = detailsAdapter
            layoutManager = LinearLayoutManager(this@DetailActivity)

        }
    }

}