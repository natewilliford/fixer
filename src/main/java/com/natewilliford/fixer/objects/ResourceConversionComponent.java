package com.natewilliford.fixer.objects;

import java.util.HashMap;
import java.util.Map;

public class ResourceConversionComponent extends Component implements Updatable {

    /**
     * Map of resource types to the rate that they increase/decrease. Rate is in resource units per second.
     */
    private Map<Integer, Integer> sourceResources = new HashMap<>();
    private Map<Integer, Integer> produceResources = new HashMap<>();

    private final int tickTime;

    private ResourceStorageComponent resourceStorageComponent;

    private int pendingTickMillis = 0;

    /**
     * Makes a new component.
     *
     * @param conversionTime The time it takes to do one conversion in millis.
     */
    ResourceConversionComponent(int conversionTime) {
        this.tickTime = conversionTime;
    }

    @Override
    public void onInit() {
        resourceStorageComponent = getGameObject().getComponent(ResourceStorageComponent.class);
    }

    @Override
    public void onUpdate(long elapsed) {
        pendingTickMillis += elapsed;
        int ticks = pendingTickMillis / tickTime;
        pendingTickMillis -= ticks * tickTime;

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
