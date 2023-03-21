package dungeonmania;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.json.JSONException;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.ResponseBuilder;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;

public class DungeonManiaController {
    private Game game = null;

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    /**
     * /dungeons
     */
    public static List<String> dungeons() {
        return FileLoader.listFileNamesInResourceDirectory("dungeons");
    }

    /**
     * /configs
     */
    public static List<String> configs() {
        return FileLoader.listFileNamesInResourceDirectory("configs");
    }

    /**
     * /game/new
     */
    public DungeonResponse newGame(String dungeonName, String configName) throws IllegalArgumentException {
        if (!dungeons().contains(dungeonName)) {
            throw new IllegalArgumentException(dungeonName + " is not a dungeon that exists");
        }

        if (!configs().contains(configName)) {
            throw new IllegalArgumentException(configName + " is not a configuration that exists");
        }

        try {
            GameBuilder builder = new GameBuilder();
            game = builder.setConfigName(configName).setDungeonName(dungeonName).buildGame();
            return ResponseBuilder.getDungeonResponse(game);
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * /game/dungeonResponseModel
     */
    public DungeonResponse getDungeonResponseModel() {
        DungeonResponse dungeonStateResponse = ResponseBuilder.getDungeonResponse(game);
        String dungeonId = dungeonStateResponse.getDungeonId();
        String dungeonName = dungeonStateResponse.getDungeonName();
        List<EntityResponse> entityResponses = dungeonStateResponse.getEntities();
        List<ItemResponse> itemResponses = dungeonStateResponse.getInventory();
        List<BattleResponse> battleResponses = dungeonStateResponse.getBattles();
        List<String> buildalesList = dungeonStateResponse.getBuildables();
        String goalString = dungeonStateResponse.getGoals();

        return new DungeonResponse(dungeonId, dungeonName, entityResponses, itemResponses, battleResponses,
                buildalesList, goalString);
    }

    /**
     * /game/tick/item
     */
    public DungeonResponse tick(String itemUsedId) throws IllegalArgumentException, InvalidActionException {
        return ResponseBuilder.getDungeonResponse(game.tick(itemUsedId));
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        return ResponseBuilder.getDungeonResponse(game.tick(movementDirection));
    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        List<String> validBuildables = List.of("bow", "shield", "midnight_armour", "sceptre", "midnight_armour");
        if (!validBuildables.contains(buildable)) {
            throw new IllegalArgumentException("Only bow, shield, midnight_armour and sceptre can be built");
        }

        return ResponseBuilder.getDungeonResponse(game.build(buildable));
    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return ResponseBuilder.getDungeonResponse(game.interact(entityId));
    }

    /**
     * /game/save
     */

    public DungeonResponse saveGame(String name) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    Files.newOutputStream(Paths.get("src", "main", "resources", "saveFile", name)));
            oos.writeObject(this.game);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("cannot save");
        }
        return ResponseBuilder.getDungeonResponse(game);
    }

    /**
     * /game/load
     */
    public DungeonResponse loadGame(String name) {
        try {
            ObjectInputStream ois = new ObjectInputStream(
                    Files.newInputStream(Paths.get("src", "main", "resources", "saveFile", name)));
            Game gamObject = (Game) ois.readObject();
            this.game = gamObject;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("cannot load the game");
        }

        return ResponseBuilder.getDungeonResponse(game);
    }

    /**
     * /games/all
     */
    public List<String> allGames() {
        return FileLoader.savedGamesInResourceDirectory("saveFile");
    }

    /**
     * /game/new/generate
     */
    public DungeonResponse generateDungeon(
            int xStart, int yStart, int xEnd, int yEnd, String configName) throws IllegalArgumentException {
        return null;
    }

    /**
     * /game/rewind
     */
    public DungeonResponse rewind(int ticks) throws IllegalArgumentException {
        return null;
    }

}
