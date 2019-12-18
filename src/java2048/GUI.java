package java2048;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Hashtable;
import java.util.Map;
import javax.swing.*;

public class GUI {
    private int frameHeight = 380;
    private int frameWidth = 320;
    private int gameBoardSize = 296;
    private int marginSize = 16;

    Color backgroundColor = new Color(255, 255, 120);

    private Font largeFeedbackFont = new Font("SansSerif", 0, 40);
    private Font smallFeedbackFont = new Font("SansSerif", 0, 20);

    private JLabel scoreLable;//用于显示信息
    private JLabel highScore;

    private MyFram frame;
    private Game game;
    private gameBoard gb;
    private Map<Integer, ImageIcon> numberTiles;

    public  GUI() {
        loadNumberTiles();
        game = new Game();
        frame = new MyFram();
        frame.setFocusable(true);
        frame.setTitle("java2048");
        frame.setLocation(800,300);//窗体位置
        frame.addKeyListener(new MyFram());//键盘监听
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gb = new gameBoard();

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(1,1));//网格布局
        northPanel.setPreferredSize(new Dimension(frameWidth, 82));//容器大小

        scoreLable = new JLabel("<html>Score:<br>0</html>", SwingConstants.CENTER);
        scoreLable.setFont(new Font("Serif", Font.BOLD,20));
        northPanel.add(scoreLable);



        highScore = new JLabel("<html>High Score:<br>0</html>", SwingConstants.CENTER);
        highScore.setFont(new Font("Serif", Font.BOLD,20));
        northPanel.add(highScore);

        ScoreFile scoreFile =new ScoreFile();/*存在最高分 就加载最高分*/
        if(scoreFile.readFile()>0) {
            game.setHighScore(scoreFile.readFile());
            frame.highScore();
        }

        northPanel.setBackground(backgroundColor);

        JPanel westBuffer = new JPanel();//面板
        westBuffer.setPreferredSize(new Dimension(marginSize, gameBoardSize));
        westBuffer.setBackground(backgroundColor);

        JPanel eastBuffer = new JPanel();
        eastBuffer.setPreferredSize(new Dimension(marginSize, gameBoardSize));
        eastBuffer.setBackground(backgroundColor);

        JPanel soutBuffer = new JPanel();
        soutBuffer.setPreferredSize(new Dimension(frameWidth, marginSize));
        soutBuffer.setBackground(backgroundColor);


        frame.getContentPane().add(northPanel, BorderLayout.NORTH);/*让panels覆盖默认的rootPane*/
        frame.getContentPane().add(westBuffer, BorderLayout.WEST);
        frame.getContentPane().add(eastBuffer, BorderLayout.EAST);
        frame.getContentPane().add(soutBuffer, BorderLayout.SOUTH);
        frame.getContentPane().add(gb, BorderLayout.CENTER);
        frame.getContentPane().setPreferredSize(new Dimension(frameWidth, frameHeight));
        frame.pack();/*设置setpreferredsize*/
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void loadNumberTiles() {//键值绑定
        numberTiles = new Hashtable<>();
        numberTiles.put(0, new ImageIcon("F://学校//2048game//images//0.jpg"));
        numberTiles.put(2, new ImageIcon("F:/学校//2048game//images//2.jpg"));
        numberTiles.put(4, new ImageIcon("F://学校//2048game//images//4.jpg"));
        numberTiles.put(8, new ImageIcon("F://学校//2048game//images//8.jpg"));
        numberTiles.put(16, new ImageIcon("F://学校//2048game//images//16.jpg"));
        numberTiles.put(32, new ImageIcon("F://学校//2048game//images//32.jpg"));
        numberTiles.put(64, new ImageIcon("F://学校//2048game//images//64.jpg"));
        numberTiles.put(128, new ImageIcon("F://学校//2048game//images//128.jpg"));
        numberTiles.put(256, new ImageIcon("F://学校//2048game//images//256.jpg"));
        numberTiles.put(512, new ImageIcon("F://学校//2048game//images//512.jpg"));
        numberTiles.put(1024, new ImageIcon("F://学校//2048game//images//1024.jpg"));
        numberTiles.put(2048, new ImageIcon("F://学校//2048game//images//2048.jpg"));
    }


