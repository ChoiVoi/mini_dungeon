package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SunStoneTest {

    @Test
    @Tag("4-8")
    @DisplayName("Test player can pick up sun stone")
    public void PickUpSunStone() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_DoorsSunStoneTest_pickUpSunStone", "c_DoorsSunStoneTest_pickUpSunStone");

        // pick up key
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("4-9")
    @DisplayName("Test player use sun stone for opening door and it retains")
    public void sunStoneStillRetained() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_DoorsSunStoneTest_pickUpSunStone", "c_DoorsSunStoneTest_pickUpSunStone");

        // pick up key
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        // open door
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());

    }
}
