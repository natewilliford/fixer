package com.natewilliford.fixer.objects;

import java.util.HashMap;
import java.util.Map;

public class ResourceStorageComponent extends Component {

    private final Map<Integer, Float> resources = new HashMap<>();

    ResourceStorageComponent() {
        super();
    }

    public float getResource(int resource) {
        return resources.get(resource) != null ? resources.get(resource) : 0f;
    }

    public void addResource(int resource, float amount) {
        resources.put(resource, amount + getResource(resource));
    }

    /**
     * Removes the amount of resources given. Make sure there are enough to remove by calling {@link #getResource(int)}
     * first.
     */
    public void removeResource(int resource, float amount) {
        float currentAmount = getResource(resource);
        resources.put(resource, Math.max(currentAmount - amount, 0));
    }
}
