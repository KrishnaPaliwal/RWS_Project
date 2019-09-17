package com.online.electronic.shopping.sellerapi;

import java.io.StringReader;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.google.gson.Gson;
import com.online.electronic.shopping.builder.ProductGsonBuilder;
import com.online.electronic.shopping.dao.SellerDao;
import com.online.electronic.shopping.model.Product;

@Path("seller")
public class SellerAPI {

	private SellerDao sellerDao = new SellerDao();
	Gson gson = ProductGsonBuilder.build();

	@POST
	@Path("addprod")
	@Consumes(MediaType.APPLICATION_XML)
	public Response addProduct(String prodObj) throws JAXBException {

		JAXBContext context = JAXBContext.newInstance(Product.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		StringReader reader = new StringReader(prodObj);

		Product prod = (Product) unmarshaller.unmarshal(reader);
		
		if (sellerDao.create(prod)) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@PUT
	@Path("updateprod/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response updateProduct(@PathParam("id") Long id, String prodObj) throws JAXBException {

		JAXBContext context = JAXBContext.newInstance(Product.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		StringReader reader = new StringReader(prodObj);

		Product prod = (Product) unmarshaller.unmarshal(reader);

		prod.setId(id);

		if (sellerDao.update(prod)) {
			return Response.status(Response.Status.OK).build();

		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	// Cache implementation 
	
	@GET
	@Path("getprod/{prodid}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getProductById(@PathParam("prodid") String id) {

		Product prod = sellerDao.get(Long.parseLong(id));
		if (prod != null) {
			
			CacheControl cache = new CacheControl();
			cache.setMaxAge(50000);
			cache.setPrivate(false);
			ResponseBuilder builder = Response.ok(prod);
			builder.cacheControl(cache);		
			return builder.build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@DELETE
	@Path("deleteprod")
	public Response delete(@QueryParam("id") Long id) {
		Product prod = sellerDao.get(id);

		if (prod != null && sellerDao.delete(prod)) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@PUT
	@Path("getproductsbyids")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProductsForSelectedIds(String ids) {
		String [] idArraay = gson.fromJson(ids, String[].class);		
		List<Product> prod = sellerDao.getProductsForSelectedIds(idArraay);
		if (prod != null) {
			String response = gson.toJson(prod);
			return Response.ok(response).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		
	}	
}
