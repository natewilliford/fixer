package com.natewilliford.fixer.objects;

import com.natewilliford.fixer.db.MarketOrdersTable;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Market {


    Market() {}

//    long placeOrder(int orderType, long userId, int resourceType, long pricePerUnit) {
//
//    }

    public static final class Order implements Jsonizable {

        public static final int BUY = 1;
        public static final int SELL = 2;

        public int id;
        public int orderType;
        public int userId;
        public int resourceType;
        public long pricePerUnit;
        public boolean filled;

        @Override
        public JSONObject toJson() {
            JSONObject json = new JSONObject();
            json.put(MarketOrdersTable.COL_ID, id);
            json.put(MarketOrdersTable.COL_ORDER_TYPE, orderType);
            json.put(MarketOrdersTable.COL_USER_ID, userId);
            json.put(MarketOrdersTable.COL_RESOURCE_TYPE, resourceType);
            json.put(MarketOrdersTable.COL_PRICE, pricePerUnit);
            json.put(MarketOrdersTable.COL_FILLED, filled);
            return json;
        }

        public static Order fromResultSet (ResultSet resultSet) throws SQLException {
            Order order = new Order();
            order.id = resultSet.getInt(MarketOrdersTable.COL_ID);
            order.orderType = resultSet.getInt(MarketOrdersTable.COL_ORDER_TYPE);
            order.userId = resultSet.getInt(MarketOrdersTable.COL_USER_ID);
            order.resourceType = resultSet.getInt(MarketOrdersTable.COL_RESOURCE_TYPE);
            order.pricePerUnit = resultSet.getInt(MarketOrdersTable.COL_PRICE);
            order.filled = resultSet.getBoolean(MarketOrdersTable.COL_FILLED);
            return order;
        }
    }
}
