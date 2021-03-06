package se2.rpgcompanion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CampaignTest {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String GAME_MASTER = "game_master_id";
    private static final String GAME_MASTER_NAME = "game_master_name";

    private static final String CHARACTER_A = "A";
    private static final String CHARACTER_B = "B";

    private static final String PLAYER_A = "PA";
    private static final String PLAYER_B = "PB";

    private Campaign campaign;

    /*
        Used mostly for the getter+setter tests. Any constructor tests should explicitly reconstruct
        the Campaign object
     */
    @Before
    public void createDefaultCampaign() throws JSONException {
        JSONObject input = new JSONObject();
        input.put("name", NAME);
        input.put("game_master", GAME_MASTER);
        input.put("game_master_name", GAME_MASTER_NAME);

        input.put("players", new JSONArray("[\"" + PLAYER_A + "\", \"" + PLAYER_B + "\"]"));
        input.put("character_ids", new JSONArray("[\"" + CHARACTER_A + "\", \"" + CHARACTER_B + "\"]"));

        campaign = new Campaign(ID, input);
    }

    @Test
    public void testConstructorNoPlayersNoCharacters() throws JSONException {
        JSONObject input = new JSONObject();
        input.put("name", NAME);
        input.put("game_master", GAME_MASTER);
        input.put("game_master_name", GAME_MASTER_NAME);

        input.put("players", new JSONArray());
        input.put("character_ids", new JSONArray());

        campaign = new Campaign(ID, input);

        assertEquals(ID, campaign.getId());
        assertEquals(NAME, campaign.getName());
        assertEquals(GAME_MASTER, campaign.getGameMaster());
        assertEquals(GAME_MASTER_NAME, campaign.getGameMasterName());
        assertTrue(campaign.getPlayers().isEmpty());
        assertTrue(campaign.getCharacterIds().isEmpty());
    }

    @Test
    public void testConstructorNoPlayers() throws JSONException {
        JSONObject input = new JSONObject();
        input.put("name", NAME);
        input.put("game_master", GAME_MASTER);
        input.put("game_master_name", GAME_MASTER_NAME);

        input.put("players", new JSONArray());
        input.put("character_ids", new JSONArray("[\"" + CHARACTER_A + "\", \"" + CHARACTER_B + "\"]"));

        campaign = new Campaign(ID, input);

        assertEquals(ID, campaign.getId());
        assertEquals(NAME, campaign.getName());
        assertEquals(GAME_MASTER, campaign.getGameMaster());
        assertEquals(GAME_MASTER_NAME, campaign.getGameMasterName());
        assertTrue(campaign.getPlayers().isEmpty());

        assertTrue(campaign.getCharacterIds().size() == 2);
        assertTrue(campaign.getCharacterIds().contains(CHARACTER_A));
        assertTrue(campaign.getCharacterIds().contains(CHARACTER_B));
    }



    @Test
    public void testConstructorNoCharacters() throws JSONException {
        JSONObject input = new JSONObject();
        input.put("name", NAME);
        input.put("game_master", GAME_MASTER);
        input.put("game_master_name", GAME_MASTER_NAME);

        input.put("players", new JSONArray("[\"" + PLAYER_A + "\", \"" + PLAYER_B + "\"]"));
        input.put("character_ids", new JSONArray());

        campaign = new Campaign(ID, input);

        assertEquals(ID, campaign.getId());
        assertEquals(NAME, campaign.getName());
        assertEquals(GAME_MASTER, campaign.getGameMaster());
        assertEquals(GAME_MASTER_NAME, campaign.getGameMasterName());
        assertTrue(campaign.getCharacterIds().isEmpty());

        assertTrue(campaign.getPlayers().size() == 2);
        assertTrue(campaign.getPlayers().contains(PLAYER_A));
        assertTrue(campaign.getPlayers().contains(PLAYER_B));
    }

    @Test
    public void testConstructor() throws JSONException {
        JSONObject input = new JSONObject();
        input.put("name", NAME);
        input.put("game_master", GAME_MASTER);
        input.put("game_master_name", GAME_MASTER_NAME);

        input.put("players", new JSONArray("[\"" + PLAYER_A + "\", \"" + PLAYER_B + "\"]"));
        input.put("character_ids", new JSONArray("[\"" + CHARACTER_A + "\", \"" + CHARACTER_B + "\"]"));

        campaign = new Campaign(ID, input);

        assertEquals(ID, campaign.getId());
        assertEquals(NAME, campaign.getName());
        assertEquals(GAME_MASTER, campaign.getGameMaster());
        assertEquals(GAME_MASTER_NAME, campaign.getGameMasterName());

        assertTrue(campaign.getCharacterIds().size() == 2);
        assertTrue(campaign.getCharacterIds().contains(CHARACTER_A));
        assertTrue(campaign.getCharacterIds().contains(CHARACTER_B));

        assertTrue(campaign.getPlayers().size() == 2);
        assertTrue(campaign.getPlayers().contains(PLAYER_A));
        assertTrue(campaign.getPlayers().contains(PLAYER_B));
    }

    @Test
    public void testGetAndSetId() {
        campaign.setId("newId");
        assertEquals("newId", campaign.getId());
    }

    @Test
    public void testGetAndSetName() {
        campaign.setName("newName");
        assertEquals("newName", campaign.getName());
    }

    @Test
    public void testGetAndSetGameMaster() {
        campaign.setGameMaster("gm");
        assertEquals("gm", campaign.getGameMaster());
    }

    @Test
    public void testGetAndSetGameMasterName() {
        campaign.setGameMasterName("gmName");
        assertEquals("gmName", campaign.getGameMasterName());
    }

    @Test
    public void testGetAndSetPlayers() {
        List<String> players  = new ArrayList<String>();
        players.add("player1");
        players.add("player2");
        campaign.setPlayers(players);

        assertEquals(players, campaign.getPlayers());
    }

    @Test
    public void testGetAndSetCharacters() {
        List<String> players  = new ArrayList<String>();
        players.add("character1");
        players.add("character2");
        campaign.setPlayers(players);

        assertEquals(players, campaign.getPlayers());
    }
}