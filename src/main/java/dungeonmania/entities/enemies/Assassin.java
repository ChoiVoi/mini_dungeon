package dungeonmania.entities.enemies;

import java.util.Random;

import dungeonmania.Game;
import dungeonmania.entities.Player;
import dungeonmania.util.Position;

public class Assassin extends Mercenary {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 15.0;
    public static final double DEFAULT_HEALTH = 30.0;
    public static final double DEFAULT_BRIBE_FAIL_RATE = 30.0;

    // The chance that the bribe on an assassin will fail.
    // The value of this field should be always inclusively
    // between 0 and 1.
    private double bribeFailRate;

    public Assassin(Position position, double health, double attack, int bribeAmount, int bribeRadius,
            double bribeFailRate) {
        super(position, health, attack, bribeAmount, bribeRadius);
        this.bribeFailRate = bribeFailRate;
    }

    @Override
    public void interact(Player player, Game game) {
        super.interact(player, game);
        Random randGen = new Random();
        if (randGen.nextDouble() <= bribeFailRate) {
            setAllied(false);
        }
    }

}
