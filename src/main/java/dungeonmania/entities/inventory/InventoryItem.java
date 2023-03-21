package dungeonmania.entities.inventory;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

/**
 * A marker interface for InventoryItem
 */
public class InventoryItem extends Entity {
    public InventoryItem(Position position) {
        super(position);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }
}
