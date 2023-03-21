package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.BattleItem;
import dungeonmania.entities.ActionOnDestroy;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class ZombieToastSpawner extends Entity implements ActionOnDestroy, Interactable {
    public static final int DEFAULT_SPAWN_INTERVAL = 0;

    public ZombieToastSpawner(Position position, int spawnInterval) {
        super(position);
    }

    public void spawn(Game game) {
        game.getEntityFactory().spawnZombie(game, this);
    }

    public BattleItem playerGetWeapon(Player player) {
        return player.getWeapon();
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onDestroy(GameMap map) {
        Game g = map.getGame();
        g.unsubscribe(getId());
    }

    @Override
    public void interact(Player player, Game game) {
        playerGetWeapon(player).use(game);
        if (Position.isAdjacent(super.getPosition(), player.getPosition())) {
            game.getMap().destroyEntity(this);
        }
    }

    @Override
    public boolean isInteractable(Player player) {
        return Position.isAdjacent(player.getPosition(), getPosition()) && player.hasWeapon();
    }
}
