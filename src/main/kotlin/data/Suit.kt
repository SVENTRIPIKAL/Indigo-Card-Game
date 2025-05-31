package data

enum class Suit(val icon: String) {
    DIAMOND("♦"),
    HEART("♥"),
    SPADE("♠"),
    CLUB("♣")
    ;
    override fun toString(): String = icon
}