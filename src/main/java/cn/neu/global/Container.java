package cn.neu.global;

import cn.neu.client.DataController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Container {
	public static Stage stage;
	public static Scene scene;
	public static Pane root;
	public static String username;
	public static String email;
	public static String token;


	/*********出入库有关***********/
	public static String goodsIdOfInout;

	public static Stage inoutStage;

	public static DataController dataController;
	/********************/

	/*********list筛选条件***********/
	public static int choiceType;
	/********************/
}
