2D Game Overview
Current State of the Game
The current version of the game is a basic 2D framework that includes essential elements for rendering a background, animating a character, and handling user input through a game loop.

Key Features Implemented:
Background and Map
Background Creation:
Background sprites have been added.
map01.txt has been created for defining the map layout.
TileManager Class:
Integrated into GamePanel.java.
Methods draw(), loadMap(), and getTileImage() implemented.
Tile Class:
Main variables such as image and collision initialized.
Character Animation
Character Animation and Movement:
Player.java includes methods setDefaultValues(), update(), and draw().
getPlayerImage() added for drawing character sprites.
Entity Class:
Core variables added for character functionality.
GamePanel.java:
update() and paintComponent() methods modified to support animation.
Game Loop
Game Loop Implementation:
GamePanel.java defines panel size, window size, frame updates, and movements.
Main.java sets up the game window using JFrame.
Input Handling:
KeyHandler.java includes keyPressed() and keyReleased() for player movement, supporting w, a, s, and d keys.
Summary
The current build provides a solid foundation with map rendering, character animation, and a basic game loop that includes user input handling. Future developments will include adding new gameplay mechanics, enhancing graphics, and improving overall interactions.
