package com.natewilliford.fixer.api;

import com.natewilliford.fixer.Main;
import com.natewilliford.fixer.objects.User;
import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class ResourceTransferServerResource extends ServerResource {

    @Post
    public final void doIt() {
        User user = Main.game.users.getForName(getChallengeResponse().getIdentifier());

        try {
            int objectId = Integer.parseInt(getAttribute("objectId"));
            int type = Integer.parseInt(getAttribute("type"));
            int toObjectId = Integer.parseInt(getAttribute("toObjectId"));
            long amount = Long.parseLong(getAttribute("amount"));

            Main.game.transferResource(user.getId(), objectId, toObjectId, type, amount);
        } catch (Exception e) {
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    }
}
