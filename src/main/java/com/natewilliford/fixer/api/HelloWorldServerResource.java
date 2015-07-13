package com.natewilliford.fixer.api;

import com.natewilliford.fixer.Main;
import com.natewilliford.fixer.objects.Game;
import com.natewilliford.fixer.objects.User;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class HelloWorldServerResource extends ServerResource {

    @Get("text/plain")
    public String toString() {

        String username = getChallengeResponse().getIdentifier();

        Game game = Main.game;
        return game.getDebugStateForUser(username);
    }
}
