package com.natewilliford.fixer.objects;

import net.jcip.annotations.GuardedBy;

public class Game {

    private static final long MINIMUM_ELAPSED = 10;

    private final Object lock = new Object();
    @GuardedBy("lock")
    private GameObjects gameObjects = new GameObjects();

    private static final IdCounter idCounter = new IdCounter();

    public final Users users = new Users();

    private UpdateThread updateThread = new UpdateThread();
    private StateLoggerThread stateLoggerThread = new StateLoggerThread();

    public void init() {
        synchronized (lock) {
            System.out.println("Game init");

            User nate = buildInitUser(idCounter.nextId(), "hungryish", "oisd89seofkijdfsoi");
            User natalie = buildInitUser(idCounter.nextId(), "sexboxfox", "adsofij893i");

            nate.getComponent(ResourceStorageComponent.class).addResource(Resources.WATER, 20);

            FarmPlotObject natesPlot = new FarmPlotObject(idCounter.nextId());
            natesPlot.setOwner(nate);
            gameObjects.addObject(natesPlot);

            FarmPlotObject nataliesPlot = new FarmPlotObject(idCounter.nextId());
            nataliesPlot.setOwner(natalie);
            gameObjects.addObject(nataliesPlot);
        }
    }

    private User buildInitUser(long id, String username, String password) {
        User user = new User(id, username, password);
        ResourceStorageComponent storage = user.getComponent(ResourceStorageComponent.class);
        storage.addResource(Resources.WATER, 100);
        storage.addResource(Resources.CORN_SEED, 100);

        users.addUser(user);
        gameObjects.addObject(user);
        return user;
    }

    public void run() {
        System.out.println("Game run");
        updateThread.start();
        stateLoggerThread.start();
    }

    // TODO: There are probably more efficient ways of doing this. Thread pool?
    private void update(long elapsed) {
        synchronized (lock) {
            for (GameObject gameObject : gameObjects) {
                gameObject.update(elapsed);
            }
        }
    }

    public String getDebugState() {
        synchronized (lock) {
            for (GameObject gameObject : gameObjects) {
                ResourceStorageComponent storage = gameObject.getComponent(ResourceStorageComponent.class);
                if (storage != null) {
                    return "Corn seed: " + storage.getResource(Resources.CORN_SEED) + "\n" +
                            "Water: " + storage.getResource(Resources.WATER) + "\n" +
                            "Corn: " + storage.getResource(Resources.CORN) + "\n";
                }
            }
        }
        return "No debug state could be built";
    }

    public String getDebugStateForUser(String username) {
        User user = users.getForName(username);

        return "User Resources: \n" +
                user.getComponent(ResourceStorageComponent.class).getDebugState();
    }


    private void logState() {
        System.out.print(getDebugState());
    }

    private final class UpdateThread extends Thread {
        private long lastUpdated = -1;

        @Override
        public void run() {
            while(true) {
                long now = System.currentTimeMillis();
                if (lastUpdated < 0) {
                    lastUpdated = now;
                }

                long elapsed = now - lastUpdated;

                if (elapsed >= MINIMUM_ELAPSED) {
                    lastUpdated = now;
                    update(elapsed);
                }
            }
        }
    }

    private final class StateLoggerThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    logState();
                    sleep(1000);
                } catch (InterruptedException e) {
                    // whatevs
                }
            }
        }
    }

    private static final class IdCounter {
        private long nextId = 0;
        IdCounter() {}

        long nextId() {
            return ++nextId;
        }
    }
}
