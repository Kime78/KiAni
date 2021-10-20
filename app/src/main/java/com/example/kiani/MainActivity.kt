package com.example.kiani

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kiani.databinding.ActivityMainBinding
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import kotlin.concurrent.thread

class Anime {
    var title: String = ""
    var img_url: String = ""
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        thread {
            //search url
            //val search : String = "naruto";
            //https://gogoanime.pe//search.html?keyword=$search

            val anime = mutableListOf<Anime>();

            for(page in 1..3) {
                val doc = Jsoup.connect("https://gogoanime.pe/new-season.html?page=$page").get();
                val main_page : Elements = doc.getElementsByClass("items");
                val anime_page = main_page[0].children();

                for(a : Element in anime_page) {
                    val entity = Anime();
                    var tit = a.children()[0].children()[0].children()[0].attributes()["alt"].toString()
                    val url = a.children()[0].children()[0].children()[0].attributes()["src"].toString()
                    entity.title = "$tit"
                    entity.img_url = "$url"
                    anime.add(entity)
                }
            }

            print(0);
        }
   }
}