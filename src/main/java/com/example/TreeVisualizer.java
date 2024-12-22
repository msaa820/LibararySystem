package com.example;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TreeVisualizer {

    private AVLTree tree;

    public TreeVisualizer(AVLTree tree) {
        this.tree = tree;
    }

    public void show() {
        Stage stage = new Stage();
        Canvas canvas = new Canvas(750, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawTree(tree.getRoot(), gc, 375, 30, 150);

        VBox layout = new VBox(canvas);
        Scene scene = new Scene(layout, 800, 600);
        stage.setTitle("AVL Tree");
        stage.setScene(scene);
        stage.show();
    }

    private void drawTree(AVLNode node, GraphicsContext gc, double x, double y, double xOffset) {
        if (node != null) {
            gc.setStroke(Color.BLACK);
            gc.setFill(Color.BLACK);
            gc.strokeOval(x - 20, y - 20, 40, 40);
            gc.fillOval(x - 20, y - 20, 40, 40);
            gc.setFill(Color.WHITE);
            gc.fillText(String.valueOf(node.bookId), x - 10, y + 5);

            if (node.left != null) {
                gc.strokeLine(x, y, x - xOffset, y + 60);
                drawTree(node.left, gc, x - xOffset, y + 60, xOffset / 2);
            }

            if (node.right != null) {
                gc.strokeLine(x, y, x + xOffset, y + 60);
                drawTree(node.right, gc, x + xOffset, y + 60, xOffset / 2);
            }
        }
    }
}
