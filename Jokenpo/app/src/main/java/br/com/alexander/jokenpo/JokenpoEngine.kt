package br.com.alexander.jokenpo

import kotlin.random.Random

enum class Result {
    WIN, DRAW, LOSS
}

class JokenpoEngine(private val availablePlays: Array<String>) {

    private var aiPlay: String = "Pedra"

    fun calculateResult(playerPlay: String): Result {
        val aiPlay = getAIPlay()

        return when {
            playerPlay == aiPlay -> Result.DRAW
            playerPlay == "Pedra" && aiPlay == "Tesoura" -> Result.WIN
            playerPlay == "Pedra" && aiPlay == "Papel" -> Result.LOSS
            playerPlay == "Papel" && aiPlay == "Pedra" -> Result.WIN
            playerPlay == "Papel" && aiPlay == "Tesoura" -> Result.LOSS
            playerPlay == "Tesoura" && aiPlay == "Papel" -> Result.WIN
            else -> Result.LOSS
        }
    }

    private fun getAIPlay(): String {
        val playIndex = Random.nextInt(0, 3)
        aiPlay = availablePlays[playIndex]
        return aiPlay
    }

    fun getAIPlayStr() = aiPlay
}