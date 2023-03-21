# Run games

/src/main/java/APP.java
line 175 is main function.
You can simply press run button (you must have java extension to run the game)

### static entities
<table>
    <tr>
        <th>Entitiy</th>
        <th>Image</th>
        <th>Descripton</th>
    </tr>
    <tr>
        <th>Wall</th>
        <th><img src = "entitiesImg/Wall.png"></th>
        <th>Blocks the movement of the Player, enemies and boulders</th>
    </tr>
        <tr>
        <th>Exit</th>
        <th><img src = "entitiesImg/Exit.png"></th>
        <th>If the Player goes through it, the puzzle may be complete.</th>
    </tr>
        <tr>
        <th>Boulder</th>
        <th><img src = "entitiesImg/Boulder.png"></th>
        <th>Acts like a wall in most cases. The only difference is that it can be pushed by the Player into cardinally adjacent squares. The Player is only strong enough to push one boulder at a time. When the player pushes a boulder, they move into the spot the boulder was previously in. Boulders can be pushed onto collectable entities.</th>
    </tr>
        <tr>
        <th>Floor Switch</th>
        <th><img src = "entitiesImg/Floor_Switch.png"></th>
        <th>Switches behave like empty squares, so other entities can appear on top of them. When a boulder is pushed onto a floor switch, it is triggered. Pushing a boulder off the floor switch untriggers.</th>
    </tr>
        <tr>
        <th>Door</th>
        <th><img src = "entitiesImg/Door.png"></th>
        <th>Exists in conjunction with a single key that can open it. If the Player holds the key, they can open the door by moving through it. Once open, it remains open.</th>
    </tr>
        <tr>
        <th>Portal</th>
        <th><img src = "entitiesImg/Portal.png"></th>
        <th> Teleports entities to a corresponding portal. The player must end up in a square cardinally adjacent to the corresponding portal. The square they teleport onto must also be within movement constraints - e.g. the player cannot teleport and end up on a wall. If all squares cardinally adjacent to the corresponding portal are walls, then the player should remain where they are.</th>
    </tr>
        <tr>
        <th>Zombie Toast Spawner</th>
        <th><img src = "entitiesImg/Zombie_Toast_Spawner.png"></th>
        <th>Spawns zombie toasts in an open square cardinally adjacent to the spawner. The Player can destroy a zombie spawner if they have a weapon and are cardinally adjacent to the spawner. If all the cardinally adjacent cells to the spawner are walls, then the spawner will not spawn any zombies.</th>
    </tr>
# goals
each dungeon also has a goal that defines what must be achieved by the player for the dungeon to be considered complete. 

### Basic goals are:
1. Getting to an exit
2. having aboulder on all switches
3. Collecting a certain number of treasure items(or more)

### Complex goals are:
1. Collecting a certain number of treasure AND getting to an exit
2. Collecting a certain number of treasure OR having a boulder on all floor switches
3. Getting to an exit AND (destroying all enemies OR collecting all treasure)

# Winning & Losing
The game is won when all the goals are achieved. The game is lost when the player dies and is removed from the map.
