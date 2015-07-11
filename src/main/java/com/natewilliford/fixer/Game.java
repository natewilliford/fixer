package com.natewilliford.fixer;

import com.natewilliford.fixer.objects.FarmPlotObject;
import com.natewilliford.fixer.objects.GameObject;
import com.natewilliford.fixer.objects.ResourceStorageComponent;
import com.natewilliford.fixer.objects.Resources;
import net.jcip.annotations.GuardedBy;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private static final long MINIMUM_ELAPSED = 100;

    private final Object lock = new Object();
    @GuardedBy("lock")
    private List<GameObject> gameObjects = new ArrayList<>();

    private UpdateThread updateThread = new UpdateThread();
    private StateLoggerThread stateLoggerThread = new StateLoggerThread();

    public void init() {
        System.out.println("Game init");
        FarmPlotObject plot1 = new FarmPlotObject();
        plot1.getComponent(ResourceStorageComponent.class).addResource(Resources.CORN_SEED, 259);
        plot1.getComponent(ResourceStorageComponent.class).addResource(Resources.WATER, 1240);
        gameObjects.add(plot1);
    }

    public void run() {
        System.out.println("Game run");
        updateThread.start();
        System.out.println("staetlogger run");
        stateLoggerThread.start();
    }

    // TODO: There are probably more efficient ways of doing this. Thread pool?
    private void update(long elapsed) {
//        System.out.println("getting update lock");
        synchronized (lock) {
//            System.out.println("got update lock");
            for (GameObject gameObject : gameObjects) {
                gameObject.update(elapsed);
            }
        }
    }

    private void logState() {
        System.out.println("getting logState lock");
        synchronized (lock) {
            System.out.println("got logState lock");
            for (GameObject gameObject : gameObjects) {
                ResourceStorageComponent storage = gameObject.getComponent(ResourceStorageComponent.class);
                if (storage != null) {
                    System.out.println("Corn: " + storage.getResource(Resources.CORN));
                }
            }
        }
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
            System.out.println("StateLogger run");
            while (true) {
                System.out.println("Trying to log state");
                try {
                    logState();
                    sleep(1000);
                } catch (InterruptedException e) {
                    // whatevs
                }
            }
        }
    }
}
