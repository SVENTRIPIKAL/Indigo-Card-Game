package data

/**
 *  enum class housing
 *  String constants
 *  @param string text string value
 *  @property S.ASTERISK
 *  @property S.BLANK_SPACE
 *  @property S.EMPTY_STRING
 *  @property S.YES_OPTION
 *  @property S.NO_OPTION
 *  @property S.EXIT_OPTION
 *  @property S.TITLE_MSG
 *  @property S.GAME_OVER_MSG
 *  @property S.FIRST_PROMPT_MSG
 *  @property S.INITIAL_CARDS_MSG
 *  @property S.TABLE_CARDS_MSG
 *  @property S.TABLE_TOP_MSG
 *  @property S.IN_HAND_MSG
 *  @property S.CHOOSE_CARD_MSG
 *  @property S.COMPUTER_PLAY_MSG
 *  @property S.WINNER_MSG
 *  @property S.SCORE_MSG
 *  @property S.CARDS_MSG
 *  @property S.STATS_MSG
 */
enum class S(val string: String) {
    ASTERISK("*"),
    BLANK_SPACE(" "),
    EMPTY_STRING(""),
    YES_OPTION("yes"),
    NO_OPTION("no"),
    EXIT_OPTION("exit"),
    TITLE_MSG("Indigo Card Game"),
    GAME_OVER_MSG("Game Over"),
    FIRST_PROMPT_MSG("Play first?"),
    INITIAL_CARDS_MSG("Initial cards on the table: $ASTERISK"),
    TABLE_CARDS_MSG("$ASTERISK$ASTERISK cards on the table$ASTERISK"),
    TABLE_TOP_MSG(", and the top card is $ASTERISK"),
    IN_HAND_MSG("Cards in hand: $ASTERISK"),
    CHOOSE_CARD_MSG("Choose a card to play (1-$ASTERISK):"),
    COMPUTER_PLAY_MSG("Computer plays $ASTERISK\n"),
    WINNER_MSG("$ASTERISK wins cards"),
    SCORE_MSG("Score"),
    CARDS_MSG("Cards"),
    STATS_MSG("$ASTERISK$ASTERISK$ASTERISK: Player $ASTERISK$ASTERISK - Computer $ASTERISK"),
    ;
    override fun toString() = string
}