package cn.neu.client;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.http.HttpStatus;

import com.google.gson.Gson;
import cn.neu.global.Container;
import cn.neu.http.Http;
import cn.neu.recv.ChgPwdDto;
import cn.neu.recv.Goods;
import cn.neu.recv.GoodsVo;
import cn.neu.recv.ProfitVo;
import cn.neu.recv.Record;
import cn.neu.recv.RecordVo;
import cn.neu.recv.User;
import cn.neu.util.CipherUtil;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
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
	private TextField inCome;
	@FXML
	private TextField outCome;
	@FXML
	private TextField in_outCome;
	@FXML
	private Pane exceldataView;
	@SuppressWarnings("rawtypes")
	@FXML
	private ChoiceBox outputChoiceBox1;
	@SuppressWarnings("rawtypes")
	@FXML
	private ChoiceBox outputChoiceBox2;
	@FXML
	private TextField outputFileName;
	@FXML
	private Text filePosition;
	@FXML
	private Text fileTip;

	@FXML
	private Pane userManageView;
	@FXML

	private Pane changePassPane;
	@FXML
	private PasswordField oldPass;
	@FXML
	private PasswordField newPass;
	@FXML
	private PasswordField newPass2;
	@FXML
	private Text changePassTip;
	@FXML
	private Text oldPassTip;
	@FXML
	private Text newPassTip;
	@FXML
	private Text newPassTip2;

	@FXML
	private Pane registerPane;
	@FXML
	private TextField rUserName;
	@FXML
	private PasswordField rPassword;
	@FXML
	private TextField rEmail;
	@FXML
	private TextField rPhone;
	@FXML
	private Text rUserNameTip;
	@FXML
	private Text rPasswordTip;
	@FXML
	private Text rEmailTip;
	@FXML
	private Text rPhoneTip;
	@FXML
	private Text rRegTip;
	@SuppressWarnings("rawtypes")
	@FXML
	private ChoiceBox rUserType;
	@FXML
	private Text rTypeTip;
	@FXML
	void confirmRegisterButtonOnMouseClicked() throws Exception {
		String rname = rUserName.getText();
		String rpass = CipherUtil.generatePassword(rPassword.getText());
		String remail = rEmail.getText();
		String rphone = rPhone.getText();
		User user = new User();
		user.setUsername(rname);
		user.setPassword(rpass);
		user.setEmail(remail);
		user.setPhone(rphone);
		user.setPermission(Container.userType+2);
		String responseBody = null;
		responseBody = Http.postConnect("http://localhost:8080/storage/user/register", Container.token,
				new Gson().toJson(user));

		if(Http.CODE == 200){
			rRegTip.setText("注册成功");
			rRegTip.setVisible(true);
		} else if(Http.CODE ==400){
			Map<String, Object> map = new Gson().fromJson(responseBody, Map.class);
			String pos = (String) map.get("pos");
			if (pos.equals("1")) {
				rUserNameTip.setText((String) map.get("msg"));
				rUserNameTip.setVisible(true);
			} else if (pos.equals("2")) {
				rPasswordTip.setText((String) map.get("msg"));
				rPasswordTip.setVisible(true);
			} else if (pos.equals("3")) {
				rTypeTip.setText((String) map.get("msg"));
				rTypeTip.setVisible(true);
			}else if (pos.equals("4")) {
				rEmailTip.setText((String) map.get("msg"));
				rEmailTip.setVisible(true);
			}else if (pos.equals("5")) {
				rPhoneTip.setText((String) map.get("msg"));
				rPhoneTip.setVisible(true);
			}
		}
	}

	@FXML
	void cancelRegisterButtonOnMouseClicked() {
		userManageView.setVisible(false);
	}

	@FXML
	void registerButtonOnMouseClicked() {
		exceldataView.setVisible(false);
		addView.setVisible(false);
		listView.setVisible(false);
		profitView.setVisible(false);
		userManageView.setVisible(true);
		changePassPane.setVisible(false);
		registerPane.setVisible(true);
		rUserNameTip.setVisible(false);
		rPasswordTip.setVisible(false);
		rTypeTip.setVisible(false);
		rEmailTip.setVisible(false);
		rPhoneTip.setVisible(false);
		rRegTip.setVisible(false);
		rUserName.clear();
		rPassword.clear();
		rEmail.clear();
		rPhone.clear();

		rUserType.setItems(FXCollections.observableArrayList("管理员(可读可写)","普通用户(只读)"));
		rUserType.getSelectionModel().selectedIndexProperty().addListener(
				(ObservableValue<? extends Number> ov1, Number oldVal, Number newVal) -> {
					System.out.println(newVal.intValue());
					Container.userType = newVal.intValue();
				});
	}

	/*************
	 * change pass
	 *
	 * @throws Exception
	 ***********************/
	@FXML
	void confirmChangePassButtonOnMouseClicked() throws Exception {
		String oldPas = oldPass.getText();
		String newPas = newPass.getText();
		String newPas2 = newPass2.getText();

		if (!newPas.equals(newPas2)) {
			newPassTip.setText("两次输入的新密码不一致");
			newPassTip.setVisible(true);
		}
		String responseBody = null;
		ChgPwdDto c = new ChgPwdDto();
		c.setOldPass(oldPas);
		c.setNewPass(newPas);
		responseBody = Http.postConnect("http://localhost:8080/storage/user/chgpwd", Container.token,
				new Gson().toJson(c));

		if (Http.CODE == 200) {
			System.out.println(new Gson().fromJson(responseBody, Map.class).toString());
			changePassTip.setText("修改密码成功");
			changePassTip.setVisible(true);
			oldPassTip.setVisible(false);
			newPassTip.setVisible(false);
			newPassTip2.setVisible(false);
		} else if (Http.CODE == 400) {
			System.out.println(new Gson().fromJson(responseBody, Map.class).toString());
			Map<String, Object> map = new Gson().fromJson(responseBody, Map.class);
			String pos = (String) map.get("pos");
			if (pos.equals("1")) {
				oldPassTip.setText((String) map.get("msg"));
				oldPassTip.setVisible(true);
			} else if (pos.equals("2")) {
				newPassTip.setText((String) map.get("msg"));
				newPassTip.setVisible(true);
			}
		}

	}

	@FXML
	void cancelPassButtonOnMouseClicked() {
		userManageView.setVisible(false);
	}

	@FXML
	void changePassButtonOnMouseClicked() {
		exceldataView.setVisible(false);
		addView.setVisible(false);
		listView.setVisible(false);
		profitView.setVisible(false);
		userManageView.setVisible(true);
		changePassPane.setVisible(true);
		registerPane.setVisible(false);
		changePassTip.setVisible(false);
		oldPassTip.setVisible(false);
		newPassTip.setVisible(false);
		newPassTip2.setVisible(false);
		oldPass.clear();
		newPass.clear();
		newPass2.clear();
	}

	/*************** change pass ***************/

	@SuppressWarnings("unchecked")
	@FXML
	void outputDataButtonOnMouseClicked(MouseEvent me) {
		exceldataView.setVisible(true);
		filePosition.setVisible(false);
		fileTip.setVisible(false);
		addView.setVisible(false);
		listView.setVisible(false);
		profitView.setVisible(false);
		outputChoiceBox1.setItems(FXCollections.observableArrayList("库存商品", "账务记录"));
		outputChoiceBox1.getSelectionModel().selectedIndexProperty()
				.addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
					System.out.print(newVal.intValue());
					Container.type1 = newVal.intValue();
					if (newVal.intValue() == 0) {
						outputChoiceBox2.setItems(FXCollections.observableArrayList("所有商品", "生产的商品", "购入的商品"));
						outputChoiceBox2.getSelectionModel().selectedIndexProperty().addListener(
								(ObservableValue<? extends Number> ov1, Number oldVal1, Number newVal1) -> {
									Container.type2 = newVal1.intValue();
								});
						outputFileName.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "-库存记录");
						Container.outFileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "-库存记录";

					} else if (newVal.intValue() == 1) {
						outputChoiceBox2.setItems(
								FXCollections.observableArrayList("所有记录", "销售出库记录", "花销记录", "生产入库记录", "修改商品价格记录 "));
						outputChoiceBox2.getSelectionModel().selectedIndexProperty().addListener(
								(ObservableValue<? extends Number> ov1, Number oldVal1, Number newVal1) -> {
									Container.type2 = newVal1.intValue();
								});
						outputFileName.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "-账务记录");
						Container.outFileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "-账务记录";
					}
				});
	}

	@FXML
	void outPositionButtonOnMouseClicked(MouseEvent e) throws Exception {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("选择导出文件位置");
		fileChooser.setInitialFileName(Container.outFileName + ".csv");
		File position = fileChooser.showSaveDialog(Container.stage);
		if (position != null) {
			String originalDir = position.toString();
			String dir = position.toString().replaceAll("\\\\", "%5C");
			System.out.println(dir);
			String responseBody = null;
			if (Container.type1 == 0) {

				responseBody = Http.getConnect(
						"http://localhost:8080/storage/goods/output?type=" + Container.type2 + "&fileAddr=" + dir,
						Container.token);

				if (Http.CODE == 200) {
					fileTip.setVisible(true);
					filePosition.setVisible(true);
					filePosition.setText(originalDir);
				} else {

				}
			} else if (Container.type1 == 1) {
				responseBody = Http.getConnect(
						"http://localhost:8080/storage/record/output?type=" + Container.type2 + "&fileAddr=" + dir,
						Container.token);

				if (Http.CODE == 200) {
					fileTip.setVisible(true);
					filePosition.setVisible(true);
					filePosition.setText(originalDir);
				} else {

				}
			}

		}
	}

	@FXML
	void searchProfitButtonOnMouseClicked(MouseEvent me) throws Exception {
		String s_time = startTime.getValue().toString();
		String e_time = endTime.getValue().toString();
		String responseBody = null;

		responseBody = Http.getConnect(
				"http://localhost:8080/storage/record/profit?s_time=" + s_time + "&e_time=" + e_time, Container.token);

		if (Http.CODE == 200) {
			ProfitVo gv = new Gson().fromJson(responseBody, ProfitVo.class);
			inCome.setText(gv.getEarn() + "");
			outCome.setText(gv.getCost() + "");
			in_outCome.setText((gv.getEarn() - gv.getCost()) + "");
		} else {

		}

	}

	@FXML
	void listProfitsButtononMouseClicked(MouseEvent me) {
		addView.setVisible(false);
		listView.setVisible(false);
		profitView.setVisible(true);
		exceldataView.setVisible(false);
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
						exceldataView.setVisible(false);
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
		recordsChoice.setItems(FXCollections.observableArrayList("所有类型", "销售出库类型", "花销类型", "生产入库类型", "修改商品价格类型 "));
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
						exceldataView.setVisible(false);
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
			exceldataView.setVisible(false);
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
			exceldataView.setVisible(false);
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
		exceldataView.setVisible(false);
		profitView.setVisible(false);
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
		exceldataView.setVisible(false);
		profitView.setVisible(false);
	}

	@FXML
	void logout(Event e) {
		System.exit(0);
	}
}
