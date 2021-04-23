package com.snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
    private Image apple;
    private Image dot;
    private Image head;

    private final int DOT_SIZE = 10; // 300*300= 90000 / 100 = 900
    private final int ALL_DOTS = 2500;
    private final int RANDOM_POSITION = 29;

    private int apple_x;
    private int apple_y;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private int dots;

    private Timer timer;

    Board() {

        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(500, 500));

        setFocusable(true);

        loadImages();
        initGame();

    }

    public void loadImages() {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("com/snakegame/icon/apple.png"));
        apple = i1.getImage();
        apple = apple.getScaledInstance(20, 20, Image.SCALE_DEFAULT);

        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("com/snakegame/icon/dot.png"));
        dot = i2.getImage();

        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("com/snakegame/icon/head.png"));
        head = i3.getImage();
    }

    public void initGame() {

        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * DOT_SIZE; // i-50, 2: 50-dot_size=50-10=40, 3: 50-2*10=30
            y[z] = 50;
        }

        locateApple();

        // "this" will call actionPerformed directly
        timer = new Timer(140, this);
        timer.start();

    }

    public void locateApple() {

         int r = (int) (Math.random() * RANDOM_POSITION); // 0 and 1 = 0.1 0.5 0.32 //double
         apple_x = (r * DOT_SIZE); // 29*10=290 Frame is of (300,300) Position of apple will be from 1 - 290

         r = (int) (Math.random() * RANDOM_POSITION);
         apple_y = (r * DOT_SIZE);
        //apple_x =(int) (Math.random(0 + apple.getWidth(null) / 2,Snake.DEFAULT_WIDTH - apple.getWidth(null) / 2));

    }

    public void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            locateApple();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (inGame) {
            g.drawImage(apple, apple_x - apple.getWidth(null) / 2, apple_y - apple.getHeight(null) / 2, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z] - head.getWidth(null) / 2, y[z] - head.getHeight(null) / 2, this);
                } else {
                    g.drawImage(dot, x[z] - dot.getWidth(null) / 2, y[z] - dot.getHeight(null) / 2, this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }

    }

    public void gameOver(Graphics g) {
        String msg = "Game Over";
        Font font = new Font("SAN SERIF", Font.BOLD, 14);
        FontMetrics metrics = getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (Snake.DEFAULT_WIDTH - metrics.stringWidth(msg)) / 2, Snake.DEFAULT_HEIGHT / 2);
    }

    public void checkCollision() {
        for (int z = dots; z > 0; z--) {
            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }

            if (y[0] >= Snake.DEFAULT_WIDTH) {
                inGame = false;
            }
            if (x[0] >= Snake.DEFAULT_HEIGHT) {
                inGame = false;
            }
            if (y[0] < 0) {
                inGame = false;
            }
            if (x[0] < 0) {
                inGame = false;
            }
            if (!inGame) {
                timer.stop();
            }
        }
    }

    public void move() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[z - 1];
            y[z] = y[z - 1];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE; // 240 - 10 = 230
        }
        if (rightDirection) {
            x[0] += DOT_SIZE;
        }
        if (upDirection) {
            y[0] -= DOT_SIZE;
        }
        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    // class inside class
    private class TAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && (!rightDirection)) {

                leftDirection = true;
                upDirection = false;
                downDirection = false;

            }
            if (key == KeyEvent.VK_RIGHT && (!leftDirection)) {

                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == KeyEvent.VK_UP && (!downDirection)) {

                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
            if (key == KeyEvent.VK_DOWN && (!upDirection)) {

                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

        }
    }
}
