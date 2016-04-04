package cn.neu.vo;

import javafx.beans.property.SimpleStringProperty;

public class JXGoodsVo {
	private SimpleStringProperty id;
	private SimpleStringProperty price;
	private SimpleStringProperty name;
	private SimpleStringProperty count;
	private SimpleStringProperty type;


	@Override
	public String toString() {
		return "GoodsVo [id=" + id + ", price=" + price + ", name=" + name + ", count=" + count + ", type=" + type
				+ "]";
	}

	public JXGoodsVo(String id, String price, String name, String count, String type) {
		super();
		this.id = new SimpleStringProperty(id);
		this.price = new SimpleStringProperty(price);
		this.name = new SimpleStringProperty(name);
		this.count = new SimpleStringProperty(count);
		this.type = new SimpleStringProperty(type);
	}

	public final SimpleStringProperty idProperty() {
		return this.id;
	}


	public final String getId() {
		return this.idProperty().get();
	}


	public final void setId(final String id) {
		this.idProperty().set(id);
	}


	public final SimpleStringProperty priceProperty() {
		return this.price;
	}


	public final String getPrice() {
		return this.priceProperty().get();
	}


	public final void setPrice(final String price) {
		this.priceProperty().set(price);
	}


	public final SimpleStringProperty nameProperty() {
		return this.name;
	}


	public final String getName() {
		return this.nameProperty().get();
	}


	public final void setName(final String name) {
		this.nameProperty().set(name);
	}


	public final SimpleStringProperty countProperty() {
		return this.count;
	}


	public final String getCount() {
		return this.countProperty().get();
	}


	public final void setCount(final String count) {
		this.countProperty().set(count);
	}


	public final SimpleStringProperty typeProperty() {
		return this.type;
	}


	public final String getType() {
		return this.typeProperty().get();
	}


	public final void setType(final String type) {
		this.typeProperty().set(type);
	}


}
