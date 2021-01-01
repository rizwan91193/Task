package com.justclean.mytask.ui.main.post

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.justclean.mytask.R
import com.justclean.mytask.data.network.NetworkConnectionInterceptor
import com.justclean.mytask.databinding.FragmentPostBinding
import com.justclean.mytask.ui.main.PlaceholderFragment
import com.justclean.mytask.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext
@AndroidEntryPoint
class PostFragment :Fragment(),CoroutineScope{

    private lateinit var binding:FragmentPostBinding
    private lateinit var viewModel: PostViewModel
    private lateinit var postlistAdapter: PostlistAdapter
    private lateinit var networkConnection:NetworkConnection
//    private val factory: PostViewModelFactory
    private lateinit var job: Job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job= Job()

    }
    override val coroutineContext: CoroutineContext
        get() = job
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post,container,false)
        viewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
        networkConnection = NetworkConnection(activity!!)
        networkConnection.observe(this, Observer { isConnected->
            if(!isConnected){
                binding.contentLayout.snackbar("No Internet Connection")
                viewModel.getPostDataList().observe(activity!!, Observer {
                if(!it.isEmpty()){
                    postlistAdapter.differ.submitList(it)
                    binding.progressBar.visibility = View.GONE
                    binding.contentLayout.visibility = View.VISIBLE
                }else{
                    binding.progressBar.visibility = View.GONE
                    binding.contentLayout.visibility = View.VISIBLE
                }
                })
            }else{
                binding.contentLayout.snackbar("Internet is connected!!")
            }



        })
        binding.progressBar.visibility = View.VISIBLE
            val constraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            val request = OneTimeWorkRequestBuilder<MyWorkerTask>().setConstraints(constraint).build()
            WorkManager.getInstance().enqueue(request)
            WorkManager.getInstance().getWorkInfoByIdLiveData(request.id).observe(activity!!, Observer {
                workInfo ->  if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                launch {
            withContext(Dispatchers.Main) {
                try {
                    val postRepose = viewModel.getPostList()
                    if (!postRepose.isEmpty()) {
                        postlistAdapter.differ.submitList(postRepose)
                        viewModel.insertPostDataList(postRepose)
                        binding.progressBar.visibility = View.GONE
                        binding.contentLayout.visibility = View.VISIBLE
                    } else {
                        binding.progressBar.visibility = View.GONE
                        binding.contentLayout.visibility = View.VISIBLE
                    }
                } catch (e: APIExceptions) {
                    binding.progressBar.visibility = View.GONE
                    binding.contentLayout.visibility = View.VISIBLE
                    e.printStackTrace()
                } catch (e: NoInternetException) {
                    binding.contentLayout.snackbar(e.message!!)
                    binding.progressBar.visibility = View.GONE
                    binding.contentLayout.visibility = View.VISIBLE
                    e.printStackTrace()
                }


            }
        }
            }else{
                binding.progressBar.visibility = View.GONE
                binding.contentLayout.visibility = View.VISIBLE
                /*viewModel.getPostDataList().observe(activity!!, Observer {
                if(!it.isEmpty()){
                    postlistAdapter.differ.submitList(it)
                    binding.progressBar.visibility = View.GONE
                    binding.contentLayout.visibility = View.VISIBLE
                }else{
                    binding.progressBar.visibility = View.GONE
                    binding.contentLayout.visibility = View.VISIBLE
                }
                })*/
            }
            })



    }
    private fun setupRecyclerView(){
        postlistAdapter =
            PostlistAdapter()
        binding.recyclerView.apply {
            isNestedScrollingEnabled = false
            setHasFixedSize(false)
            adapter = postlistAdapter
            layoutManager = LinearLayoutManager(activity)

        }
    }
    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(): PostFragment {
            return PostFragment()
        }
    }
    /*inner class MyWorkerTask (context: Context, workerParameters: WorkerParameters):
        Worker(context,workerParameters){
        override  fun doWork(): Result {

            return try {
                try {
                    makingNetworkCall()
                }catch (e: Exception){
                    Result.failure()
                }
            }catch (e: Exception){
                Result.failure()
            }
        }

        private  fun makingNetworkCall() :Result{
            *//*val postRepose = viewModel.getPostList()
            if (!postRepose.isEmpty()) {
                return Result.success()
            }else{
                return Result.failure()
            }*//*
       return Result.success()
        }


    }*/
}