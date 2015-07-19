package com.natewilliford.fixer;

import com.natewilliford.fixer.api.GameObjectsServerResource;
import com.natewilliford.fixer.api.HelloWorldServerResource;
import com.natewilliford.fixer.api.ResourceTransferServerResource;
import com.natewilliford.fixer.db.Database;
import com.natewilliford.fixer.objects.Game;
import com.natewilliford.fixer.objects.User;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.LocalVerifier;

import javax.xml.crypto.Data;

public class Main {

    public static Game game;
    public static Database database;

    public static void main(String[] args) throws Exception {

        System.out.println("Starting main");

        database = new Database();
        database.connect();
        database.init();

        game = new Game();

        game.init();
        game.run();

        Component component = new Component();
        component.getServers().add(Protocol.HTTP, 9876);
        FixerApp app = new FixerApp();


        component.getDefaultHost().attach(app);
        component.start();
    }

    private static final class FixerApp extends Application {
        @Override
        public Restlet createInboundRoot() {
            Router router = new Router(getContext());

            ChallengeAuthenticator guard = new ChallengeAuthenticator(getContext(), ChallengeScheme.HTTP_BASIC, "Valid User");

            guard.setVerifier(new GameVerifier());
            router.attach("/hello", HelloWorldServerResource.class);
            router.attach("/gameobjects", GameObjectsServerResource.class);
            router.attach("/resource/{objectId}/{type}/transfer/{toObjectId}/{amount}", ResourceTransferServerResource.class);

            guard.setNext(router);

            return guard;
        }
    }

    private static final class GameVerifier extends LocalVerifier {

        @Override
        public char[] getLocalSecret(String identifier) {
            User user = Main.game.users.getForName(identifier);
            if (user != null) {
                return user.getPassword().toCharArray();
            }

            return null;
        }
    }
}
