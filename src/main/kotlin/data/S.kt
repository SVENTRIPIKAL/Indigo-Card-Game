package data

enum class S(val string: String) {
    ASTERISK("*"),
    BLANK_SPACE(" "),
    RESET_OPTION("reset"),
    SHUFFLE_OPTION("shuffle"),
    GET_OPTION("get"),
    EXIT_OPTION("exit"),
    MENU_MSG("Choose an action (reset, shuffle, get, exit):"),
    RESET_SHUFFLE_MSG("Card deck is *."),
    EXIT_MSG("Bye"),
    WRONG_ACTION_MSG("Wrong Action."),
    NUM_OF_CARDS_MSG("Number of cards:"),
    INVALID_NUM_OF_CARDS_MSG("Invalid number of cards."),
    INSUFFICIENT_CARDS_MSG("The remaining cards are insufficient to meet the request."),
    ;
    override fun toString() = string
}