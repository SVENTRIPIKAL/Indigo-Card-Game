package data

/**
 *  enum class housing
 *  Suit constants
 *  @param icon suit string value
 *  @property Suit.DIAMOND
 *  @property Suit.HEART
 *  @property Suit.SPADE
 *  @property Suit.CLUB
 */
enum class Suit(val icon: String) {
    DIAMOND("♦"),
    HEART("♥"),
    SPADE("♠"),
    CLUB("♣")
    ;
    override fun toString(): String = icon
}