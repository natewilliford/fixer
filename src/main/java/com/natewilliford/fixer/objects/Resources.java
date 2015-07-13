package com.natewilliford.fixer.objects;

class Resources {

    public static final int WATER = 1;
    public static final int CORN_SEED = 2;
    public static final int CORN = 3;

    public static final Integer[] ALL = { WATER, CORN_SEED, CORN };

    static String getResourceName(int resource) {
        switch (resource) {
            case WATER:
                return "Water";
            case CORN_SEED:
                return "Corn Seed";
            case CORN:
                return "Corn";
            default:
                return "Unknown";
        }
    }
}
