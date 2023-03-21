package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;

public class MidnightArmour extends Buildable {
    public static final double DEFAULT_ATTACK = 1;
    public static final double DEFAULT_ATTACK_SCALE_FACTOR = 1;
    public static final int DEFAULT_DURABILITY = 5;
    public static final double DEFAULT_DEFENCE = 0;
    public static final double DEFAULT_DEFENCE_SCALE_FACTOR = 1;

    private int durability;
    private double extraAttack;
    private double extraDefence;

    public MidnightArmour(int durability, double extraAttack, double extraDefence) {
        super(null);
        this.durability = durability;
        this.extraAttack = extraAttack;
        this.extraDefence = extraDefence;
    }

    @Override
    public void use(Game game) {
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(
                0,
                extraAttack,
                extraDefence,
                1,
                1));
    }

    @Override
    public int getDurability() {
        return durability;
    }
}
