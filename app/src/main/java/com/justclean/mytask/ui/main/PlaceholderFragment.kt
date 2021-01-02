package com.justclean.mytask.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.justclean.mytask.R
import com.justclean.mytask.ui.main.favorites.FavoritesFragment
import com.justclean.mytask.ui.main.post.PostFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * A placeholder fragment containing a simple view.
 */
@AndroidEntryPoint
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        pageViewModel.text.observe(activity!!, Observer<String> {

            when (it) {
                "0" -> {
                    val fragmentManager: FragmentManager = childFragmentManager
                    val transaction = fragmentManager.beginTransaction()
                    val fragment = PostFragment()
                    transaction.add(R.id.constraintLayout, fragment)
                    transaction.addToBackStack(null)
                    transaction.commit()

                }
                "1" -> {
                    val transaction = childFragmentManager.beginTransaction()
                    val fragment = FavoritesFragment()
                    transaction.add(R.id.constraintLayout, fragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }

            }
        })
        return root
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
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}