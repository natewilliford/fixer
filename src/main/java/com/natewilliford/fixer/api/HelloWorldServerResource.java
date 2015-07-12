package com.natewilliford.fixer.api;

import com.natewilliford.fixer.Main;
import com.natewilliford.fixer.objects.User;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class HelloWorldServerResource extends ServerResource {

    @Get("text/plain")
    public String toString() {
        User user = Main.game.users.getForName(getQueryValue("username"));
        if (user != null && user.validPassword(getQueryValue("password"))) {
            return Main.game.getDebugState();
        }

        return "Could not auth";
    }
}
