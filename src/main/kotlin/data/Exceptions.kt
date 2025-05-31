package data

/**
 *  thrown when user input does
 *  not match provided options
 */
class WrongActionException(override val message: String): Exception()

/**
 *  thrown when the user chooses to
 *  exit the main menu / game loop
 */
class ExitGameException(override val message: String): Exception()

/**
 *  thrown when the user inputs
 *  anything other than a number
 *  within the range of 1-52
 */
class InvalidNumberOfCardsException(override val message: String): Exception()

/**
 *  thrown when the user requests
 *  a number of cards that exceeds
 *  the current game deck size
 */
class InsufficientCardsException(override val message: String): Exception()