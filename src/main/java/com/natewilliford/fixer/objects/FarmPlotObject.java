package com.natewilliford.fixer.objects;

public class FarmPlotObject extends GameObject {

    public FarmPlotObject(int id) {
        super(id);
        ResourceStorageComponent storage = new ResourceStorageComponent(
                Resources.CORN_SEED, Resources.WATER, Resources.CORN);
        storage.setResourceMax(Resources.WATER, 10);
        storage.setResourceMax(Resources.CORN_SEED, 10);
        storage.setResourceMax(Resources.CORN, 10);
        addComponent(storage);

        ResourceConversionComponent conversion = new ResourceConversionComponent(1000);
        conversion.addSourceResource(Resources.CORN_SEED, 1);
        conversion.addSourceResource(Resources.WATER, 1);
        conversion.addProduceResource(Resources.CORN, 1);
        addComponent(conversion);

        init();
    }

    @Override
    public int getType() {
        return GameObjects.Type.FARM_PLOT;
    }
}
