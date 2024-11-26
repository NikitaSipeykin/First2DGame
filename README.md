# 2D Game Overview

## Current State of the Game
The game is a basic 2D framework featuring background rendering, character animation, and user input handling within a game loop. The **basic version of the game has been completed**.

# Game Development Project

## Key Features

### Background and Map
- Background sprites and `map01.txt` for map layout created.
- World map with a camera following the player for a dynamic view.
- Incremental map loading for resource efficiency.

### Character Animation and Movement
- `Player.java` includes methods for setting default values, updating, and drawing characters.
- Character sprites drawn via `getPlayerImage()`.
- **Entity Class** has core variables for character functionality.
- Animation supported through updated `update()` and `paintComponent()` in `GamePanel.java`.

### Collision Detection and Game Loop
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
- Sound effects added for:
  - Picking up keys.
  - Interacting with doors.
  - Collecting boots.
- Background music added to enhance the game atmosphere.

### UI Elements
- **Timer**: Displays the time elapsed during gameplay.
- **Key Counter**: Shows how many keys the player has collected.
- **Interaction Messages**: Text prompts appear on-screen to guide the player when interacting with objects.
- **End Game Screen**: When the player finds the chest, a congratulatory message is displayed along with the time taken to complete the game.

---

## Summary
The game has a foundational structure with map rendering, character animation, a following camera, basic collision detection, and resource-efficient map loading. Recent updates include:
- Integration of sound effects for specific object interactions.
- Addition of background music.
- Introduction of a new object: **Boots**.
- Completion of the **basic version** with added UI elements:
  - Timer.
  - Key counter.
  - Interaction messages.
  - End game screen showing the player's completion time and a congratulatory message.
