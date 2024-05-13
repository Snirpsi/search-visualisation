package ecs.components.graphics.drawables;

import ecs.components.graphics.Drawable;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.*;

public class MapCSP2D extends Drawable {

    /**
     * stores the number of circles
     * Like GAMESIZE = variables.size();
     * Call like mapColoringProblem.GAMESIZE
     */
    private int circleNumber;

    /**
     * stores the Datastructure from the circles
     */
    Map<String, Circle> variableToCircleMap;

    /**
     * storage Datastructure of Variable constraints Map
     */
    private List<List<String>> variableConstraintsMap;

    /**
     * Constructor with CircleSize
     *
     * @param circleNumber = mapColoringProblem.GAMESIZE
     */
    public MapCSP2D(int circleNumber, List<List<String>> variableConstraintsMap) {
        this.variableToCircleMap = new HashMap<>();
        this.variableConstraintsMap = variableConstraintsMap;
        this.circleNumber = circleNumber;
    }

    /**
     * get the number of circles based on the GAMESIZE and the function mapColoringProblem.GAMESIZE
     */
    public int getCircleNumber() {
        return circleNumber;
    }

    /**
     * get the Datastructure from the circles
     */
    public Map<String, Circle> getVariableToCircleMap() {
        return variableToCircleMap;
    }

    /**
     * get the Datastructure of the Variable constraints Map
     */
    public List<List<String>> getVariableConstraintsMap() {
        return variableConstraintsMap;
    }

    /**
     * insert a circle to the map
     *
     * @param sprites
     * @param circles
     * @param variable
     * @param index
     * @param cX
     * @param cY
     */
    public void insertCircle(Sprite sprites, List<Circle> circles, List<String> variable, int index, double cX, double cY) {
        Circle c = new Circle();
        c.setRadius(30);
        c.setCenterX(cX);
        c.setCenterY(cY);
        c.setFill(Color.WHITE);
        c.setStrokeWidth(2);
        c.setStroke(Color.BLACK);
        sprites.addShape(c);
        circles.add(c);

        // Add the circle to the map
        String v = variable.get(index);
        this.variableToCircleMap.put(v, c);

        System.out.println(variableConstraintsMap);
        System.out.println(getVariableConstraintsMap());
    }

    /**
     * set the color of the circle based on the variable and the value
     *
     * @param variable
     * @param value
     */
    public void setCircleColor(String variable, String value) {
        Circle c = variableToCircleMap.get(variable);
        if (value == null) {
            List<Color> availableColors = Arrays.asList(Color.RED, Color.BLUE, Color.GREEN);
            Random random = new Random();
            Color randomColor = availableColors.get(random.nextInt(availableColors.size()));
            c.setFill(randomColor);
        }else if(value.equals("red")) {
            c.setFill(Color.RED);
        } else if(value.equals("green")) {
            c.setFill(Color.GREEN);
        } else if(value.equals("blue")) {
            c.setFill(Color.BLUE);
        }
    }

    public void fitToMap(List<String> vcm, Sprite sprites){
//        System.out.println("Constraint: " + vcm.get(0) + " " + vcm.get(1));

        String var1 = vcm.get(0);
        String var2 = vcm.get(1);

        Circle c1 = variableToCircleMap.get(var1);
        Circle c2 = variableToCircleMap.get(var2);

        Line l = new Line();
        l.setStartX(c1.getCenterX());
        l.setStartY(c1.getCenterY());
        l.setEndX(c2.getCenterX());
        l.setEndY(c2.getCenterY());
        l.setStrokeWidth(2);
        l.setStroke(Color.BLACK);

        sprites.addShape(l);
    }

    @Override
    public void update(float deltaT) {
        // TODO In best case Auto-generated method stub
    }

    /**
     * returns all shapes of the {@link MapCSP2D}
     */
    @Override
    public List<Node> getShapes() {
        // TODO: Implement the return of the shapes
        return null;
    }
}

/*
 * Copyright (c) 2024 Alexander Ultsch
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

