package cn.neu.client;

import com.google.gson.Gson;
import cn.neu.global.Container;
import cn.neu.http.Http;
import cn.neu.recv.Goods;
import cn.neu.recv.GoodsVo;
import cn.neu.recv.Record;
import cn.neu.recv.RecordVo;
import cn.neu.util.MapToGoods;
import cn.neu.util.MapToRecords;
import cn.neu.vo.JXGoodsVo;
import cn.neu.vo.JXRecordsVo;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DataController {
	@FXML
	private TitledPane goodsManage;
	@FXML
	private TitledPane recordManage;
	@FXML
	private TitledPane dataManage;
	@FXML
	private TitledPane userManage;
	@FXML
	private Button listGoodsButton;
	@FXML
	private Button addGoodsButton;
	@FXML
	private Button listRecordsButton;
	@FXML
	private Button addRecordsButton;
	@FXML
	private Button listProfitsButton;
	@FXML
	private Button inputDataButton;
	@FXML
	private Button outputDataButton;
	@FXML
	private Button changePassButton;
	@FXML
	private Button registerUserButton;
	@FXML
	private Button logoutButton;
	@FXML
	private TableView<JXGoodsVo> goodsTable;
	@FXML
	private TableView<JXRecordsVo> recordsTable;
	@FXML
	private Pane listView;
	@FXML
	private Pane addView;
	@FXML
	private Pane addGoods;
	@FXML
	private Button ConfirmAddGoodsButton;
	@FXML
	private Button CancelAddGoodsButton;
	@FXML
	private TextField addGoodsName;
	@SuppressWarnings("rawtypes")
	@FXML
	private ChoiceBox addGoodsType;
	@FXML
	private TextField addGoodsCount;
	@FXML
	private TextField addGoodsPrice;
	@FXML
	private Pane addRecords;
	@FXML
	private TextField addRecordsComment;
	@FXML
	private TextField addRecordsPrice;
	@FXML
	private Button ConfirmAddRecordsButton;
	@FXML
	private Button CancelAddRecordsButton;
	@FXML
	private Pagination pagination;
	@FXML
	private Button inoutButton;
	@SuppressWarnings("rawtypes")
	@FXML
	private ChoiceBox goodsChoice;
	@SuppressWarnings("rawtypes")
	@FXML
	private ChoiceBox recordsChoice;
	@FXML
	private Pane profitView;
	@FXML
	private DatePicker startTime;
	@FXML
	private DatePicker endTime;

	@FXML
	void searchProfitButtonOnMouseClicked(MouseEvent me){
		//System.out.println();startTime.getValue()
	}
	@FXML
	void listProfitsButtononMouseClicked(MouseEvent me){
		addView.setVisible(false);
		listView.setVisible(false);
		profitView.setVisible(true);
	}
	@FXML
	void inoutButtononMouseClicked() throws Exception {
		Container.dataController = this;
		Pane root = FXMLLoader.load(getClass().getResource("inout.fxml"));
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setTitle("出入库");
		stage.setScene(scene);
		stage.show();
		Container.inoutStage = stage;
		String responseBody = null;

		responseBody = Http.getConnect("http://localhost:8080/storage/goods/list?id=" + Container.goodsIdOfInout,
				Container.token);

		if (Http.CODE == 200) {
			GoodsVo gv = new Gson().fromJson(responseBody, GoodsVo.class);
			((TextField) root.getChildren().get(1)).setText(gv.getGoods().get(0).getName());
			((TextField) root.getChildren().get(2)).setText(gv.getGoods().get(0).getCount() + "");
		} else {

		}

	}

	@FXML
	void changePriceButtononMouseClicked() throws Exception {
		Container.dataController = this;
		Pane root = FXMLLoader.load(getClass().getResource("price.fxml"));
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setTitle("修改价格");
		stage.setScene(scene);
		stage.show();
		Container.inoutStage = stage;
		String responseBody = null;

		responseBody = Http.getConnect("http://localhost:8080/storage/goods/list?id=" + Container.goodsIdOfInout,
				Container.token);

		if (Http.CODE == 200) {
			GoodsVo gv = new Gson().fromJson(responseBody, GoodsVo.class);
			((TextField) root.getChildren().get(1)).setText(gv.getGoods().get(0).getName());
			((TextField) root.getChildren().get(3)).setText(gv.getGoods().get(0).getPrice() + "");
		} else {

		}

	}

	@FXML
	void listGoodsButtonOnMouseClicked(Event event) throws Exception {
		goodsChoice.setVisible(true);
		recordsChoice.setVisible(false);
		goodsChoice.setItems(FXCollections.observableArrayList("所有商品", "生产的商品", "购买的商品"));
		goodsChoice.getSelectionModel().selectedIndexProperty()
				.addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
					Container.choiceType = newVal.intValue();
					String responseBody = null;
					try {
						if (newVal.intValue() == 1 || newVal.intValue() == 2) {
							responseBody = Http.getConnect(
									"http://localhost:8080/storage/goods/list?type=" + (newVal.intValue()),
									Container.token);

						} else {
							responseBody = Http.getConnect("http://localhost:8080/storage/goods/list", Container.token);
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (Http.CODE == 200) {
						// 成功
						GoodsVo gv = new Gson().fromJson(responseBody, GoodsVo.class);
						pagination.setPageCount(gv.getCount() % 20 == 0 ? gv.getCount() / 20 : gv.getCount() / 20 + 1);
						((TableColumn) goodsTable.getColumns().get(0))
								.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("id"));
						((TableColumn) goodsTable.getColumns().get(1))
								.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("name"));
						((TableColumn) goodsTable.getColumns().get(2))
								.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("price"));
						((TableColumn) goodsTable.getColumns().get(3))
								.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("count"));
						((TableColumn) goodsTable.getColumns().get(4))
								.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("type"));
						goodsTable.setItems(FXCollections.observableArrayList(MapToGoods.to(gv.getGoods())));
						listView.setVisible(true);
						addView.setVisible(false);
						profitView.setVisible(false);
						goodsTable.setVisible(true);
						recordsTable.setVisible(false);
					} else {
						// 失败
						// message.setText((String) new
						// Gson().fromJson(responseBody,
						// Map.class).get("msg"));
						// message.setVisible(true);
					}
				});
		pagination.setPageFactory((Integer i) -> createGoodsPage(i));
	}

	@FXML
	void listRecordsButtonOnMouseClicked(Event event) throws Exception {
		goodsChoice.setVisible(false);
		recordsChoice.setVisible(true);
		recordsChoice.setItems(FXCollections.observableArrayList("所有类型","销售出库类型", "花销类型", "生产入库类型", "修改商品价格类型 "));
		recordsChoice.getSelectionModel().selectedIndexProperty()
				.addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
					Container.choiceType = newVal.intValue();
					String responseBody = null;
					try {
						if (newVal.intValue() == 1 || newVal.intValue() == 2 || newVal.intValue() == 3
								|| newVal.intValue() == 4) {
							responseBody = Http.getConnect(
									"http://localhost:8080/storage/record/list?type=" + newVal.intValue(),
									Container.token);
						} else {
							responseBody = Http.getConnect("http://localhost:8080/storage/record/list",
									Container.token);
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (Http.CODE == 200) {
						// 成功
						RecordVo rv = new Gson().fromJson(responseBody, RecordVo.class);
						pagination.setPageCount(rv.getCount() % 20 == 0 ? rv.getCount() / 20 : rv.getCount() / 20 + 1);
						((TableColumn) recordsTable.getColumns().get(0))
								.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("id"));
						((TableColumn) recordsTable.getColumns().get(1))
								.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("type"));
						((TableColumn) recordsTable.getColumns().get(2))
								.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("comment"));
						((TableColumn) recordsTable.getColumns().get(3))
								.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("price"));
						((TableColumn) recordsTable.getColumns().get(4))
								.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("create_time"));
						((TableColumn) recordsTable.getColumns().get(5))
								.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("update_time"));
						((TableColumn) recordsTable.getColumns().get(6))
								.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("goods_name"));
						recordsTable.setItems(FXCollections.observableArrayList(MapToRecords.to(rv.getRecords())));
						listView.setVisible(true);
						addView.setVisible(false);
						profitView.setVisible(false);
						goodsTable.setVisible(false);
						recordsTable.setVisible(true);
					} else {
						// 失败
						// message.setText((String) new
						// Gson().fromJson(responseBody,
						// Map.class).get("msg"));
						// message.setVisible(true);
					}
				});

		pagination.setPageFactory((Integer i) -> createRecordsPage(i));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Node createRecordsPage(Integer i) {
		String responseBody = null;
		try {
			if (Container.choiceType == 1 || Container.choiceType == 2 || Container.choiceType == 3
					|| Container.choiceType == 4) {
				responseBody = Http.getConnect(
						"http://localhost:8080/storage/record/list?type=" + Container.choiceType + "&page=" + (i + 1),
						Container.token);
			} else {
				responseBody = Http.getConnect("http://localhost:8080/storage/record/list?page=" + (i + 1),
						Container.token);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (Http.CODE == 200) {
			// 成功
			RecordVo rv = new Gson().fromJson(responseBody, RecordVo.class);
			pagination.setPageCount(rv.getCount() % 20 == 0 ? rv.getCount() / 20 : rv.getCount() / 20 + 1);
			((TableColumn) recordsTable.getColumns().get(0))
					.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("id"));
			((TableColumn) recordsTable.getColumns().get(1))
					.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("type"));
			((TableColumn) recordsTable.getColumns().get(2))
					.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("comment"));
			((TableColumn) recordsTable.getColumns().get(3))
					.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("price"));
			((TableColumn) recordsTable.getColumns().get(4))
					.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("create_time"));
			((TableColumn) recordsTable.getColumns().get(5))
					.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("update_time"));
			((TableColumn) recordsTable.getColumns().get(6))
					.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("goods_name"));
			recordsTable.setItems(FXCollections.observableArrayList(MapToRecords.to(rv.getRecords())));
			listView.setVisible(true);
			addView.setVisible(false);
			profitView.setVisible(false);
			goodsTable.setVisible(false);
			recordsTable.setVisible(true);
		} else {
			// 失败
			// message.setText((String) new Gson().fromJson(responseBody,
			// Map.class).get("msg"));
			// message.setVisible(true);
		}

		Node n = new Button();
		n.setVisible(false);
		return n;
	}

	@FXML
	void goodsRowOnMouseClicked(MouseEvent e) {
		String target = e.getTarget().toString();
		if (target.endsWith("'")) {
			String id = target.substring(target.indexOf('\'') + 1, target.lastIndexOf('\''));
			Container.goodsIdOfInout = id;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Node createGoodsPage(Integer i) {
		String responseBody = null;
		try {
			if (Container.choiceType == 1 || Container.choiceType == 2) {
				responseBody = Http.getConnect(
						"http://localhost:8080/storage/goods/list?type=" + (Container.choiceType) + "&page=" + (i + 1),
						Container.token);

			} else {
				responseBody = Http.getConnect("http://localhost:8080/storage/goods/list?page=" + (i + 1),
						Container.token);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (Http.CODE == 200) {
			// 成功
			GoodsVo gv = new Gson().fromJson(responseBody, GoodsVo.class);
			pagination.setPageCount(gv.getCount() % 20 == 0 ? gv.getCount() / 20 : gv.getCount() / 20 + 1);
			((TableColumn) goodsTable.getColumns().get(0))
					.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("id"));
			((TableColumn) goodsTable.getColumns().get(1))
					.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("name"));
			((TableColumn) goodsTable.getColumns().get(2))
					.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("price"));
			((TableColumn) goodsTable.getColumns().get(3))
					.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("count"));
			((TableColumn) goodsTable.getColumns().get(4))
					.setCellValueFactory(new PropertyValueFactory<JXGoodsVo, String>("type"));
			goodsTable.setItems(FXCollections.observableArrayList(MapToGoods.to(gv.getGoods())));
			listView.setVisible(true);
			addView.setVisible(false);
			profitView.setVisible(false);
			goodsTable.setVisible(true);
			recordsTable.setVisible(false);
		} else {
			// 失败
			// message.setText((String) new Gson().fromJson(responseBody,
			// Map.class).get("msg"));
			// message.setVisible(true);
		}

		Node n = new Button();
		n.setVisible(false);
		return n;
	}

	@SuppressWarnings("unchecked")
	@FXML
	void addGoodsButtonOnMouseClicked(Event e) throws Exception {
		addGoodsType.setItems(FXCollections.observableArrayList("生产的商品", "购买的商品"));
		listView.setVisible(false);
		addView.setVisible(true);
		addGoods.setVisible(true);
		addRecords.setVisible(false);
	}

	@SuppressWarnings("unused")
	@FXML
	void ConfirmAddGoodsButtonOnMouseClicked(Event e) throws Exception {
		String name = addGoodsName.getText();
		int type = addGoodsType.getSelectionModel().getSelectedIndex() + 1;
		String price = addGoodsPrice.getText();
		String count = addGoodsCount.getText();
		Goods goods = new Goods();
		goods.setName(name);
		goods.setType(type);
		goods.setPrice(Double.parseDouble(price));
		goods.setCount(Integer.parseInt(count));

		String responseBody = Http.postConnect("http://localhost:8080/storage/goods/add", Container.token,
				new Gson().toJson(goods));
		if (Http.CODE == 200) {
			listGoodsButtonOnMouseClicked(e);
		} else {
			// 失败
		}

		listGoodsButtonOnMouseClicked(e);
	}

	@FXML
	void CancelAddGoodsButtonOnMouseClicked(Event e) throws Exception {
		listGoodsButtonOnMouseClicked(e);
	}

	@SuppressWarnings("unused")
	@FXML
	void ConfirmAddRecordsButtonOnMouseClicked(Event e) throws Exception {
		Record record = new Record();
		record.setComment(addRecordsComment.getText());
		record.setType(2);
		record.setPrice(Double.parseDouble(addRecordsPrice.getText()));

		String responseBody = Http.postConnect("http://localhost:8080/storage/record/add", Container.token,
				new Gson().toJson(record));
		if (Http.CODE == 200) {
			listGoodsButtonOnMouseClicked(e);
		} else {
			// 失败
		}

		listRecordsButtonOnMouseClicked(e);
	}

	@FXML
	void CancelAddRecordsButtonOnMouseClicked(Event e) throws Exception {
		listRecordsButtonOnMouseClicked(e);
	}

	@FXML
	void addRecordsButtonOnMouseClicked(Event e) {
		listView.setVisible(false);
		addView.setVisible(true);
		addRecords.setVisible(true);
		addGoods.setVisible(false);
	}

	@FXML
	void logout(Event e) {
		System.exit(0);
	}
}
