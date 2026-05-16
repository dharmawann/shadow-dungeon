# Shadow Dungeon

## Project Overview
This repository contains the source code for **Shadow Dungeon**, a 2D arcade-style platformer game developed for **SWEN20003 – Object-Oriented Software Development** at the University of Melbourne. The game is built in **Java** using the **Bagel** game engine.

Players explore multiple rooms, defeat enemies, collect coins, unlock doors, and purchase upgrades through an in-game store system. The game emphasizes object-oriented design principles, collision handling, inheritance, and modular architecture.

---

## Features

- **Multiple Rooms**: Prep Room, Battle Rooms (A & B), and End Room with win/lose states.
- **Character Selection**: Choose between Robot and Marine in the Prep Room.
- **Enemy Variants**: Bullet Kin, Ashen Bullet Kin, and Key Bullet Kin.
- **Combat System**: Mouse-based shooting with projectile collision detection.
- **Locked Doors & Keys**: Doors unlock when enemies are cleared or keys are collected.
- **Treasure Boxes**: Can be opened for rewards.
- **Store System**: 
  - Press **Spacebar** to open/close the store.
  - Purchase health using **E**.
  - Upgrade weapons using **L**.
  - Restart the game using **P** or **Enter**.
- **Player Abilities**:
  - Robot earns bonus coins per kill.
  - Marine is immune to river damage.
- **Configuration Files**:
  - `app.properties` defines entity positions and stats.
  - `message.properties` defines in-game messages.

---

## Technology Stack

- **Java 17+** – Core programming language  
- **Bagel Game Engine** – Rendering, input handling, and window management  
- **Maven 3.8+** – Build automation and dependency management  
- **Properties Files** – External configuration system  

---

## Repository Structure

- `src/` → Java source files including `ShadowDungeon.java` (main entry point), `Player.java`, `Enemy.java`, `Door.java`, and related classes  
- `res/` → Game assets (sprites, fonts, configuration files)  
- `app.properties` → Game entity configuration  
- `message.properties` → In-game UI messages  
- `pom.xml` → Maven build configuration  
- `target/` → Auto-generated compiled output  

---

## Controls

| Action | Key |
|--------|------|
| Move Up | W |
| Move Left | A |
| Move Down | S |
| Move Right | D |
| Shoot | Mouse Click |
| Select Robot | R |
| Select Marine | M |
| Open Treasure Box | K |
| Open / Close Store | Spacebar |
| Purchase Health | E |
| Upgrade Weapon | L |
| Restart Game | P / Enter |

---

## Deployment and Local Development

The game is designed to run locally using Maven.

To set up and run the project:

1. Clone the repository:

```bash
git clone https://github.com/dharmawann/shadow-dungeon.git
```

2. Open the project folder
3. Wait for Maven to index and download dependencies
4. Run ShadowDungeon.java
5. Game window will launch automaticallt

Requirements:
- Java 17+
- Maven 3.8+
- Bagel game engine (included automatically via Maven pom.xml)






