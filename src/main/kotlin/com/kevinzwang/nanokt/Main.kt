package com.kevinzwang.nanokt

import com.jagrosh.jdautilities.command.CommandClientBuilder
import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.jagrosh.jdautilities.examples.command.GuildlistCommand
import com.jagrosh.jdautilities.examples.command.PingCommand
import com.jagrosh.jdautilities.examples.command.ShutdownCommand
import com.kevinzwang.nanokt.commands.CatCommand
import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDABuilder

fun main(args: Array<String>) {
    val token = "MzMzMjk3NDQyODg5OTI0NjA4.XT4t5Q.jhxCYfQ4mgIY_pJbLj96htqXX90"
    val ownerId = "312418351844425731"

    val waiter = EventWaiter()
    val client = CommandClientBuilder()
        .setOwnerId(ownerId)
        .setPrefix("[")
        .addCommands(
            /* built-in commands */
            PingCommand(),
            ShutdownCommand(),
            GuildlistCommand(waiter),
            /* custom commands */
            CatCommand()
        )
        .build()


    val jda = JDABuilder(AccountType.BOT)
        .setToken(token)
        .addEventListener(waiter, client)
        .build()
    jda.awaitReady()
    println("Bot logged in as ${jda.selfUser.name}!")
}
