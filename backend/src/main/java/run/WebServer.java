package run;

import commun.params;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.apache.log4j.Logger;

import java.util.List;

public class WebServer extends AbstractVerticle {
    public Router router = Router.router(vertx);
    private Logger logger = Logger.getLogger(WebServer.class);
    private JsonObject config = new JsonObject();
    private MongoClient client;

    //Define Router
    public void getRouter() {
        this.router.route().handler(hdlr -> {
            HttpServerResponse resp = hdlr.response()
                    .putHeader("Access-Control-Allow-Origin", "http://localhost:" + config.getInteger("client_port"))
                    .putHeader("Access-Control-Allow-Header", "*")
                    .putHeader("Access-Control-Allow-Credentials", "true");
            hdlr.next();
        });
        // consommer le corps de requete HTTP
        this.router.route().handler(BodyHandler.create());

        //route retourner un utilisateur
        this.router.route("/user/:id").method(HttpMethod.POST).handler(this::getUserHandler);
        this.router.route("/users/").method(HttpMethod.POST).handler(this::getUsersHandler);
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        try {
            // charger le fichier de configuration
            config = params.config;
            //Router
            this.getRouter();
            // le port du serveur http
            int port = params.server_port;
            // creation serveur HTTP
            HttpServer server = vertx.createHttpServer();
            //config mongo db
            JsonObject mongoconfig = params.db;
            // creer un client mongoDB
            this.client = MongoClient.createShared(vertx, mongoconfig);
            server.requestHandler(router::accept).listen(port);
            logger.debug("server is up, port: " + port);
            //*****************//
            startFuture.complete();
        } catch (Exception e) {
            logger.error(e, e);
            startFuture.fail(e);
        }
    }

    private void getUserHandler(RoutingContext routingContext) {
        try {
            logger.debug("start route `/user/:id` handler `getUserHandler`");
            JsonObject mongoconfig = config.getJsonObject("db");
            MongoClient client = MongoClient.createShared(vertx, mongoconfig);

            HttpServerResponse response = routingContext.response();
            String id = routingContext.request().getParam("id");

            JsonObject query = new JsonObject()
                    .put("_id", id);
            System.out.println(query);

            client.find("user", query, (res) -> {
                returnData(res, response);
            });

        } catch (Exception e) {
            logger.error(e, e);
        }
    }

    private void getUsersHandler(RoutingContext routingContext) {
        try {
            logger.debug("start route `/users` handler `getUsersHandler`");
            JsonObject mongoconfig = config.getJsonObject("db");
            MongoClient client = MongoClient.createShared(vertx, mongoconfig);

            HttpServerResponse response = routingContext.response();
            String id = routingContext.request().getParam("id");

            JsonObject query = new JsonObject();
            System.out.println(query);

            client.find("user", query, (res) -> {
                returnData(res, response);
            });

        } catch (Exception e) {
            logger.error(e, e);
        }
    }

    private void returnData(AsyncResult<List<JsonObject>> res, HttpServerResponse response) {
        if (res.succeeded()) {
            JsonObject responseContent = new JsonObject()
                    .put("succeeded", true)
                    .put("data", res.result());
            response.putHeader("content-type", "application/json; charset=utf-8");
            response.end(responseContent.encode());
        } else {
            JsonObject responseContent = new JsonObject()
                    .put("succeeded", false);
            response.putHeader("content-type", "application/json; charset=utf-8");
            response.end(responseContent.encode());
        }
    }

    // send http response
    private void sendResponse(JsonObject responseContent, HttpServerResponse response) {
        try {
            logger.debug("sending http Response, content: " + responseContent.encodePrettily());
            response.putHeader("content-type", "application/json; charset=utf-8");
            response.end(responseContent.encode());
        } catch (Exception e) {
            logger.error(e, e);
        }
    }
}