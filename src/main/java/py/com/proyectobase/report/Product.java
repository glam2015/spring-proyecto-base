package py.com.proyectobase.report;

public class Product {

	private Long id;
	private String state;
	private String branch;
	private String productLine;
	private String item;
	private Long quantity;
	private Float amount;

	public Product(Long id, String productLine, String item, String state,
			String branch, Long quantity, Float amount) {

		super();
		this.id = id;
		this.state = state;
		this.branch = branch;
		this.productLine = productLine;
		this.item = item;
		this.quantity = quantity;
		this.amount = amount;
	}

	public Product(Long id, String productLine, String item, String state,
			String branch, Long quantity, Float amount, boolean x) {

		super();
		this.id = id;
		this.state = state;
		this.branch = branch;
		this.productLine = productLine;
		this.item = item;
		this.quantity = quantity;
		this.amount = amount;
	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public String getState() {

		return state;
	}

	public void setState(String state) {

		this.state = state;
	}

	public String getBranch() {

		return branch;
	}

	public void setBranch(String branch) {

		this.branch = branch;
	}

	public String getProductLine() {

		return productLine;
	}

	public void setProductLine(String productLine) {

		this.productLine = productLine;
	}

	public String getItem() {

		return item;
	}

	public void setItem(String item) {

		this.item = item;
	}

	public Long getQuantity() {

		return quantity;
	}

	public void setQuantity(Long quantity) {

		this.quantity = quantity;
	}

	public Float getAmount() {

		return amount;
	}

	public void setAmount(Float amount) {

		this.amount = amount;
	}

	public String toString() {

		return item;
	}
}
