package com.online.electronic.shopping.buyer;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.online.electronic.shopping.builder.ProductGsonBuilder;
import com.online.electronic.shopping.dao.BuyerDao;
import com.online.electronic.shopping.model.Product;

@Path("buyer")
public class BuyerAPI {

	private BuyerDao buyerDao = new BuyerDao();
	Gson gson = ProductGsonBuilder.build();

	@GET
	@Path("listprodbycategory")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProductsByCategory(@QueryParam("category") String category, @HeaderParam("Authorization") String authString) {
		
		Response rsResponse = null;
		
		// Basic Authentication
		if(isUserAuthenticated(authString)) {

		List<Product> prod = buyerDao.getProductsByCategory(category);
		if (prod != null) {
			String response = gson.toJson(prod);
			rsResponse =  Response.ok(response).build();
		} else {
			rsResponse =  Response.status(Response.Status.NOT_FOUND).build();
		}
		}
		else {
			String authfailed = "Authentication Failed";
			rsResponse = Response.status(Response.Status.BAD_REQUEST).entity(authfailed.toString()).build();
		}
		return rsResponse;
	
	}

	private boolean isUserAuthenticated(String authString) {
		if(!authString.equals("Basic dXNlcm5hbWU6cGFzc3dvcmQ=")) return false;
		String authToken = authString.substring(6);
		byte[] decodedAuth = Base64.decodeBase64(authToken.getBytes());
		String auth = new String(decodedAuth);
		String[] token =  auth.split(":");
		String username= token[0];
		String password= token[1];
		if(!username.equals("username") || !password.equals("password"))
		{
			return false;
		}
		return true;		
		
	}


	@GET
	@Path("listprodbyrange")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProductsByPriceRange(@QueryParam("min") String min, @QueryParam("max") String max) {

		List<Product> prod = buyerDao.getProductsByPriceRange(min, max);
		if (prod != null) {
			String response = gson.toJson(prod);
			return Response.ok(response).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	
	}
	
	@GET
	@Path("getallprod")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProduct() {

		List<Product> prod = buyerDao.getAllProd();
		if (prod != null) {
			String response = gson.toJson(prod);
			return Response.ok(response).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
}
