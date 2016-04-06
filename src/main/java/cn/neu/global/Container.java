package cn.neu.global;

import cn.neu.client.DataController;
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

	/*********导出数据***********/
	public static int type1;//record or goods
	public static int type2;// 对应上面
	public static String outFileName;
	/********************/


	/*********注册用户***********/
	public static int userType;
	/********************/
}
