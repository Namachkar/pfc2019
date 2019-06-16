package run;

import io.vertx.core.Future;
import io.vertx.core.Vertx;

public class deployServer {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        Future<String> future = Future.future();

        System.out.println("Runing on port 2108");

        vertx.deployVerticle(WebServer.class.getName(), future);

        future.setHandler(event -> {
            if (event.succeeded()) {
                System.out.println("Runing on port 2108: http://localhost:2108");
            } else {
                event.cause().printStackTrace();
            }
        });

    }
}
