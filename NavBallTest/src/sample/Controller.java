package sample;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;


import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;


public class Controller {
    @FXML
    private Button CenterViewButton;
    @FXML
    private Button northButton;
    @FXML
    private Button southButton;
    @FXML
    private Button eastButton;
    @FXML
    private Button westButton;
    @FXML
    private Line xGreenLine;
    @FXML
    private Line yGreenLine;

    @FXML
    private Sphere rotationSphere;

    @FXML
    private Slider horizontalSlider;

    @FXML
    private Ellipse verticalEllipse1;

    @FXML
    private Ellipse verticalEllipse2;

    @FXML
    private Ellipse verticalEllipse3;

    @FXML
    private Ellipse verticalEllipse4;

    @FXML
    private Ellipse horizontalEllipse1;

    @FXML
    private Ellipse horizontalEllipse2;

    @FXML
    private Ellipse horizontalEllipse3;

    @FXML
    private Ellipse horizontalEllipse4;


    //Directional Button Actions
    @FXML
    private void northButtonPressed(ActionEvent event) {
        moveNorth();
    }
    @FXML
    private void southButtonPressed(ActionEvent event) {
       moveSouth();
    }
    @FXML
    private void eastButtonPressed(ActionEvent event) {
        moveEast();
    }
    @FXML
    private void westButtonPressed(ActionEvent event) {
        moveWest();
    }


    //Slider Actions

    private static final double ROTATE_SECS   = 20;

    @FXML
    private void sliderDragDone(ActionEvent event) {

    }

    public void initialize() {
       final double EARTH_RADIUS  = 101;
       final double VIEWPORT_SIZE = 800;
       final double ROTATE_SECS   = 30;

       final double MAP_WIDTH  = 8192 / 2d;
       final double MAP_HEIGHT = 4092 / 2d;

        final String DIFFUSE_MAP =
                "http://planetmaker.wthr.us/img/earth_gebco8_texture_8192x4096.jpg";
        final String NORMAL_MAP =
                "http://planetmaker.wthr.us/img/earth_normalmap_flat_8192x4096.jpg";
        final String SPECULAR_MAP =
                "http://planetmaker.wthr.us/img/earth_specularmap_flat_8192x4096.jpg";
        PhongMaterial earthMaterial = new PhongMaterial();
        earthMaterial.setDiffuseMap(
                new Image(
                        DIFFUSE_MAP,
                        MAP_WIDTH,
                        MAP_HEIGHT,
                        true,
                        true
                )
        );
        earthMaterial.setBumpMap(
                new Image(
                        NORMAL_MAP,
                        MAP_WIDTH,
                        MAP_HEIGHT,
                        true,
                        true
                )
        );
        earthMaterial.setSpecularMap(
                new Image(
                        SPECULAR_MAP,
                        MAP_WIDTH,
                        MAP_HEIGHT,
                        true,
                        true
                )
        );

        rotationSphere.setMaterial(
                earthMaterial
        );


        horizontalSlider.valueProperty().addListener((observable, oldValue, newValue) -> {

            System.out.print("new val ");
            System.out.println(newValue.intValue());
            System.out.print("old val ");
            System.out.println(oldValue.intValue());

            if(newValue.intValue() > oldValue.intValue()) {
                rotateAroundYAxis(rotationSphere, "right").play();
            } else if(newValue.intValue() < oldValue.intValue()){
                //rotate left
                rotateAroundYAxis(rotationSphere, "left").play();

            } else {
                rotateAroundYAxis(rotationSphere, "none").stop();
            }

        });

    }

    private RotateTransition rotateAroundYAxis(Node node, String direction) {
        RotateTransition rotate = new RotateTransition(
                Duration.seconds(0.3),
                node
        );
        rotate.setAxis(Rotate.Y_AXIS);
        if (direction == "right") {
            rotate.setFromAngle(rotationSphere.getRotate() - 10);
        } else if (direction == "left"){
            rotate.setFromAngle(rotationSphere.getRotate() + 10);
        } else {

        }
        //rotate.setToAngle(0);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setCycleCount(1);

        return rotate;
    }


    //Move Direction helper functions
    private void moveEast() {
        CenterViewButton.setTranslateX(CenterViewButton.getTranslateX() + 5);
        yGreenLine.setTranslateX(yGreenLine.getTranslateX() + 5);
    }
    private void moveWest(){
        CenterViewButton.setTranslateX(CenterViewButton.getTranslateX() - 5);
        yGreenLine.setTranslateX(yGreenLine.getTranslateX() - 5);
    }
    private void moveNorth(){
        CenterViewButton.setTranslateY(CenterViewButton.getTranslateY() - 5);
        xGreenLine.setTranslateY(xGreenLine.getTranslateY() - 5);
    }
    private void moveSouth(){
        CenterViewButton.setTranslateY(CenterViewButton.getTranslateY() + 5);
        xGreenLine.setTranslateY(xGreenLine.getTranslateY() + 5);
    }
}

