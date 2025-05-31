package data

enum class S(val string: String) {
    BLANK_SPACE(" ")
    ;
    override fun toString() = string
}