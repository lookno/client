package cn.neu.client;

import cn.neu.global.Container;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle("登录");
			stage.setScene(scene);
			stage.show();
			Container.root = root;
			Container.scene = scene;
			Container.stage = stage;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
