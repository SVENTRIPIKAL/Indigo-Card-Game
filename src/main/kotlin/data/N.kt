package data

enum class N(val int: Int) {
    ZERO(0),
    ONE(1),
    FOUR(4),
    SIX(6),
    FIFTY_TWO(52)
    ;
    override fun toString(): String = "$int"
}