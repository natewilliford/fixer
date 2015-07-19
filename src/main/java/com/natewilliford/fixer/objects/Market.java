package com.natewilliford.fixer.objects;

import java.util.ArrayList;
import java.util.List;

public class Market extends GameObject {

//    private final List<Order> orders = new ArrayList<>();

    Market(long id) {
        super(id);
    }

    @Override
    int getType() {
        return GameObjects.Type.MARKET;
    }

//    long placeOrder(int orderType, long userId, int resourceType, long pricePerUnit) {
//
//    }

//    public static final class Order {
//        public static final int BUY = 1;
//        public static final int SELL = 2;
//
//        public int orderType;
//        public long userId;
//        public int resourceType;
//        public long pricePerUnit;
//    }
}
