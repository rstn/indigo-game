package indigo

import indigo.Deck.Card
import indigo.Deck.Rank

fun String?.isPositiveNumber(): Boolean {
    if (this.isNullOrBlank()) return false
    return this.trim().all { it.isDigit() }
}

class Game {
    private val humanPlayer = HumanPlayer("Player")
    private val AIPlayer = AIPlayer("Computer")
    private val gameTable = GameTable(Deck())

    private val scoreCalculator = ScoreCalculator(humanPlayer, AIPlayer)

    companion object Command {
        const val exit_name = "exit"
    }

    fun start() {
        println("Indigo Card Game")
        var curPlayer: Player = choiceFirstPlayer()
        val firstPlayer = curPlayer
        var lastWonPlayer: Player? = null

        gameTable.makeFirstDealing()
        gameTable.printInitialCards()
        var move: Pair<String, Card?>? = null

        do {
            gameTable.printTableCards()
            if (humanPlayer.availableCard() == 0 && AIPlayer.availableCard() == 0) {
                if (gameTable.getAmountCardsInDeck() == 0) {
                    if (lastWonPlayer != null) {
                        gameTable.moveTableCardsToPlayer(lastWonPlayer, null)
                    } else {
                        gameTable.moveTableCardsToPlayer(firstPlayer, null)
                    }
                    break
                } else {
                    gameTable.makePlayerDealing(humanPlayer)
                    gameTable.makePlayerDealing(AIPlayer)
                }
            }

            if (curPlayer.availableCard() > 0) {
                move = curPlayer.makeMove(gameTable.getCards())
                if (move.second != null) {
                    if (gameTable.isWinCard(move.second!!)) {
                        gameTable.moveTableCardsToPlayer(curPlayer, move.second!!)
                        lastWonPlayer = curPlayer
                        println("${curPlayer.getName()} wins cards")
                        scoreCalculator.calculate()
                        scoreCalculator.printScores()
                    } else {
                        gameTable.giveCard(move.second!!)
                    }
                }
            }
            curPlayer = if (curPlayer == humanPlayer) AIPlayer else humanPlayer
        } while (exit_name != move?.first)

        if (exit_name != move?.first) {
            scoreCalculator.calculate(firstPlayer)
            scoreCalculator.printScores()
        }
        println("Game Over")
    }

    private fun choiceFirstPlayer(): Player {
        var curPlayer: Player?
        do {
            println("Play first?")
            curPlayer = when (readLine()!!.trim().lowercase()) {
                "yes" -> humanPlayer
                "no" -> AIPlayer
                else -> null
            }
        } while (curPlayer == null)
        return curPlayer
    }
}

class GameTable(private val deck: Deck) {
    private val cards = mutableListOf<Card>()

    fun getCards(): List<Card> {
        return cards
    }

    fun moveTableCardsToPlayer(player: Player, card: Card?) {
        if (card != null) {
            cards += card
        }
        player.addWonCards(cards.toList())
        cards.clear()
    }

    fun getAmountCardsInDeck(): Int {
        return deck.availableCard()
    }

    fun giveCard(card: Card) {
        cards += card
    }

    fun makeFirstDealing() {
        cards.addAll(deck.take(4))
    }

    fun printInitialCards() {
        println("Initial cards on the table: ${cards.joinToString(" ")}\n")
    }

    fun printTableCards() {
        if (cards.isEmpty()) {
            println("No cards on the table")
        } else {
            println("${cards.size} cards on the table, and the top card is ${cards.last()}")
        }
    }

    fun makePlayerDealing(player: Player) {
        player.giveCard(deck.take(6))
    }

    fun isWinCard(card: Card): Boolean {
        if (cards.isEmpty()) return false
        val lastCard = cards.last()
        return lastCard.rank == card.rank || lastCard.suit == card.suit
    }
}

class ScoreCalculator(private val p1: Player, private val p2: Player) {
    private val winRank = listOf(Rank.Ace, Rank.Ten, Rank.Jack, Rank.Queen, Rank.King)

    fun calculate(firstPlayer: Player) {
        calculate()
        val wonPlayer = when {
            p1.getWonCards().size == p2.getWonCards().size -> firstPlayer
            p1.getWonCards().size > p2.getWonCards().size -> p1
            else -> p2
        }
        wonPlayer.addScore(3)
    }

    fun calculate() {
        p1.calculateScores()
        p2.calculateScores()
    }

    private fun Player.calculateScores() {
        this.setScore(this.getWonCards().count { it.rank in winRank })
    }

    fun printScores() {
        println("Score: ${p1.getName()} ${p1.getScore()} - ${p2.getName()} ${p2.getScore()}")
        println("Cards: ${p1.getName()} ${p1.getWonCards().size} - ${p2.getName()} ${p2.getWonCards().size}\n")
    }

}