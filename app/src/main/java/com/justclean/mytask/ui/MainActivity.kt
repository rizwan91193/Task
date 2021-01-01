package com.justclean.mytask.ui

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.justclean.mytask.R
import com.justclean.mytask.databinding.ActivityMainBinding
import com.justclean.mytask.ui.main.SectionsPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
//    private lateinit var networkConnection:NetworkConnection
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)

        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


        /*networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected->
            if(!isConnected){
                binding.rootLayout.snackbar("No Internet Connection")
            }else{
                binding.rootLayout.snackbar("Internet is connected!!")
            }



        })*/

    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onBackPressed() {
        super.onBackPressed()
//            val fragment = this.supportFragmentManager.primaryNavigationFragment
           /* val fragment = supportFragmentManager.fragments.get(0).childFragmentManager.fragments.get(0)
            if(fragment is DetailFragment){
                val transaction = this.supportFragmentManager.beginTransaction()
                val fragment1 = PostFragment()
                transaction.replace(R.id.constraintLayout,fragment1)
                transaction.commit()
            }*/
            /*if(supportFragmentManager.backStackEntryCount>1){
                val fragment = PlaceholderFragment.newInstance(0)
            }else{
                super.onBackPressed()
            }*/



           /* val transaction = supportFragmentManager.beginTransaction()
            val fragment1 = PostFragment()
            transaction.replace(R.id.constraintLayout,fragment1)
            transaction.commit()*/
    }
}