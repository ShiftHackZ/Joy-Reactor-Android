package com.shifthackz.joyreactor.network.parser

import android.util.Log
import com.shifthackz.joyreactor.entity.Section
import com.shifthackz.joyreactor.network.extensions.baseUrl
import com.shifthackz.joyreactor.network.extensions.formatSectionUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class SectionsParser {

    suspend fun fetchSections(url: String): Result<List<Section>> = withContext(Dispatchers.IO) {
        try {
            val sections = mutableListOf<Section>()
            val doc = Jsoup.connect(url).get()
            val cssUrl = doc.head().select("link[href~=.*\\.(css)]").attr("href").let { "https:$it" }
            val css = Jsoup.connect(cssUrl).get().html()
//            Log.d("DBGCSS", "$cc")
            val sidebars = doc.select("div.sidebar_block")
            for (i in 0 until sidebars.size) {
                val title = sidebars[i].select("h2.sideheader").text()
                val lis = sidebars[i].select("li")
                for (j in 0 until lis.size) {
                    if (lis[j].attr("id").isNotBlank()) continue
                    val name = lis[j].attr("class")
                    val link = lis[j].select("a")[0].attr("href").formatSectionUrl()
//                    lis[j].select("a")[0].
                    val img = lis[j].select("a")[0].cssSelector()
                    val imgCssSel = img.split(" > ").takeLast(2).joinToString(" ")//.split(" ")[0]
//                    Log.d("DBGCSS", "$img")
//                    Log.d("DBGCSS", "$imgCssSel")
//                    Log.d("DBGCSS", "${css.html().contains(imgCssSel)}")
                    var idx = css.lastIndexOf(imgCssSel)
                    var symbol = css[idx]
                    var str = ""
                    while (symbol != ')') {
                        idx++
                        symbol = css[idx]
                        str += symbol
                    }
                    val one = cssUrl.baseUrl() + str.split("'")[1].replace("..", "")
                    Log.d("DBGCSS", "$one")
                    if (link.isNotBlank() && !link.startsWith("javascript")) {
                        sections.add(
                            Section(
                                name, link, one
                            )
                        )
                    }
                }
            }
            Result.success(sections)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
