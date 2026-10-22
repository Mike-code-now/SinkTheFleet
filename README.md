Sink The Fleet

A two-player Battleship game for one computer.
Both players place five ships on their own 10x10 grid, then take turns firing at
each other's grid. The first player to sink the whole enemy fleet wins, and the
win is saved to a leaderboard.

2IP90 Programming, CBL project, TU/e 2026-2027
Authors: Mike de Brouwer Gonzalvo (2422026), Jorge Sistac Plaza (2478781)

---
Requirements:

- Java

---

How to run

1. Open NetBeans and choose File → Open Project.
2. Select the "Battleships" folder (the folder that contains "pom.xml").
3. Press F6 or the green Run button in Netbeans. The game opens on the start screen.

---

How to play

1. Start a game
Click Start Game and enter a name for each player. Names must be different,
cannot be empty and cannot contain a comma.

2. Place your ships
Each player places five ships of lengths 5, 4, 3, 3 and 2 on the Your Fleet
grid.

- Click a cell to choose where the ship starts. The ship is previewed in
  yellow.
- Click Yes to place it (it turns grey) or No if you don't like the placement.
- Click Rotate to switch between placing ships to the right and placing
  them downwards.
- Ships cannot overlap or stick out of the grid.

When all five ships are placed, click Switch Player. A START TURN screen
hides the board so the other player cannot see your fleet. Hand over the
computer and let the next player click it.

3. Battle
Fire by clicking a cell in Enemy Waters.

| Result     | What you see                                     |
|------------|--------------------------------------------------|
| Miss       | Blue cell with an X                              |
| Hit        | Red cell with an X                               |
| Hit & sunk | Red cell with an X, and the message "HIT & SUNK" |

- After a hit, you may fire again.
- After a miss, your turn is over. Click Switch Player and hand over.
- Shots fired at you are shown with an X on your own fleet.

4. Winning
The first player to sink all five enemy ships wins. The game announces the
winner, adds the win to the leaderboard and returns to the start screen.

Leaderboard
Click Show Leaderboard on the start screen to see every player's number of
wins, highest first. Wins are saved in `leaderboard.txt` in the project folder.
