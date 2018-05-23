package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Entity
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "product.all", query = "SELECT p FROM Product p"),
		@NamedQuery(name = "product.byPrice", query = "SELECT p FROM Product p where p.price>=:priceFrom and p.price<=:priceTo"),
		@NamedQuery(name = "product.byName", query = "SELECT p FROM Product p where p.name=:productName"),
		@NamedQuery(name = "product.byCategory", query = "SELECT p FROM Product p where p.category=:productCategory"),
		@NamedQuery(name = "product.id", query = "FROM Product p WHERE p.id=:productId")
})

public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private double price;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ProductComment> comments;

	@Enumerated(EnumType.STRING)
	private ProductCategory category;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public List<ProductComment> getComments() {
		return comments;
	}

	public void setComments(List<ProductComment> comments) {
		this.comments = comments;
	}

}
