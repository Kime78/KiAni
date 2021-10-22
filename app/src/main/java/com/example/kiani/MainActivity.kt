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
import kotlin.math.max

class Anime {
    var title: String = ""
    var imgURL: String = ""
    var animeID: String = ""
    var genres: MutableList<String> = mutableListOf<String>()
    var status: String = ""
    var type: String = ""
    var releaseDate: Int = 0
    var numOfEpisodes: Int = 0
}
fun parseAnime(animeID: String) : Anime {
    val result = Anime()
    val link = "https://gogoanime.pe/category$animeID"
    val doc = Jsoup.connect(link).get()
    val data = doc.getElementsByClass("type")

    //string processing
    val subtype = data[0].children()[1].attributes()["href"].split('/').last() // "/sub-category/winter-2019-anime"
    val desc = data[1].textNodes()[0].text()
    val genre = data[2].children()
    val test = mutableListOf<String>()
    for(i in 1 until genre.size){
        test.add(genre[i].attributes()["href"].split('/').last())
    }
    return Anime()

}
fun getNewSeason(): MutableList<Anime> {

    val anime = mutableListOf<Anime>()

    for(page in 1..3) {
        val doc = Jsoup.connect("https://gogoanime.pe/new-season.html?page=$page").get()
        val mainPage : Elements = doc.getElementsByClass("items")
        val animePage = mainPage[0].children()

        for(a : Element in animePage) {
            val entity = Anime()
            val tit = a.children()[0].children()[0].children()[0].attributes()["alt"].toString()
            val url = a.children()[0].children()[0].children()[0].attributes()["src"].toString()
            val id = a.children()[0].children()[0].attributes()["href"].toString()
            entity.title = tit
            entity.imgURL = url
            entity.animeID = id
            anime.add(entity)
        }
    }

    return anime
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

            val test = parseAnime("/kaguya-sama-wa-kokurasetai-tensai-tachi-no-renai-zunousen")
            print(0)
        }
   }
}