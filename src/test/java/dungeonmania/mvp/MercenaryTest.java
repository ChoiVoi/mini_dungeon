package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MercenaryTest {

    @Test
    @Tag("12-1")
    @DisplayName("Test mercenary in line with Player moves towards them")
    public void simpleMovement() {
        // Wall Wall Wall Wall Wall Wall
        // P1 P2 P3 P4 M4 M3 M2 M1 . Wall
        // Wall Wall Wall Wall Wall Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_simpleMovement", "c_mercenaryTest_simpleMovement");

        assertEquals(new Position(8, 1), getMercPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(7, 1), getMercPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(6, 1), getMercPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(5, 1), getMercPos(res));
    }

    @Test
    @Tag("12-2")
    @DisplayName("Test mercenary stops if they cannot move any closer to the player")
    public void stopMovement() {
        // Wall Wall Wall
        // P1 P2 Wall M1 Wall
        // Wall Wall Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_stopMovement", "c_mercenaryTest_stopMovement");

        Position startingPos = getMercPos(res);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(startingPos, getMercPos(res));
    }

    @Test
    @Tag("12-3")
    @DisplayName("Test mercenaries can not move through closed doors")
    public void doorMovement() {
        // Wall Door Wall
        // P1 P2 Wall M1 Wall
        // Key Wall Wall Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_doorMovement", "c_mercenaryTest_doorMovement");

        Position startingPos = getMercPos(res);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(startingPos, getMercPos(res));
    }

    @Test
    @Tag("12-4")
    @DisplayName("Test mercenary moves around a wall to get to the player")
    public void evadeWall() {
        // Wall M2
        // P1 P2 Wall M1
        // Wall M2
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_evadeWall", "c_mercenaryTest_evadeWall");

        res = dmc.tick(Direction.RIGHT);
        assertTrue(new Position(4, 1).equals(getMercPos(res))
                || new Position(4, 3).equals(getMercPos(res)));
    }

    @Test
    @Tag("12-5")
    @DisplayName("Testing a mercenary can be bribed with a certain amount")
    public void bribeAmount() {
        // Wall Wall Wall Wall Wall
        // P1 P2/Treasure P3/Treasure P4/Treasure M4 M3 M2 M1 Wall
        // Wall Wall Wall Wall Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_bribeAmount", "c_mercenaryTest_bribeAmount");

        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        // pick up first treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(7, 1), getMercPos(res));

        // attempt bribe
        assertThrows(InvalidActionException.class, () -> dmc.interact(mercId));
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // pick up second treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(6, 1), getMercPos(res));

        // attempt bribe
        assertThrows(InvalidActionException.class, () -> dmc.interact(mercId));
        assertEquals(2, TestUtils.getInventory(res, "treasure").size());

        // pick up third treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(3, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(5, 1), getMercPos(res));

        // achieve bribe
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
    }

    @Test
    @Tag("12-6")
    @DisplayName("Testing a mercenary can be bribed within a radius")
    public void bribeRadius() {
        // Wall Wall Wall Wall Wall
        // P1 P2/Treasure P3 P4 M4 M3 M2 M1 Wall
        // Wall Wall Wall Wall Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_bribeRadius", "c_mercenaryTest_bribeRadius");

        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();
        // pick up treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(7, 1), getMercPos(res));

        // attempt bribe
        assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
    }

    @Test
    @Tag("12-7")
    @DisplayName("Testing an allied mercenary does not battle the player")
    public void allyBattle() {
        // Wall Wall Wall
        // P1 P2/Treasure . M2 M1 Wall
        // Wall Wall Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_allyBattle", "c_mercenaryTest_allyBattle");

        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        // pick up treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // achieve bribe
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());

        // walk into mercenary, a battle does not occur
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, res.getBattles().size());
    }

    @Test
    @Tag("12-8")
    @DisplayName("Testing an allied mercenary moving behaviour")
    public void allyMovement() {
        // Wall Wall Wall
        // P1 P2/Treasure . M2 M1 Wall
        // Wall Wall Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_allyBattle", "c_mercenaryTest_allyBattle");

        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        // pick up treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // achieve bribe
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());

        // walk into mercenary, a battle does not occur
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, res.getBattles().size());
        assertTrue(new Position(3, 1).equals(getMercPos(res)));
        res = dmc.tick(Direction.DOWN);
        // mercenary starts to follow player
        assertTrue(new Position(3, 1).equals(getMercPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(3, 2).equals(getMercPos(res)));
        res = dmc.tick(Direction.UP);
        assertTrue(new Position(2, 2).equals(getMercPos(res)));
        res = dmc.tick(Direction.UP);
        assertTrue(new Position(2, 1).equals(getMercPos(res)));
        res = dmc.tick(Direction.LEFT);
        assertTrue(new Position(2, 0).equals(getMercPos(res)));
        res = dmc.tick(Direction.DOWN);
        assertTrue(new Position(1, 0).equals(getMercPos(res)));
    }

    @Test
    @Tag("12-5")
    @DisplayName("Testing a mercenary can mindControlled with sceptre")
    public void mindControl() {
        // Wall Wall Wall Wall Wall
        // P1 P2/Treasure P3/Treasure P4/Treasure M4 M3 M2 M1 Wall
        // Wall Wall Wall Wall Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_mindControl",
                "c_mercenaryTest_mindControl");

        // pick up sceptre and build sceptre
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        assertEquals(1, TestUtils.getEntities(res, "mercenary").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        // check build successful
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());
        assertEquals(1, TestUtils.getEntities(res, "mercenary").size());
    }

    private Position getMercPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "mercenary").get(0).getPosition();
    }
}
