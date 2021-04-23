package com.snakegame;

import javax.swing.*;

public class Game extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 2504130082787173701L;
    static int DEFAULT_WIDTH, DEFAULT_HEIGHT;

    Game() {
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
        new Game().setVisible(true);
    }
}