    class gameBoard extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(new Color(20, 20, 20));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            int[][] board = game.getGameBoard();
            for (int y = 1; y < 5; y++) {
                for (int x = 1; x < 5; x++) {
                    int X = (8 * x) + (64 * (x - 1));
                    int Y = (8 * y) + (64 * (y - 1));
                    int thisNumber = board[y - 1][x - 1];//获取当前方块的值
                    if (numberTiles.containsKey(thisNumber)) {
                        ImageIcon thisTile;
                        thisTile = numberTiles.get(thisNumber);//通过键，加载图片
                        thisTile.paintIcon(this, g, X, Y);
                    }
                }
            }
        }
    }

    class WinBoard extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(new Color(20, 20, 20));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setFont(largeFeedbackFont);
            g.setColor(new Color(0, 80, 0));
            g.drawString("2048!", 20, 40);
            g.setFont(smallFeedbackFont);
            g.setColor(new Color(255, 255, 255));
            g.drawString("press ENTER to play again !", 20, 70);
        }
    }

    class LoseBoard extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(new Color(20, 20, 20));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setFont(largeFeedbackFont);
            g.setColor(new Color(200, 0, 0));
            g.drawString("   You Lose!", 20, 40);
            g.setFont(smallFeedbackFont);
            g.setColor(new Color(255, 255, 255));
            g.drawString("press ENTER to try again !", 20, 70);
        }
    }

     class MyFram extends JFrame implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {/*设置键盘监听*/
            int key = e.getKeyCode();
            if (game.getStatus() == GameStatus.CONTINUE) {
                if (key == KeyEvent.VK_UP) {
                    System.out.println("up key pressed...");
                    game.pushUp();
                    game.addNewNumbers();
                    game.checkStatus();
                    game.printArray();
                    gb.repaint();
                    updataScore();
                    System.out.println(game.getScore());
                } else if (key == KeyEvent.VK_DOWN) {
                    System.out.println("down key pressed...");
                    game.pushDown();
                    game.addNewNumbers();
                    game.checkStatus();
                    game.printArray();
                    gb.repaint();
                    updataScore();
                    System.out.println(game.getScore());
                } else if (key == KeyEvent.VK_LEFT) {
                    System.out.println("left key pressed...");
                    game.pushLeft();
                    game.addNewNumbers();
                    game.checkStatus();
                    game.printArray();
                    gb.repaint();
                    updataScore();
                    System.out.println(game.getScore());
                } else if (key == KeyEvent.VK_RIGHT) {
                    System.out.println("right key pressed...");
                    game.pushRight();
                    game.addNewNumbers();
                    game.checkStatus();
                    game.printArray();
                    gb.repaint();
                    updataScore();
                    System.out.println(game.getScore());
                }
                GameStatus gs = game.getStatus();
                if (gs == GameStatus.LOSE) {
                    frame.getContentPane().remove(gb);
                    frame.getContentPane().add(new LoseBoard(), BorderLayout.CENTER);
                    frame.getContentPane().invalidate();
                    frame.getContentPane().validate();/*重画确保有组件刷新*/
                } else if (gs == GameStatus.WIN) {
                    frame.getContentPane().remove(gb);
                    frame.getContentPane().add(new WinBoard(), BorderLayout.CENTER);
                    frame.getContentPane().invalidate();
                    frame.getContentPane().validate();
                }
            } else {
                if (key == KeyEvent.VK_ENTER) {
                    int nowScore = updataScore();
                   // game.setHighScore(nowScore);

                    ScoreFile sf =new ScoreFile();
                    if(sf.readFile()<nowScore){
                        File file = new File("F:\\学校\\2048game\\score.txt");
                        FileWriter fw = null;
                        try {
                            fw = new FileWriter(file);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        BufferedWriter bf = new BufferedWriter(fw);
                        try {
                            bf.write(String.valueOf(nowScore));
                            bf.flush();
                            bf.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                    game.setHighScore(sf.readFile());
                   // System.out.println(sf.readFile());

                    highScore();
                    game = new Game();
                    frame.getContentPane().remove(((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.CENTER));
                    frame.getContentPane().add(gb);
                    gb.repaint();
                    updataScore();
                    frame.getContentPane().invalidate();
                    frame.getContentPane().validate();
                }
            }
            GameStatus gameStatus = game.getStatus();
            if (gameStatus == GameStatus.CONTINUE)
                System.out.println("game continue...");
            if (gameStatus == GameStatus.WIN)
                System.out.println("you win...");
            if (gameStatus == GameStatus.LOSE)
                System.out.println("you lose");
    }

        public int updataScore() {
            scoreLable.setText("<html>Score:<br>" + game.getScore() + "</html>");
            return game.getScore();
        }

        public void highScore() {
            highScore.setText("<html>high Score:<br>" + game.getHighScore() + "</html>");
        }
    }
}

