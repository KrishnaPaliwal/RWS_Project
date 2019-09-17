package com.online.electronic.shopping.serializer;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.online.electronic.shopping.model.Product;

public class ProductSerializer implements JsonSerializer<Product> {

	public JsonElement serialize(Product prod, Type arg1, JsonSerializationContext arg2) {

		JsonObject obj = new JsonObject();

		obj.addProperty("product_id", prod.getId());
        obj.addProperty("product_name", prod.getProductName());
        obj.addProperty("category", prod.getCategory());
        obj.addProperty("price", prod.getPrice());
        obj.addProperty("stock_number", prod.getStokeNumber());
        obj.addProperty("remark", prod.getRemark());

        return obj;
	}

}
