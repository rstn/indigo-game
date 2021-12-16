package indigo

import indigo.Deck.*
import kotlin.random.Random

interface Player {

    fun makeMove(cardsOnTable: List<Card>): Pair<String, Card?>

    fun giveCard(cards: List<Card>)

    fun printCards()

    fun availableCard(): Int

    fun addWonCards(cards: List<Card>)

    fun getWonCards(): List<Card>

    fun addScore(points: Int)

    fun setScore(points: Int)

    fun getScore(): Int

    fun getName(): String
}

abstract class AbstractPlayer(private val name: String) : Player {

    protected val cards = mutableListOf<Card>()
    private val wonCards = mutableListOf<Card>()
    private var points: Int = 0

    override fun giveCard(cards: List<Card>) {
        this.cards.addAll(cards)
    }

    override fun printCards() {
        val sb = StringBuilder("Cards in hand: ")
        for (i in 0 until cards.size) {
            sb.append("${i + 1})${cards[i]} ")
        }
        println(sb.toString())
    }

    override fun availableCard(): Int {
        return cards.size
    }

    override fun addWonCards(cards: List<Card>) {
        wonCards.addAll(cards)
    }

    override fun getWonCards(): List<Card> {
        return wonCards
    }

    override fun addScore(points: Int) {
        this.points += points
    }

    override fun setScore(points: Int) {
        this.points = points
    }

    override fun getScore(): Int {
        return points
    }

    override fun getName(): String {
        return name
    }
}

class HumanPlayer(name: String) : AbstractPlayer(name) {
    override fun makeMove(cardsOnTable: List<Card>): Pair<String, Card?> {
        printCards()
        var move: String
        var card: Card? = null
        do {
            println("Choose a card to play (1-${cards.size}):")
            move = (readLine() ?: "").trim()
            if (move.isPositiveNumber()) {
                val cardNum = move.toInt() - 1
                if (cardNum in 0 until cards.size) {
                    card = cards.removeAt(cardNum)
                }
            }
        } while (card == null && move != Game.exit_name)
        return Pair(move, card)
    }
}

class AIPlayer(name: String) : AbstractPlayer(name) {
    override fun makeMove(cardsOnTable: List<Card>): Pair<String, Card?> {
        printCards()
        val card = choiceCard(cardsOnTable)
        cards.remove(card)
        println("${this.getName()} plays $card")
        return Pair("", card)
    }

    private fun choiceCard(cardsOnTable: List<Card>): Card {
        if (cards.size == 1) {
            //there is only one card in hand, put it on the table
            return cards.last()
        }

        val candidates = findWinCandidates(cardsOnTable)
        return if (candidates.isEmpty()) {
            cards.last()
        } else {
            val indexCard = Random.nextInt(0, candidates.size)
            candidates[indexCard]
        }
    }

    private fun findWinCandidates(cardsOnTable: List<Card>): List<Card> {
        val lastCard = if (cardsOnTable.isEmpty()) null else cardsOnTable.last()
        val candidates = mutableListOf<Card>()
        if (lastCard != null) {
            candidates.findSuitableWinCards(lastCard)
        } else {
            //there are no cards on the table
            candidates.findSuitableWinCards()
        }
        return candidates
    }

    private fun MutableList<Card>.findSuitableWinCards(lastCard: Card) {
        val cardSuits = cards.filter { it.suit == lastCard.suit }
        if (cardSuits.size > 1) {
            this.addAll(cardSuits)
            return
        }

        val cardRanks = cards.filter { it.rank == lastCard.rank }
        this.addAll(cardRanks)
        if(cardRanks.size > 1) {
            this.addAll(cardSuits)
            return
        }

        //maybe there is one card with the same suit or on card with the same rank
        this.addAll(cardSuits)
        this.addAll(cardSuits)
        if (this.isNotEmpty()) return

        this.findSuitableWinCards()
    }

    private fun MutableList<Card>.findSuitableWinCards() {
        val cardsSuit = mutableMapOf<Suit, MutableList<Card>>()
        val cardsRank = mutableMapOf<Rank, MutableList<Card>>()
        cards.forEach { card ->
            if (cardsSuit.containsKey(card.suit)) {
                cardsSuit[card.suit]!! += card
            } else {
                cardsSuit[card.suit] = mutableListOf(card)
            }

            if (cardsRank.containsKey(card.rank)) {
                cardsRank[card.rank]!! += card
            } else {
                cardsRank[card.rank] = mutableListOf(card)
            }
        }

        val manyCardsSuit = cardsSuit.filter { entry -> entry.value.size > 1 }
        val manyCardsRand = cardsRank.filter { entry -> entry.value.size > 1 }
        if (manyCardsSuit.isNotEmpty()) {
            //there are cards in hand with the same suit, throw one of them at random
            this.addAll(manyCardsSuit.values.flatten().toSet())
        } else if (manyCardsRand.isNotEmpty()) {
            //there are no cards in hand with the same suit, but there are cards with the same rank
            this.addAll(manyCardsRand.values.flatten().toSet())
        } else {
            //any of card in hand
            this.addAll(cards)
        }
    }

    override fun printCards() {
        val sb = StringBuilder()
        for (i in 0 until cards.size) {
            sb.append("${cards[i]} ")
        }
        println(sb.toString())
    }
}
