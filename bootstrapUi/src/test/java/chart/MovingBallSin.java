package chart;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import static javafx.scene.input.KeyCode.SPACE;

public class MovingBallSin extends Application {
    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        Circle circle = new Circle(15);
        circle.setStroke(Color.PINK);
        circle.setFill(Color.PINK);
        Double[] points=new Double[2000];
        points[0]=0.0;
        points[1]=200.0;
        double d=0.8; //取点间隔
        //生成正弦曲线上的1000个点
        for(int i=2;i<1999;i++){
            points[i]=points[i-2]+d;
            points[i+1]=200+70*Math.sin(points[i]/20);
            i++;
        }

        //用折线逼近正弦曲线
        Polyline polyline = new Polyline();
        polyline.getPoints().addAll(points);
        polyline.setStroke(Color.BLUEVIOLET);
        pane.getChildren().add(circle);
        pane.getChildren().add(polyline);

        //x轴、y轴
        Line y=new Line(440,0,440,400);
        Line x=new Line(0,200,800,200);
        Line tick1 = new Line(440,0,435,10);
        Line tick2 = new Line(440,0,445,10);
        Line tick3 = new Line(800,200,790,195);
        Line tick4 = new Line(800,200,790,205);
        Text texty = new Text(420,10,"Y");
        Text textx = new Text(780,190,"X");
        Text textPi1 = new Text(560,210,"2π");
        Text textPi2 = new Text(300,210,"-2π");
        Text text0 = new Text(430,210,"0");
        pane.getChildren().addAll(x,y,texty,textx,tick1,tick2,tick3,tick4);
        pane.getChildren().addAll(textPi1,textPi2,text0);

        PathTransition pt = new PathTransition();
        pt.setPath(polyline);
        pt.setNode(circle);
        pt.setDuration(Duration.seconds(6));
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt.setCycleCount(Timeline.INDEFINITE);
        pt.play();

        circle.setOnKeyPressed(e->{
            if(e.getCode()==SPACE && pt.getStatus()==PathTransition.Status.RUNNING)
                pt.pause();
            else if(e.getCode()==SPACE)
                pt.play();
        });

        Scene scene = new Scene(pane, 800, 400);
        primaryStage.setTitle("The Moving Ball");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        circle.requestFocus();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

