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
        <th><img src = "/entitiesimg/Wall.png"></th>
        <th>Blocks the movement of the Player, enemies and boulders</th>
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
