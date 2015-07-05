package com.natewilliford.fixer.objects;

import java.util.HashMap;
import java.util.Map;

public class ResourceConversionComponent extends Component implements Updatable {

    /**
     * Map of resource types to the rate that they increase/decrease. Rate is in resource units per second.
     */
    private Map<Integer, Float> sourceResources = new HashMap<>();
    private Map<Integer, Float> produceResources = new HashMap<>();

    private ResourceStorageComponent resourceStorageComponent;

    ResourceConversionComponent() {
        super();
    }

    @Override
    void onInit() {
        resourceStorageComponent = getGameObject().getComponent(ResourceStorageComponent.class);
    }

    @Override
    public void onUpdate(long elapsed) {
        // First figure out how much we can convert.
        float percentThatCanBeDrained = 1f;
        for (Map.Entry<Integer, Float> entry : sourceResources.entrySet()) {
            float remainingResource = resourceStorageComponent.getResource(entry.getKey());
            float amountToDrain = ((float) elapsed / 1000) * entry.getValue();
            if (remainingResource < amountToDrain) {
                percentThatCanBeDrained = remainingResource / amountToDrain;
            }
        }

        if (approximatelyZero(percentThatCanBeDrained)) {
            return;
        }

        for (Map.Entry<Integer, Float> entry : sourceResources.entrySet()) {
            float amountToDrain = ((float) elapsed / 1000) * entry.getValue() * percentThatCanBeDrained;
            resourceStorageComponent.removeResource(entry.getKey(), amountToDrain);
        }

        for (Map.Entry<Integer, Float> entry : produceResources.entrySet()) {
            float amountToProduce = ((float) elapsed / 1000) * entry.getValue() * percentThatCanBeDrained;
            resourceStorageComponent.addResource(entry.getKey(), amountToProduce);
        }
    }

    void addSourceResource(int resource, float rate) {
        sourceResources.put(resource, rate);
    }

    void addProduceResource(int resource, float rate) {
        produceResources.put(resource, rate);
    }

    private boolean approximatelyZero(float val) {
        return Math.abs(val) < 0.0000001f;
    }
}
