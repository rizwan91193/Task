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
import com.justclean.mytask.util.BasicClickListener
import com.justclean.mytask.util.CommonUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

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


    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onBackPressed() {
        CommonUtils.alertDialog(this,"Do you want to exit?","Yes","No",object :BasicClickListener{
            override fun onYesClick(value: String?) {
                this@MainActivity.finish()
                System.exit(0)
            }

            override fun onNoClick() {

            }
        })
    }
}