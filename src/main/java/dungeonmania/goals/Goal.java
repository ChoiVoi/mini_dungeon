package dungeonmania.goals;

import java.io.Serializable;

import dungeonmania.Game;

public interface Goal extends Serializable {
    /**
     * @return true if the goal has been achieved, false otherwise
     */
    public boolean achieved(Game game);

    public String toString(Game game);

}
