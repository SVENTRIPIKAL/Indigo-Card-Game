package data

/**
 *  enum class housing
 *  Rank constants
 *  @param icon rank string value
 *  @property Rank.TWO
 *  @property Rank.THREE
 *  @property Rank.FOUR
 *  @property Rank.FIVE
 *  @property Rank.SIX
 *  @property Rank.SEVEN
 *  @property Rank.EIGHT
 *  @property Rank.NINE
 *  @property Rank.TEN
 *  @property Rank.JACK
 *  @property Rank.QUEEN
 *  @property Rank.KING
 *  @property Rank.ACE
 */
enum class Rank(val icon: String) {
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("J"),
    QUEEN("Q"),
    KING("K"),
    ACE("A")
    ;
    override fun toString(): String = icon
}