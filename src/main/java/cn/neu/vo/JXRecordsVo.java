package cn.neu.vo;

import javafx.beans.property.SimpleStringProperty;

public class JXRecordsVo {
	private SimpleStringProperty id;
	private SimpleStringProperty goods_name;
	private SimpleStringProperty create_time;
	private SimpleStringProperty update_time;
	private SimpleStringProperty comment;
	private SimpleStringProperty price;
	private SimpleStringProperty type;

	public final SimpleStringProperty idProperty() {
		return this.id;
	}

	public final String getId() {
		return this.idProperty().get();
	}

	public final void setId(final String id) {
		this.idProperty().set(id);
	}

	public final SimpleStringProperty goods_nameProperty() {
		return this.goods_name;
	}

	public final String getGoods_name() {
		return this.goods_nameProperty().get();
	}

	public final void setGoods_name(final String goods_name) {
		this.goods_nameProperty().set(goods_name);
	}

	public final SimpleStringProperty create_timeProperty() {
		return this.create_time;
	}

	public final String getCreate_time() {
		return this.create_timeProperty().get();
	}

	public final void setCreate_time(final String create_time) {
		this.create_timeProperty().set(create_time);
	}

	public final SimpleStringProperty update_timeProperty() {
		return this.update_time;
	}

	public final String getUpdate_time() {
		return this.update_timeProperty().get();
	}

	public final void setUpdate_time(final String update_time) {
		this.update_timeProperty().set(update_time);
	}

	public final SimpleStringProperty commentProperty() {
		return this.comment;
	}

	public final String getComment() {
		return this.commentProperty().get();
	}

	public final void setComment(final String comment) {
		this.commentProperty().set(comment);
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

	public final SimpleStringProperty typeProperty() {
		return this.type;
	}

	public final String getType() {
		return this.typeProperty().get();
	}

	public final void setType(final String type) {
		this.typeProperty().set(type);
	}

	public JXRecordsVo(String id, String goods_name, String create_time, String update_time, String comment,
			String price, String type) {
		super();
		this.id = new SimpleStringProperty(id);
		this.goods_name = new SimpleStringProperty(goods_name);
		this.create_time = new SimpleStringProperty(create_time);
		this.update_time = new SimpleStringProperty(update_time);
		this.comment = new SimpleStringProperty(comment);
		this.price = new SimpleStringProperty(price);
		this.type = new SimpleStringProperty(type);
	}

}
