package indigo

class Deck {
    enum class Rank(val shortName: String) {
        Ace("A"),
        Two("2"),
        Three("3"),
        Four("4"),
        Five("5"),
        Six("6"),
        Seven("7"),
        Eight("8"),
        Nine("9"),
        Ten("10"),
        Jack("J"),
        Queen("Q"),
        King("K")
    }

    enum class Suit(val symbol: String) {
        Diamond("♦"),
        Heart("♥"),
        Spade("♠"),
        Club("♣")
    }

    class Card(val suit: Suit, val rank: Rank) {
        override fun toString(): String {
            return rank.shortName + suit.symbol
        }
    }

    private var cards: MutableList<Card>

    init {
        val cs = mutableListOf<Card>()
        Suit.values().forEach { suit ->
            Rank.values().forEach { rank ->
                cs += Card(suit, rank)
            }
        }
        cs.shuffle()
        cards = cs
    }

    fun availableCard(): Int {
        return cards.size
    }

    fun take(num: Int): List<Card> {
        val res = mutableListOf<Card>()
        for (i in cards.size - 1 downTo cards.size - num) {
            res += cards.removeAt(i)
        }
        return res
    }
}
