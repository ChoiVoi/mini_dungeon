package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AssassinTest {

    @Test
    @Tag("15-1")
    @DisplayName("Testing a assassin bribe fail [failRate=1.0]")
    public void bribeFail() {
        // Wall Wall Wall Wall Wall
        // P1 P2/Treasure P3/T P4 A4 A3 A2 A1 Wall
        // Wall Wall Wall Wall Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_bribeFail", "c_assassinTest_bribeFail");

        String assassinId = TestUtils.getEntitiesStream(res, "assassin").findFirst().get().getId();

        // pick up treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(7, 1), getAssassinPos(res));

        // attempt bribe first time
        res = assertDoesNotThrow(() -> dmc.interact(assassinId));
        assertEquals(new Position(6, 1), getAssassinPos(res));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        // second attemt bribe
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        // interact counts as one move
        assertEquals(new Position(5, 1), getAssassinPos(res));

        // attempt bribe second time but does not throw becuase first time bribe failed
        res = assertDoesNotThrow(() -> dmc.interact(assassinId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
    }

    private Position getAssassinPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "assassin").get(0).getPosition();
    }
}
