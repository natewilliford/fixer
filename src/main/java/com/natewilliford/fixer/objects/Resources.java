package com.natewilliford.fixer.objects;

class Resources {

    public static final int GOLD = 1;
    public static final int WATER = 2;
    public static final int CORN_SEED = 3;
    public static final int CORN = 4;

    public static final Integer[] ALL = { WATER, CORN_SEED, CORN };

    static String getResourceName(int resource) {
        switch (resource) {
            case GOLD:
                return "Gold";
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
