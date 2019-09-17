package com.online.electronic.shopping.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name="product")
public class Product implements Serializable {

	private static final long serialVersionUID = -8627352849931989521L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="product_id", nullable=false)
    private Long id;

    @Column(name="product_name", nullable=false)
    private String productName;


    @Column(name="category", nullable=false)
    private String category;
    
    @Column(name="price", nullable=false)
    private Long price;   
 
    @Column(name="stock_number", nullable=false)
    private Long stokeNumber;  
    
    @Column(name="remark", nullable=false)
    private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getStokeNumber() {
		return stokeNumber;
	}

	public void setStokeNumber(Long stokeNumber) {
		this.stokeNumber = stokeNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
