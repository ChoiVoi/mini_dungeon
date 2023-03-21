package dungeonmania.entities;

import dungeonmania.map.GameMap;

public interface ActionOnOverlap {
    public void onOverlap(GameMap map, Entity entity);
}
