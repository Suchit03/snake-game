package com.snakegame;

import javax.swing.*;

public class Snake extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 2504130082787173701L;
    static int DEFAULT_WIDTH, DEFAULT_HEIGHT;

    Snake() {
        super("Snake Game");
        DEFAULT_HEIGHT = 500;
        DEFAULT_WIDTH = 500;

        add(new Board());
        pack();

        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        new Snake().setVisible(true);
    }
}