package com.natewilliford.fixer.objects;

import com.natewilliford.fixer.objects.com.natewilliford.fixer.objects.testobjects.Components;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class GameObjectTest extends TestCase {

    GameObject gameObject;

    @Before
    public void setUp() throws Exception {
        gameObject = new GameObject();
    }

    @Test
    public void testAddComponent() throws Exception {
        Components.TestComponent testComponent = new Components.TestComponent();
        gameObject.addComponent(testComponent);
        assertSame(testComponent, gameObject.getComponent(Components.TestComponent.class));
    }

    @Test
    public void testAddComponents() throws Exception {
        Components.TestComponent testComponent = new Components.TestComponent();
        Components.OtherTestComponent otherTestComponent = new Components.OtherTestComponent();

        gameObject.addComponents(Arrays.asList(testComponent, otherTestComponent));
        assertSame(testComponent, gameObject.getComponent(Components.TestComponent.class));
        assertSame(otherTestComponent, gameObject.getComponent(Components.OtherTestComponent.class));
    }


}