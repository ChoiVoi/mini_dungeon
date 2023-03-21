package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;

public class Sceptre extends Buildable {

    private int durability;
    private int mindControllingTime;

    public Sceptre(int durability, int mindControllingTime) {
        super(null);
        this.durability = durability;
        this.mindControllingTime = mindControllingTime;
    }

    // assume that it can be only used once
    @Override
    public void use(Game game) {
        durability--;
        if (durability <= 0) {
            game.getPlayer().remove(this);
        }
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(
                0,
                0,
                0,
                0,
                0));
    }

    @Override
    public int getDurability() {
        return durability;
    }

    public int getMindControlTime() {
        return mindControllingTime;
    }
}
