package data

/**
 *  enum class housing
 *  Integer constants
 *  @param int integer value
 *  @property N.ZERO
 *  @property N.ONE
 *  @property N.THREE
 *  @property N.FOUR
 *  @property N.SIX
 *  @property N.TWENTY_THREE
 */
enum class N(val int: Int) {
    ZERO(0),
    ONE(1),
    THREE(3),
    FOUR(4),
    SIX(6),
    TWENTY_THREE(23)
    ;
    override fun toString(): String = "$int"
}