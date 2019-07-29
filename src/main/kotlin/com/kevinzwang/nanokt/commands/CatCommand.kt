package com.kevinzwang.nanokt.commands

import com.google.gson.Gson
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.core.Permission
import java.lang.Exception
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.time.Duration

class CatCommand: Command() {
    private var client: HttpClient = HttpClient.newBuilder()
        .followRedirects(HttpClient.Redirect.NORMAL)
        .build()
    private var randomCatRequest: HttpRequest = HttpRequest.newBuilder()
        .uri(URI.create("http://aws.random.cat/meow"))
        .timeout(Duration.ofSeconds(3))
        .build()
    private var catApiRequest: HttpRequest = HttpRequest.newBuilder()
        .uri(URI.create("http://thecatapi.com/api/images/get"))
        .timeout(Duration.ofSeconds(3))
        .build()

    init {
        this.name = "cat"
        this.help = "shows a cute cat pic"
        this.guildOnly = false
    }

    data class RandomCatResponse(val file: String)

    override fun execute(event: CommandEvent) {
        try {
            val response = client.send(randomCatRequest, BodyHandlers.ofString())
            val content = Gson().fromJson(response.body(), RandomCatResponse::class.java)
            event.reply(content.file)
        } catch (e: Exception) {
            try {
                val response = client.send(catApiRequest, BodyHandlers.ofString())
                val uri = response.uri().toString()
                event.reply(uri)
            } catch (e: Exception) {
                event.reply("I searched far and wide for a cat picture, but I couldn't find one! :scream: \nPlease try again later.")
            }
        }
    }

}