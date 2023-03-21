package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersistenceTest {
    @Test
    @Tag("17-1")
    @DisplayName("Test save entities position")
    public void testSaveEntitesPosition() throws Exception {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
                "d_gameModePersistsAcrossSaveLoad", "c_gameModePersistsAcrossSaveLoad");

        EntityResponse player = TestUtils.getPlayer(res).get();
        assertEquals(new Position(1, 1), player.getPosition());
        res = dmc.tick(Direction.DOWN);
        player = TestUtils.getPlayer(res).get();
        assertEquals(new Position(1, 2), player.getPosition());

        // save game
        dmc.saveGame("test_save_entities_postition");
        res = dmc.loadGame("test_save_entities_postition");
        player = TestUtils.getPlayer(res).get();
        assertEquals(new Position(1, 2), player.getPosition());
    }
}
