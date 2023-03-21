package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Swamp;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class ZombieToast extends Enemy {
    public static final double DEFAULT_HEALTH = 5.0;
    public static final double DEFAULT_ATTACK = 6.0;

    public ZombieToast(Position position, double health, double attack) {
        super(position, health, attack);
    }

    @Override
    public void move(Game game) {
        if (isStuck()) {
            Swamp.stuckAction(this);
            return;
        }
        GameMap map = game.getMap();
        Position nextPos = map.randomPosFind(getPosition(), this);
        game.move(this, nextPos);
    }

}
