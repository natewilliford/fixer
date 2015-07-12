package com.natewilliford.fixer;

import com.natewilliford.fixer.db.Database;

public class Main {

    public static void main(String[] args) {

        System.out.println("Starting main");

//        Database db = new Database();
//        db.connect();

        Game game = new Game();

        game.init();
        game.run();





    }
}
