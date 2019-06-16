package commun;

import io.vertx.core.json.JsonObject;

public interface params {
    public static int server_port = 2108;

    public static int db_port = 27017;
    public static String db_host = "localhost";
    public static String db_name = "application";

    public static JsonObject db = new JsonObject().
            put("host", params.db_host).
            put("port", params.db_port).
            put("db_name", params.db_name);

    public static JsonObject config = new JsonObject().
            put("server_port", params.server_port).
            put("db", params.db);

}
