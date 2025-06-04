package data

/**
 *  enum class housing
 *  Integer constants
 *  @property N.ZERO
 *  @property N.ONE
 *  @property N.FOUR
 *  @property N.SIX
 */
enum class N(val int: Int) {
    ZERO(0),
    ONE(1),
    FOUR(4),
    SIX(6)
    ;
    override fun toString(): String = "$int"
}