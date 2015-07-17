package com.natewilliford.fixer.objects;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class GameObjects implements Iterable<GameObject> {

    @Override
    public Iterator<GameObject> iterator() {
        return objectsById.values().iterator();
    }

    interface Type {
        int USER = 1;
        int FARM_PLOT = 2;
    }

    private final Map<Long, GameObject> objectsById = new HashMap<>();
    private final Multimap<Long, GameObject> objectsByOwner = HashMultimap.create();

    void addObject(GameObject object) throws IllegalArgumentException {
        if (objectsById.containsKey(object.getId())) {
            throw new IllegalArgumentException("An object with that ID already exists");
        }
        objectsById.put(object.getId(), object);
        objectsByOwner.put(object.getOwnerId(), object);
    }

    Collection<GameObject> getObjectsByOwner(User user) {
        return objectsByOwner.get(user.getId());
    }
}
