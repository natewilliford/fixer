package com.natewilliford.fixer.api;

import com.natewilliford.fixer.Main;
import com.natewilliford.fixer.objects.GameObject;
import com.natewilliford.fixer.objects.User;
import org.json.JSONObject;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.Collection;

public class GameObjectsServerResource extends ServerResource {

    @Get("application/json")
    public final String toJson() {
        JSONObject jsonObject = new JSONObject();

        String username = getChallengeResponse().getIdentifier();
        User user = Main.game.users.getForName(username);

        jsonObject.put("user", user.toJson());

        Collection<GameObject> gameObjects = Main.game.gameObjectsForUser(user);

        for (GameObject o : gameObjects) {
            jsonObject.append("objects", o.toJson());
        }

        return jsonObject.toString();

    }
}
