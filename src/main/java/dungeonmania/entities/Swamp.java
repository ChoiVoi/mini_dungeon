package dungeonmania.entities;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Swamp extends Entity implements ActionOnOverlap {
    public static final int DEFAULT_MOVING_FACTOR = 1;
    private int movementFactor;

    public Swamp(Position position, int movementFactor) {
        super(position.asLayer(Entity.FLOOR_LAYER));
        this.movementFactor = movementFactor;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Player) { // player doesn't get stuck
            return;
        }

        entity.setIsStuck(true);
        entity.setLeftStuckDuration(movementFactor);
    }

    public static void stuckAction(Entity e) {
        e.decrementLeftStuckDuration();
        if (e.getLeftStuckDuration() == 0) {
            e.setIsStuck(false);
        }
    }

    public int getMovementFactor() {
        return this.movementFactor;
    }
}
