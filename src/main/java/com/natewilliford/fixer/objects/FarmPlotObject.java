package com.natewilliford.fixer.objects;

public class FarmPlotObject extends GameObject {

    public FarmPlotObject(long id) {
        super(id);
        ResourceStorageComponent storage = new ResourceStorageComponent(
                Resources.CORN_SEED, Resources.WATER, Resources.CORN);
        addComponent(storage);

        ResourceConversionComponent conversion = new ResourceConversionComponent(100000);
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
