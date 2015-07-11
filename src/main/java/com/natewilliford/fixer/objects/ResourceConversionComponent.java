package com.natewilliford.fixer.objects;

import java.util.HashMap;
import java.util.Map;

public class ResourceConversionComponent extends Component implements Updatable {

    public static final int TICK_TIME = 1000;

    /**
     * Map of resource types to the rate that they increase/decrease. Rate is in resource units per second.
     */
    private Map<Integer, Integer> sourceResources = new HashMap<>();
    private Map<Integer, Integer> produceResources = new HashMap<>();

    private ResourceStorageComponent resourceStorageComponent;

    private int pendingTickMillis = 0;

    ResourceConversionComponent() {
        super();
    }

    @Override
    public void onInit() {
        resourceStorageComponent = getGameObject().getComponent(ResourceStorageComponent.class);
    }

    @Override
    public void onUpdate(long elapsed) {
        pendingTickMillis += elapsed;
        int ticks = pendingTickMillis / TICK_TIME;
        pendingTickMillis -= ticks * TICK_TIME;

        for (int i = 0; i < ticks; i++) {
            if (!canTick()) {
                break;
            }
            tick();
        }
    }

    private boolean canTick() {
        for (Map.Entry<Integer, Integer> entry : sourceResources.entrySet()) {
            if (resourceStorageComponent.getResource(entry.getKey()) < entry.getValue()) {
                // There aren't enough resources to tick.
                return false;
            }
        }
        return true;
    }

    private void tick() {
        for (Map.Entry<Integer, Integer> entry : sourceResources.entrySet()) {
            resourceStorageComponent.removeResource(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<Integer, Integer> entry : produceResources.entrySet()) {
            resourceStorageComponent.addResource(entry.getKey(), entry.getValue());
        }
    }

    void addSourceResource(int resource, int rate) {
        sourceResources.put(resource, rate);
    }

    void addProduceResource(int resource, int rate) {
        produceResources.put(resource, rate);
    }
}
