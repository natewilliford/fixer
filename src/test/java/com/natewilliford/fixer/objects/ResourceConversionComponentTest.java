package com.natewilliford.fixer.objects;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResourceConversionComponentTest {

    GameObject gameObject;
    ResourceStorageComponent resourceStorageComponent;
    ResourceConversionComponent resourceConversionComponent;

    @Before
    public void setUp() throws Exception {
        gameObject = new GameObject() {
            @Override
            public int getType() {
                return 0;
            }
        };

        resourceStorageComponent = new ResourceStorageComponent(Resources.CORN, Resources.CORN_SEED, Resources.WATER);
        gameObject.addComponent(resourceStorageComponent);

        resourceConversionComponent = new ResourceConversionComponent(1000);
        gameObject.addComponent(resourceConversionComponent);

        gameObject.init();
    }

    @Test
    public void testOnUpdate() throws Exception {
        resourceStorageComponent.addResource(Resources.CORN_SEED, 50);
        resourceStorageComponent.addResource(Resources.WATER, 50);

        resourceConversionComponent.addSourceResource(Resources.CORN_SEED, 1);
        resourceConversionComponent.addSourceResource(Resources.WATER, 1);
        resourceConversionComponent.addProduceResource(Resources.CORN, 3);

        gameObject.update(1000);

        assertEquals(resourceStorageComponent.getResource(Resources.CORN_SEED), 49);
        assertEquals(resourceStorageComponent.getResource(Resources.WATER), 49);
        assertEquals(resourceStorageComponent.getResource(Resources.CORN), 3);
    }

    @Test
    public void testPartialConversion() throws Exception {
        resourceStorageComponent.addResource(Resources.CORN_SEED, 50);
        resourceStorageComponent.addResource(Resources.WATER, 1);

        resourceConversionComponent.addSourceResource(Resources.CORN_SEED, 1);
        resourceConversionComponent.addSourceResource(Resources.WATER, 1);
        resourceConversionComponent.addProduceResource(Resources.CORN, 3);

        gameObject.update(3000);

        assertEquals(resourceStorageComponent.getResource(Resources.CORN_SEED), 49);
        assertEquals(resourceStorageComponent.getResource(Resources.WATER), 0);
        assertEquals(resourceStorageComponent.getResource(Resources.CORN), 3);
    }
}