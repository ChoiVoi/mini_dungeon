package dungeonmania.mvp;
import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class SwampTest {
    @Test
    @Tag("16-1")
    @DisplayName("Test spider stuck in swamp")
    public void spiderStuck() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        // movement_factor = 2
        DungeonResponse res = dmc.newGame("d_swampTest_spider", "c_swampTest_spider");
        Position pos = TestUtils.getEntities(res, "spider").get(0).getPosition();

        List<Position> movementTrajectory = new ArrayList<>();
        int x = pos.getX();
        int y = pos.getY();
        int nextPositionElement = 0;

        // Stuck
        movementTrajectory.add(new Position(x, y));
        // Stuck
        movementTrajectory.add(new Position(x, y));
        // Up
        movementTrajectory.add(new Position(x, y - 1));
        // Stuck
        movementTrajectory.add(new Position(x, y - 1));
        // Stuck
        movementTrajectory.add(new Position(x, y - 1));
        // Right
        movementTrajectory.add(new Position(x + 1, y - 1));

        // Assert Circular Movement of Spider
        for (int i = 0; i < 6; ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(movementTrajectory.get(nextPositionElement),
            TestUtils.getEntities(res, "spider").get(0).getPosition());
            nextPositionElement++;
        }
    }
    
    @Test
    @Tag("16-2")
    @DisplayName("Testing zombie stuck in swamp")
    public void zombieStuck() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swampTest_zombie", "c_swampTest_zombie");
        assertEquals(1, getZombies(res).size());
        assertEquals(getZombies(res).get(0).getPosition(), new Position(9, 9));
        
        Position prevPosition = getZombies(res).get(0).getPosition();
        //   "movement_factor": 5
        for (int i = 0; i < 5; i++) {
            res = dmc.tick(Direction.UP);
            assertTrue(prevPosition.equals(getZombies(res).get(0).getPosition()));
        }
        res = dmc.tick(Direction.UP);
        // out of stuck it should change the position
        // it doesn't assume that random movement includes choosing to stay still
        assertFalse(prevPosition.equals(getZombies(res).get(0).getPosition()));
    }

    private List<EntityResponse> getZombies(DungeonResponse res) {
        return TestUtils.getEntities(res, "zombie_toast");
    }

    @Test
    @Tag("16-3")
    @DisplayName("Test mercenary stuck in swamp")
    public void allyMercenaryDoesNotStuck() {
        //                                  Wall    Wall   Wall    Wall    Wall    Wall
        // P1       P2      P3      P4      M4      M3     M2/S    M1/S      .      Wall
        //                                  Wall    Wall   Wall    Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swampTest_mercenary", "c_swampTest_mercenary");

        assertEquals(new Position(8, 1), getMercPos(res));
        res = dmc.tick(Direction.RIGHT);
        // Stuck
        assertEquals(new Position(8, 1), getMercPos(res));
        res = dmc.tick(Direction.RIGHT);
        // Stuck
        assertEquals(new Position(8, 1), getMercPos(res));
        res = dmc.tick(Direction.RIGHT);
        // Out of stuck
        assertEquals(new Position(7, 1), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        // Stuck
        assertEquals(new Position(7, 1), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        // Stuck
        assertEquals(new Position(7, 1), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        // Out of stuck
        assertEquals(new Position(6, 1), getMercPos(res));
    }

    @Test
    @Tag("16-4")
    @DisplayName("Testing allied ajacent mercenary does not get stuck")
    public void allyMovement() {
        // Wall     Wall                    Wall    Wall    Wall
        // P1       P2/Treasure      P3     M2/S    M1/S    Wall
        //                                  Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        // "movement_factor": 2
        DungeonResponse res = dmc.newGame("d_swampTest_allyMercenary", "c_swampTest_allyMercenary");

        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();
        // initial position
        assertEquals(new Position(5, 1), getMercPos(res));
        // pick up treasure
        res = dmc.tick(Direction.RIGHT);
        // stuck at (5,1) tick 1
        assertEquals(new Position(5, 1), getMercPos(res));
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        // stuck at (5,1) tick 2
        assertEquals(new Position(5, 1), getMercPos(res));

        res = dmc.tick(Direction.RIGHT);
        // plyayer at (3,1), Mercenary stuck at (4,1)
        assertEquals(new Position(4, 1), getMercPos(res));

        res = dmc.tick(Direction.LEFT);
        // plyayer at (2,1), Mercenary stuck at (3,1)
        assertEquals(new Position(4, 1), getMercPos(res));
    }


    private Position getMercPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "mercenary").get(0).getPosition();
    }

    @Test
    @Tag("16-5")
    @DisplayName("Testing player does not get stuck")
    public void playerDoesNotStuck() {
        DungeonManiaController dmc = new DungeonManiaController();
        // "movement_factor": 2
        DungeonResponse res = dmc.newGame("d_swampTest_player", "c_swampTest_player");

        EntityResponse player = TestUtils.getPlayer(res).get();
        assertEquals(new Position(1, 1), player.getPosition());
        res = dmc.tick(Direction.RIGHT);
        player = TestUtils.getPlayer(res).get();
        assertEquals(new Position(2, 1), player.getPosition());
        res = dmc.tick(Direction.RIGHT);
        player = TestUtils.getPlayer(res).get();
        assertEquals(player.getPosition(), new Position(3, 1));      
    }
}
