package practice;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Created by mist36 on 2016/06/01.
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // シーングラフを作成
            BorderPane root = new BorderPane();

            // シーングラフに子ノードを追加する
            Rectangle r = new Rectangle(10, 20, 100, 200);
            r.setFill(Color.YELLOW);
            root.getChildren().add(r);

            // シーンを作成
            Scene scene = new Scene(root, 400, 400, Color.BLACK);

            // ステージにシーンを登録し、ウィンドウ表示する
            primaryStage.setScene(scene);
            primaryStage.setTitle("JavaFX Hellow World!");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

