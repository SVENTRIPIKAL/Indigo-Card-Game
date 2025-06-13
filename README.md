# Indigo-Card-Game

A console program that enables the user to actively \
compete against the Computer in an interactive card \
game of **Indigo**.


### Rules:
With an initial **52 card deck**, there will be **4 cards on \
the table**, including **1 top card**, and **6 cards in each \
player's hand**. Each player will then begin taking turns \
placing one card on the table from their hand. If a player \
places a card that has a similar **Rank** or **Suit** as that of \
the **top card**, then the player wins all cards on the table. \
If not, then the player's turn is ended. **Note: won cards \
are set aside & not added to a player's hand.** Whenever \
a player's hand is empty, then another **6 cards are dealt** \
to that player from the **game deck**. The game ends once all \
cards from the deck & each player's hand have been played.

### Points:
Points are accrued whenever a player wins all cards \
on the table. **1 point** is given for each **A, 10, J, Q, K** \
that the player receives, including **the placed card** that \
was used to win the round, if it is also one of the \
previously mentioned ranks. Additionally, **3 points** are \
also given to the player with **the most cards** at the end \
of the game. If there are still **cards on the table** when \
the game ends, then **all the table cards** are given to \
the **last player who won** a round & their points are \
adjusted according to the ranks of the received cards. \
Total possible **game points is 23** across both players.

### Examples:
```
Indigo Card Game
Play first?
>> no

Initial cards on the table: 5♦ 9♣ A♠ A♥

4 cards on the table, and the top card is A♥
Computer plays 3♥

Computer wins cards
Score: Player 0 - Computer 2
Cards: Player 0 - Computer 5

No cards on the table
Cards in hand: 6♥ 6♦ 9♦ 7♠ Q♥ 2♦
Choose a card to play (1-6):
>> 6
1 cards on the table, and the top card is 2♦

.....   .....   .....   .....   .....   .....
.....   .....   .....   .....   .....   .....

1 cards on the table, and the top card is J♠
Cards in hand: 9♥
Choose a card to play (1-1):
>> 1
2 cards on the table, and the top card is 9♥
Score: Player 15 - Computer 8
Cards: Player 27 - Computer 25

Game Over
```

### Highlights
* OOP
* Inheritance
* While Loops
* User Prompt
* Inner Classes
* Polymorphism
* Enum Classes
* When Statements

### Inspiration
[Indigo Card Game](https://hyperskill.org/projects/214) (Kotlin) \
_Part of the [JetBrains Academy: Hyperskill - Kotlin Developer](https://hyperskill.org/courses/3-kotlin-developer) Course_