package rest;

import domain.Product;
import domain.ProductCategory;
import domain.ProductComment;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Path("/parts")
@Stateless
public class ProductResources {

	@PersistenceContext
	private EntityManager em;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getAll() {
		return em.createNamedQuery("product.all", Product.class).getResultList();
	}

	@GET
	@Path("/byPrice")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getByPrice(@QueryParam("from") double from, @QueryParam("to") double to) {
		return em.createNamedQuery("product.byPrice", Product.class)
				.setParameter("priceFrom", from)
				.setParameter("priceTo", to)
				.getResultList();
	}

	@GET
	@Path("/byName")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getByPrice(@QueryParam("name") String name) {
		return em.createNamedQuery("product.byName", Product.class)
				.setParameter("productName", name)
				.getResultList();
	}

	@GET
	@Path("/byCategory")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getByCategory(@QueryParam("category") ProductCategory category) {
		return em.createNamedQuery("product.byCategory", Product.class)
				.setParameter("productCategory", category)
				.getResultList();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(Product product) {
		em.persist(product);
		return Response.ok(product.getId()).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") int id) {
		Product result = em.createNamedQuery("product.id", Product.class)
				.setParameter("productId", id)
				.getSingleResult();
		if (result == null) {
			return Response.status(404).build();
		}
		return Response.ok(result).build();
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int id, Product p) {
		Product result = em.createNamedQuery("product.id", Product.class)
				.setParameter("productId", id)
				.getSingleResult();
		if (result == null) {
			return Response.status(404).build();
		}
		result.setName(p.getName());
		result.setPrice(p.getPrice());
		em.persist(result);
		return Response.ok().build();
	}

	@POST
	@Path("/{id}/comments")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addComment(@PathParam("id") int id, ProductComment comment) {
		comment.setDate(LocalDateTime.now());
		Product product = em.createNamedQuery("product.id", Product.class)
				.setParameter("productId", id)
				.getSingleResult();
		if (product == null) {
			return Response.status(404).build();
		}
		product.getComments().add(comment);
		em.persist(product);
		return Response.ok(product.getId()).build();
	}

	@GET
	@Path("/{id}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductComment> getComments(@PathParam("id") int id) {
		Product product = em.createNamedQuery("product.id", Product.class)
				.setParameter("productId", id)
				.getSingleResult();
		if (product == null) {
			return new ArrayList<>();
		}
		return product.getComments();
	}

	@DELETE
	@Path("/{id}/comments/{commentId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteComment(@PathParam("commentId") long commentId) {
		ProductComment comment = em.createNamedQuery("comment.byId", ProductComment.class)
				.setParameter("commentId", commentId)
				.getSingleResult();
		if (comment == null) {
			return Response.status(404).build();
		}
		em.remove(comment);
		return Response.status(204).build();
	}
}
