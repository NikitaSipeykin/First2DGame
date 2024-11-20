# 2D Game Overview

## Current State of the Game
The game is a basic 2D framework featuring background rendering, character animation, and user input handling within a game loop.

### Key Features

#### Background and Map
- Background sprites and `map01.txt` for map layout created.
- World map with a camera following the player for a dynamic view.
- Incremental map loading for resource efficiency.
- **TileManager Class** integrated in `GamePanel.java` with `draw()`, `loadMap()`, and `getTileImage()` methods.
- **Tile Class** initialized with variables like `image` and `collision`.

#### Character Animation and Movement
- `Player.java` includes methods for setting default values, updating, and drawing characters.
- Character sprites drawn via `getPlayerImage()`.
- **Entity Class** has core variables for character functionality.
- Animation supported through updated `update()` and `paintComponent()` in `GamePanel.java`.

#### Collision Detection and Game Loop
- Collision logic for player interactions with tiles and objects added.
- Game loop in `GamePanel.java` handles panel size, updates, and movement.
- **Input Handling** in `KeyHandler.java` supports movement with `w`, `a`, `s`, and `d` keys.

### Objects and Interactions
- **SuperObject.java** parent class added with `draw()` method.
- Minor object variable additions in `GamePanel.java`.
- **AssertSetter.java** includes `setObject()` for placing objects.
- Added object classes:
  - `OBJ_Key.java`
  - `OBJ_Door.java`
  - `OBJ_Chest.java`
  - `OBJ_Boots.java`
- Object sprites integrated.

## Summary
The game has a foundational structure with map rendering, character animation, a following camera, basic collision detection, and resource-efficient map loading. Recent additions include interactive object classes and sprites without collision logic. Future improvements will focus on expanding gameplay mechanics, enhancing graphics, and refining interactions.