package cn.neu.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import cn.neu.global.Container;
import cn.neu.http.Http;
import cn.neu.util.CipherUtil;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserController {

	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Button loginButton;
	@FXML
	private Text forgetText;
	@FXML
	private Text resend;
	@FXML
	private Text message;
	@FXML
	private TextField username2;
	@FXML
	private TextField email;
	@FXML
	private TextField vcode;
	@FXML
	private PasswordField newpassword;
	@FXML
	private Button verifyButton;
	@FXML
	private Button changeButton;
	@FXML
	private Pane pane1;
	@FXML
	private Pane pane2;
	@FXML
	private Pane pane3;

	@FXML
	void forgetTextOnMouseEntered(MouseEvent e) {
		forgetText.setUnderline(true);
		forgetText.setFill(Color.BLUE);
		forgetText.setCursor(Cursor.HAND);
	}

	@FXML
	void forgetTextOnMouseExited(MouseEvent e) {
		forgetText.setUnderline(false);
		forgetText.setFill(Color.BLACK);
	}

	@FXML
	void forgetTextOnMouseClicked(MouseEvent e) {
		message.setVisible(false);
		pane1.setVisible(false);
		pane2.setVisible(true);
	}

	@FXML
	void resendOnMouseEntered(MouseEvent e) {
		resend.setUnderline(true);
		resend.setFill(Color.BLUE);
		resend.setCursor(Cursor.HAND);
	}

	@FXML
	void resendOnMouseExited(MouseEvent e) {
		resend.setUnderline(false);
		resend.setFill(Color.BLACK);
	}

	@FXML
	void resendOnMouseClicked(MouseEvent e) {
		String uname = Container.username;
		String umail = Container.email;
		Map<String, String> map1 = new HashMap<>();
		map1.put("username", uname);
		map1.put("email", umail);
		message.setText("验证码已重新发送");
		message.setVisible(true);
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Http.postConnect("http://localhost:8080/storage/user/findpwd", null, new Gson().toJson(map1));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
	}

	@FXML
	void clickLoginButton(Event event) {
		System.out.println(username.getText() + "" + password.getText());
	}

	@FXML
	void loginButtonOnMouseClicked(Event e) throws Exception {
		String uname = username.getText();
		String upass = password.getText();
		String md5pass = CipherUtil.generatePassword(upass);
		Map<String, String> map1 = new HashMap<>();
		map1.put("username", uname);
		map1.put("password", md5pass);
		Map<String, Object> map = Http.postConnect("http://localhost:8080/storage/user/login", null,
				new Gson().toJson(map1));
		if (Http.CODE == 200) {
			// 登录成功
			Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setTitle("仓储管理系统");
			stage.setScene(scene);
			stage.show();
			Container.stage.close();
			Container.stage = stage;
			Container.root = root;
			Container.scene = scene;
		} else {
			// 登录失败
			message.setText((String) map.get("msg"));
			message.setVisible(true);
		}
	}

	@FXML
	void verifyButtonOnMouseClicked(Event e) throws Exception {
		String uname = username2.getText();
		String umail = email.getText();
		Container.email = umail;// 要可能重新发送 存到这里喽
		Map<String, String> map1 = new HashMap<>();
		map1.put("username", uname);
		map1.put("email", umail);
		Container.username = uname;
		// 先直接跳转界面 因为发送邮件太慢 没有用 艹 不改发邮件的代码 只能开个线程了...
		pane2.setVisible(false);
		pane3.setVisible(true);
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// 随便他到底他妈发没发成功 没收到的话自己再jb点一次就行了
					Http.postConnect("http://localhost:8080/storage/user/findpwd", null, new Gson().toJson(map1));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
	}

	@FXML
	void changeButtonOnMouseClicked(Event e) throws Exception {
		String uvcode = vcode.getText();
		String unewpass = newpassword.getText();
		Map<String, String> map1 = new HashMap<>();
		map1.put("username", Container.username);
		map1.put("password", CipherUtil.generatePassword(unewpass));
		map1.put("vCode", uvcode);
		Map<String, Object> map = Http.postConnect("http://localhost:8080/storage/user/changepwd", null,
				new Gson().toJson(map1));
		if (Http.CODE == 200) {
			message.setText("修改密码成功,请重新登录");
			message.setVisible(true);
			pane3.setVisible(false);
			pane1.setVisible(true);
		} else {
			message.setText((String) map.get("msg"));
			message.setVisible(true);
		}

	}
}
