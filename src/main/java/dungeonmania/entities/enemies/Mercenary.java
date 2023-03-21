package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.Swamp;
import dungeonmania.entities.buildables.Sceptre;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Mercenary extends Enemy implements Interactable {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 10.0;

    private int bribeAmount = Mercenary.DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = Mercenary.DEFAULT_BRIBE_RADIUS;
    private boolean allied = false;
    private boolean followingPlayer = false;

    public Mercenary(Position position, double health, double attack, int bribeAmount, int bribeRadius) {
        super(position, health, attack);
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
    }

    public boolean isAllied() {
        return allied;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (allied)
            return;
        super.onOverlap(map, entity);
    }

    /**
     * check whether the current merc can be bribed
     *
     * @param player
     * @return
     */
    private boolean canBeBribed(Player player) {
        return bribeRadius >= 0 && player.countEntityOfType(Treasure.class) >= bribeAmount;
    }

    private boolean canBeMindControlled(Player player) {
        return player.countEntityOfType(Sceptre.class) >= 1;
    }

    /**
     * bribe the merc
     */
    private void bribe(Player player) {
        for (int i = 0; i < bribeAmount; i++) {
            player.use(Treasure.class);
        }

    }

    /**
     * mind control merc
     */
    private void mindControl(Player player) {
        player.use(Sceptre.class);
    }

    @Override
    public void interact(Player player, Game game) {
        allied = true;
        if (player.countEntityOfType(Sceptre.class) >= 1) {
            mindControl(player);
        } else {
            bribe(player);
        }
        bribe(player);
    }

    @Override
    public void move(Game game) {
        Position nextPos;
        GameMap map = game.getMap();
        if (allied) {
            if (Position.isAdjacent(getPosition(), map.getPlayer().getPosition())) {
                followingPlayer = true;
            }
            if (followingPlayer) {
                nextPos = map.getPlayer().getPreviousPosition();
                // allies adjacent mercenary doesn't stuck
                map.moveTo(this, nextPos);
                return;
            } else {
                nextPos = map.dijkstraPathFind(getPosition(), map.getPlayer().getPosition(), this);
            }
        } else {
            // Follow hostile
            nextPos = map.dijkstraPathFind(getPosition(), map.getPlayer().getPosition(), this);
        }
        if (isStuck()) {
            Swamp.stuckAction(this);
            return;
        }
        map.moveTo(this, nextPos);
    }

    @Override
    public boolean isInteractable(Player player) {
        return !allied && (canBeBribed(player) || canBeMindControlled(player));
    }

    protected void setAllied(boolean allied) {
        this.allied = allied;
    }
}
