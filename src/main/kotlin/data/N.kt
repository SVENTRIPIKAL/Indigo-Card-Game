package data

enum class N(val int: Int) {
    ZERO(0),
    ONE(1),
    FIFTY_TWO(52)
    ;
    override fun toString(): String = "$int"
}