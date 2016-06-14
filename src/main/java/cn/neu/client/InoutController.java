package cn.neu.client;


import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import cn.neu.global.Container;
import cn.neu.http.Http;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class InoutController{
	@FXML
	private TextField goodsName;
	@FXML
	private TextField goodsTotalCount;
	@FXML
	private TextField goodsOperCount;
	@FXML
	private Button in;
	@FXML
	private Button out;
	@FXML
	private Button cancel;
	@SuppressWarnings("unused")
	@FXML
	void outOnMouseClicked(MouseEvent e) throws Exception{
		String responseBody = null;
		Map<String, String> map = new HashMap<>();
		map.put("id", Container.goodsIdOfInout);
		map.put("count", (-Integer.parseInt(goodsOperCount.getText()))+"");
		System.out.println(map+" 1");
		responseBody = Http.postConnect("http://localhost:8080/storage/goods/modify",
				Container.token,new Gson().toJson(map));

		if (Http.CODE == 200) {
			Container.inoutStage.close();
			Container.dataController.listGoodsButtonOnMouseClicked(null);
		} else {

		}
	}
	@SuppressWarnings("unused")
	@FXML
	void inOnMouseClicked(MouseEvent e) throws Exception{
		String responseBody = null;
		Map<String, String> map = new HashMap<>();
		map.put("id", Container.goodsIdOfInout);
		map.put("count", (Integer.parseInt(goodsOperCount.getText()))+"");
		responseBody = Http.postConnect("http://localhost:8080/storage/goods/modify",
				Container.token,new Gson().toJson(map));

		if (Http.CODE == 200) {
			Container.inoutStage.close();
			Container.dataController.listGoodsButtonOnMouseClicked(null);
		} else {

		}
	}
	@FXML
	void cancelOnMouseClicked(MouseEvent e){
		Container.inoutStage.close();
	}
}
