# indigo-game
Indigo game is cards game for 2 player (one of them is computer)

We use a standard virtual 52-card deck. 

The main game concept is easy. Players take turns in tossing cards onto the table. If a player throws a card with the same suit or rank as the topmost card on the table, that player wins all the cards on the table.

The deck card ranks are A, 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, and K. Deck card suits include ♦, ♥, ♠, ♣. A card is represented by a rank and a suit. The whole deck includes K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣, K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦, K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥, K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠.

###Game rules
The game rules are easy:
1. Four cards are placed face-up on the table;
2. Six cards are dealt to each player;
3. The players take turns in playing cards. If the card has the same suit or rank as the topmost card, then the player wins all the cards on the table;
4. When both players have no cards in hand, go to step 2 unless there are no more remaining cards in the card deck.
5. The remaining cards on the table go to the player who won the cards last. In the rare case where none of the players win any cards, then all cards go to the player who played first.

When a card with the same suit or rank as the top card on the table is played, the one who tossed it wins all of the cards on the table. The program in this situation should save the cards for the winner before clearing the table of all cards. The program should be keeping track of all cards won by the two players.

The program should also count the points of each player, so develop a point system. The cards with the ranks A, 10, J, Q, K get 1 point each, while the player who has the most cards gets three points. If the players have the same number of cards, then the player who played first gets these points. The total points in this game are 23. The player with the most points wins the game.

###Computer play strategy
From now on, we will introduce the term the candidate cards. They are the cards in hand that can win the cards on the table.

The strategy is as follows:
1. If there is only one card in hand, put it on the table (Example 2);
2. If there is only one candidate card, put it on the table (Example 3);
3. If there are no cards on the table: 
   1. If there are cards in hand with the same suit, throw one of them at random (Example 4). For example, if the cards in hand are 7♥ 9♥ 8♣ A♠ 3♦ 7♦ Q♥ (multiple ♥, and ♦ suits), the computer will play one card at random. 
   2. If there are no cards in hand with the same suit, but there are cards with the same rank (this situation occurs only when there are 4 or fewer cards in hand), then throw one of them at random (Example 5). For example, if the cards in hand are 7♦ 7♥ 4♠ K♣, throw one of 7♦ 7♥ at random. 
   3. If there are no cards in hand with the same suit or rank, throw any card at random. For example, if the cards in hand are 9♥ 8♣ A♠ 3♦, throw any of them at random.
4. If there are cards on the table but no candidate cards, use the same tactics as in step 3. That is:
   1. If there are cards in hand with the same suit, throw one of them at random (Example 6). For example, if the top card on the table is A♦, and the cards in hand are 6♣ Q♥ 8♣ J♠ 7♣ (multiple ♣ suit), the computer will place any of 6♣ 8♣ 7♣ at random. 
   2. If there are no cards in hand with the same suit, but there are cards with the same rank (this may occur when there are 3 or fewer cards in hand), throw one of them at random (Example 7). For example, if the top card on the table is A♦ and the cards in hand are J♠ Q♥ J♣, put one of J♠ J♣ at random. 
   3. If there are no cards in hand with the same suit or rank, then put any card at random. For example, if the top card on the table is A♦, and the cards in hand are J♠ Q♥ K♣, throw any of them at random.
5. If there are two or more candidate cards:
   1. If there are 2 or more candidate cards with the same suit as the top card on the table, throw one of them at random (Example 8). For example, if the top card on the table is 5♥, and the cards in hand are 6♥ 8♣ 5♠ 7♦ 7♥, then the candidate cards are 6♥ 7♥ 5♠. There are 2 candidate cards with the same suit as the top card on the table, 6♥ 7♥. Place any at random. 
   2. If the above isn't applicable, but there are 2 or more candidate cards with the same rank as the top card on the table, throw one of them at random (example 9). For example, if the top card on the table is J♥, and the cards in hand are 3♥ J♣ J♠ 6♦, then the candidate cards are 3♥ J♣ J♠. In this case, there are no 2 or more candidate cards with the same suit, but there are 2 candidate cards with the same rank as the top card on the table that are J♣ J♠. Put any at random. 
   3. If nothing of the above is applicable, then throw any of the candidate cards at random.

The strategy above can be improved. Moreover, the computer can play even better if it keeps track of the cards that have been played. However, our goal isn't a complex algorithm for the computer, but to learn how to apply the strategy.

###Examples
The greater-than symbol followed by a space (> ) represents the user input. Note that it's not part of the input.
```
Indigo Card Game
Play first?
> yes
Initial cards on the table: 7♣ 2♣ 9♣ J♦

4 cards on the table, and the top card is J♦
Cards in hand: 1)7♥ 2)10♥ 3)10♣ 4)A♣ 5)2♦ 6)10♠ 
Choose a card to play (1-6):
> 5
Player wins cards
Score: Player 1 - Computer 0
Cards: Player 5 - Computer 0

No cards on the table
6♥ 5♦ 4♦ Q♠ J♠ A♥ 
Computer plays 4♦
1 cards on the table, and the top card is 4♦
Cards in hand: 1)7♥ 2)10♥ 3)10♣ 4)A♣ 5)10♠ 
Choose a card to play (1-5):
> 1
2 cards on the table, and the top card is 7♥
6♥ 5♦ Q♠ J♠ A♥ 
Computer plays 6♥
Computer wins cards
Score: Player 1 - Computer 0
Cards: Player 5 - Computer 3

No cards on the table
Cards in hand: 1)10♥ 2)10♣ 3)A♣ 4)10♠ 
Choose a card to play (1-4):
> 1
1 cards on the table, and the top card is 10♥
5♦ Q♠ J♠ A♥ 
Computer plays A♥
Computer wins cards
Score: Player 1 - Computer 2
Cards: Player 5 - Computer 5

...

No cards on the table
7♦ 9♥ 9♠ A♠ 
Computer plays A♠
1 cards on the table, and the top card is A♠
Cards in hand: 1)6♠ 2)K♣ 3)4♣ 
Choose a card to play (1-3):
> 1
Player wins cards
Score: Player 16 - Computer 3
Cards: Player 39 - Computer 8

No cards on the table
7♦ 9♥ 9♠ 
Computer plays 9♠
1 cards on the table, and the top card is 9♠
Cards in hand: 1)K♣ 2)4♣ 
Choose a card to play (1-2):
> 1
2 cards on the table, and the top card is K♣
7♦ 9♥ 
Computer plays 9♥
3 cards on the table, and the top card is 9♥
Cards in hand: 1)4♣ 
Choose a card to play (1-1):
> 1
4 cards on the table, and the top card is 4♣
7♦ 
Computer plays 7♦
5 cards on the table, and the top card is 7♦
Score: Player 20 - Computer 3
Cards: Player 44 - Computer 8

Game Over
Indigo Card Game

```