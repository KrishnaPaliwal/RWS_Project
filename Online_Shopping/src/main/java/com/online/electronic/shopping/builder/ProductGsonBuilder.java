package com.online.electronic.shopping.builder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.online.electronic.shopping.model.Product;
import com.online.electronic.shopping.serializer.ProductSerializer;

public class ProductGsonBuilder {

	public static Gson build()
	{
	    GsonBuilder builder = new GsonBuilder()
	            .setPrettyPrinting()
	            .registerTypeAdapter(Product.class, new ProductSerializer());
	        Gson gson = builder.create();
	        return gson;
	}
}
