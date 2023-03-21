package dungeonmania.goals;

import dungeonmania.Game;
import dungeonmania.entities.enemies.ZombieToastSpawner;

public class EnemiesGoal implements Goal {

    private int goalKill;

    public EnemiesGoal(int goalKill) {
        this.goalKill = goalKill;
    }

    @Override
    public boolean achieved(Game game) {
        if (game.getPlayer() == null) {
            return false;
        }
        // spawner size has to be 0
        return goalKill <= game.totalKilledEnemies()
                && game.getMap().getEntities(ZombieToastSpawner.class).size() == 0;
    }

    @Override
    public String toString(Game game) {
        return ":enemies";
    }
}
