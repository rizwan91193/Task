package com.justclean.mytask.ui.main.favorites

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.justclean.mytask.R
import com.justclean.mytask.databinding.FragmentFavoritesBinding
import com.justclean.mytask.ui.main.post.PostFragment
import com.justclean.mytask.ui.main.post.PostViewModel
import com.justclean.mytask.ui.main.post.PostlistAdapter
import com.justclean.mytask.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
@AndroidEntryPoint
class FavoritesFragment :Fragment(),CoroutineScope{
    private lateinit var binding:FragmentFavoritesBinding
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var networkConnection: NetworkConnection

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job= Job()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites,container,false)
        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        return binding.root
    }
    private fun setupRecyclerView(){
        favoritesAdapter =
            FavoritesAdapter()
        binding.recyclerView.apply {
            isNestedScrollingEnabled = false
            setHasFixedSize(false)
            adapter = favoritesAdapter
            layoutManager = LinearLayoutManager(activity)

        }
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
        networkConnection = NetworkConnection(activity!!)
        networkConnection.observe(activity!!, Observer { isConnected->
            if(!isConnected){
                binding.contentLayout.snackbar("No Internet Connection")
                binding.noData.visibility = View.VISIBLE
            }else{

//                binding.contentLayout.snackbar("Internet is connected!!")
                viewModel.getFavDataList().observe(activity!!, Observer {
                    if(!it.isEmpty()){
                        favoritesAdapter.differ.submitList(it)
                        binding.progressBar.visibility = View.GONE
                        binding.contentLayout.visibility = View.VISIBLE
                        binding.noData.visibility = View.GONE
                    }else{
                        binding.progressBar.visibility = View.GONE
                        binding.contentLayout.visibility = View.VISIBLE
                        binding.noData.visibility = View.VISIBLE
                    }
                })
            }



        })




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
        fun newInstance(): FavoritesFragment {
            return FavoritesFragment()
        }
    }
}