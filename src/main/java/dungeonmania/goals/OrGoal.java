package dungeonmania.goals;

import dungeonmania.Game;

public class OrGoal implements Goal {
    private Goal g1;
    private Goal g2;

    public OrGoal(Goal g1, Goal g2) {
        this.g1 = g1;
        this.g2 = g2;
    }

    @Override
    public boolean achieved(Game game) {
        return (g1.achieved(game) || g2.achieved(game));
    }

    @Override
    public String toString(Game game) {
        return "(" + g1.toString(game) + " OR " + g2.toString(game) + ")";
    }

}
