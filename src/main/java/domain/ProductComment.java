package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@Entity
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "comment.byId", query = "SELECT c FROM ProductComment c where c.id=:commentId"),
		@NamedQuery(name = "comment.byProductId", query = "SELECT c FROM ProductComment c, Product p where p.id=:productId")
})
public class ProductComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String content;

	private LocalDateTime date;

	@ManyToOne(cascade = CascadeType.ALL)
	private Product product;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
}
