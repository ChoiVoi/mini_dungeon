package dungeonmania.entities;

import dungeonmania.map.GameMap;

public interface ActionOnMovedAway {
    public void onMovedAway(GameMap map, Entity entity);
}
