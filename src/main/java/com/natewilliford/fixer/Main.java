package com.natewilliford.fixer;

import com.natewilliford.fixer.api.HelloWorldServerResource;
import com.natewilliford.fixer.objects.Game;
import org.restlet.Server;
import org.restlet.data.Protocol;

public class Main {

    public static Game game;

    public static void main(String[] args) {

        System.out.println("Starting main");

//        Database db = new Database();
//        db.connect();

        game = new Game();


        game.init();
        game.run();

        try {
            new Server(Protocol.HTTP, 8182, HelloWorldServerResource.class).start();
        } catch (Exception e) {
            System.out.println("Shit went down:" + e.getMessage());
        }

    }
}
