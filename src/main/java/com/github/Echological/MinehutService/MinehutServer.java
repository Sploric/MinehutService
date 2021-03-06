package com.github.Echological.MinehutService;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import static com.github.Echological.MinehutService.MinehutService.mainApi;

public class MinehutServer {

    public MinehutServer(String id) {
        MinehutServer.id = id;
    }

    static String id = System.getenv("SERVER_ID");

    static JSONObject data() {
        String endpoint = mainApi("server", id);
        HttpResponse<JsonNode> res = Unirest.get(endpoint)
                .asJson();

        JsonNode node = res.getBody();

        if (node == null) {
            System.out.println("Request to "+endpoint+" failed ("+res.getStatus()+")");
            return null;
        }

        return node.getObject().getJSONObject("server");
    }

    /**
     * @return JSONObject - server data from the api request
     */
    public static JSONObject server(){
        return data();
    }

    /**
     * @return String - name of the server
     */
    public static String name(){
        JSONObject data = data();
        if (data == null) return null;
        return data.getString("name");
    }

    /**
     * @return boolean - boolean determining if the server is online or offline
     */
    public static boolean online(){
        JSONObject data = data();
        if (data == null) return false;
        return data.getBoolean("online");
    }

    /**
     * @return JSONArray - JSON array of player uuids
     */
    public static JSONArray players(){
        JSONObject data = data();
        if (data == null) return null;
        return data.getJSONArray("players");
    }

    /**
     * @return String - motd of the server
     */
    public static String motd(){
        JSONObject data = data();
        if (data == null) return null;
        return data.getString("motd");
    }

    /**
     * @return JSONArray - installed content on the server
     * @deprecated endpoint no longer exists
     */
    public static JSONArray installedContent(){
        JSONObject data = data();
        if (data == null) return null;
        return data.getJSONArray("installed_content");
    }

    /**
     * @return String - the id of the server
     */
    public static String id() {
        return id;
    }

    /**
     * @return Boolean - if the server exists and is online
     */
    public static boolean exists() {
        if (id == null || data() == null) return false; // checks if it exists
        return online(); // if online = good, if not = bad
    }

}
