package com.natewilliford.fixer.objects;

import org.json.JSONObject;

import java.util.*;

public class ResourceStorageComponent extends Component implements Jsonizable {

    public final Map<Integer, Long> resources = new HashMap<>();

    // TODO: Merge allowed and max.
    private final Set<Integer> allowedResources;
    private final Map<Integer, Long> resourceMax = new HashMap<>();

    ResourceStorageComponent(Integer... allowedResources) {
        super();
        this.allowedResources = new HashSet<>(Arrays.asList(allowedResources));
    }

    public long getResource(int resource) {
        return resources.get(resource) != null ? resources.get(resource) : 0;
    }

    public void addResource(int resource, long amount) {
        if (!allowedResources.contains(Resources.ALL) && !allowedResources.contains(resource)) {
            throw new IllegalArgumentException(
                    String.format("Resource type %s cannot be added to this object.", resource));
        }
        resources.put(resource, amount + getResource(resource));
    }

    /**
     * Removes the amount of resources given. Make sure there are enough to remove by calling {@link #getResource(int)}
     * first.
     */
    public void removeResource(int resource, long amount) {
        long currentAmount = getResource(resource);
        if (amount > currentAmount) {
            throw new IllegalArgumentException("Tried to remove too many resources");
        }
        resources.put(resource, Math.max(currentAmount - amount, 0));
    }

    @Override
    public void onInit() {}

    @Override
    int getType() {
        return Components.Type.RESORUCE_STORAGE;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONObject resourcesJson = new JSONObject();
        json.put("resources", resourcesJson);
        for (Map.Entry<Integer, Long> entry : resources.entrySet()) {
            resourcesJson.put(String.valueOf(entry.getKey()), entry.getValue());
        }
        return json;
    }

    public String getDebugState() {
        StringBuilder builder = new StringBuilder();
        for (int resource : allowedResources) {
            builder.append(Resources.getResourceName(resource) + ": " + getResource(resource) + "\n");
        }
        return builder.toString();
    }

    void setResourceMax(int type, long max) {
        resourceMax.put(type, max);
    }

    long getResourceRoomLeft(int type) {
        long max = resourceMax.get(type) != null ? resourceMax.get(type) : Long.MAX_VALUE;
        return max - getResource(type);
    }
}
