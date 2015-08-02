package com.natewilliford.fixer.api;

import com.natewilliford.fixer.Main;
import com.natewilliford.fixer.db.MarketOrdersTable;
import com.natewilliford.fixer.objects.Market;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.sql.Connection;
import java.sql.ResultSet;

public class MarketOrdersServerResource extends ServerResource {

    @Get("application/json")
    public String getJson() {
        try {
            int resourceType = Integer.parseInt(getAttribute("resourceType"));
            int orderType = Integer.parseInt(getAttribute("orderType"));
            ResultSet results = MarketOrdersTable.getOpenOrders(
                    Main.database.getConnection(), resourceType, orderType);
            JSONObject jsonResponse = new JSONObject();
            while(results.next()) {
                Market.Order order = Market.Order.fromResultSet(results);
                jsonResponse.append("orders", order.toJson());
            }
            return jsonResponse.toString();
        } catch (Exception e) {
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
        return "";
    }
}
