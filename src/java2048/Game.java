package java2048;


import java.util.ArrayList;
import java.util.Random;

public class Game {
    private int[][] gameBoard;
    private Random r = new Random();
    private GameStatus status;
    private int score;
    private int highScore;

    public Game() {
        gameBoard = new int[4][4];
        addNewNumbers();/*最开始生成两个数字*/
        addNewNumbers();
        status = GameStatus.CONTINUE;
    }

    public int getScore(){
        return score;
    }
    public int getHighScore(){
        return highScore;
    }
    public void setHighScore(int highScore){
        this.highScore=highScore;
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public GameStatus getStatus() {
        return status;
    }

    /*打印表格*/
    public void printArray() {
        for (int[] x : gameBoard) {
            System.out.format("%6d%6d%6d%6d%n", x[0], x[1], x[2], x[3]);
        }
        System.out.print("\n");
    }

    /*随机生成数字*/
    public void addNewNumbers() {
        if(checkBoardFull())
            return;
        ArrayList<Integer> emptySpacesX = new ArrayList<Integer>();
        ArrayList<Integer> emptySpacesY = new ArrayList<Integer>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (gameBoard[i][j] == 0) {
                    emptySpacesX.add(new Integer(i));
                    emptySpacesY.add(new Integer(j));
                }
            }
        }
        int choice = r.nextInt(emptySpacesX.size());
        int numberChooser = r.nextInt(2);/*随机生成0，1*/
        int newNumber = 2;//默认是2
        if (numberChooser == 0) {//若生成的随机数是0，就将该位置的值为4
            newNumber = 4;
        }
        int X = emptySpacesX.get(choice);//返回随机数的位置
        int Y = emptySpacesY.get(choice);
        gameBoard[X][Y] = newNumber;/*将2或者4赋值给该位置*/
    }

    /*向上操作*/
    public void pushUp() {
        System.out.println("push up........");
        for (int y = 0; y < 4; y++) {
            for (int x = 1; x < 4; x++) {/*从第二行开始*/
                int flag = 0;//判断标志
                if (gameBoard[x][y] != 0) {
                    int value = gameBoard[x][y];
                    int X = x - 1;/*X为前一行*/
                    while ((X >= 0) && gameBoard[X][y] == 0) {
                        X--;
                        flag = 1;
                    }
                    if (X == -1) {/*前面所有行都为0，直接赋值给第一行*/
                        gameBoard[0][y] = value;
                        gameBoard[x][y] = 0;
                    } else if (gameBoard[X][y] != value) {/*flag为0，证明前一行不都为0，并且与该行值不相等，不做操作*/
                        if (flag == 1) {/*中间有1或2行空行，因此将该行的下一行赋值为value*/
                            gameBoard[X + 1][y] = value;
                            gameBoard[x][y] = 0;
                        }
                    } else if(gameBoard[X][y]==gameBoard[x][y]){
                        gameBoard[X][y] *= 2;
                        gameBoard[x][y] = 0;
                        score+=gameBoard[X][y];
                    }
                }
            }
        }
    }

    /*向下操作*/
    public void pushDown() {
        System.out.println("push down........");
        for (int y = 0; y < 4; y++) {
            for (int x = 2; x > -1; x--) {/*第3行开始*/
                int flag = 0;//判断标志
                if (gameBoard[x][y] != 0) {
                    int value = gameBoard[x][y];
                    int X = x + 1;/*X为下一行*/
                    while ((X <= 3) && gameBoard[X][y] == 0) {
                        X++;
                        flag = 1;
                    }
                    if (X == 4) {/*第4行都为0，将value赋值第4行*/
                        gameBoard[3][y] = value;
                        gameBoard[x][y] = 0;
                    } else if (gameBoard[X][y] != value) {/*下一行不等于value，flag为0，不做操作*/
                        if (flag == 1) {
                            gameBoard[X - 1][y] = value;/*flag为一，中间有空行，让它上一行为value*/
                            gameBoard[x][y] = 0;
                        }
                    } else if(gameBoard[X][y]==gameBoard[x][y]){

                        gameBoard[X][y] *= 2;
                        gameBoard[x][y] = 0;
                        score+=gameBoard[X][y];
                    }
                }
            }
        }
    }

    /*向右操作*/
    public void pushRight() {
        System.out.println("push right........");
        for (int x = 0; x < 4; x++) {
            for (int y = 2; y > -1; y--) {/*第3列开始*/
                int flag = 0;//判断标志
                if (gameBoard[x][y] != 0) {
                    int value = gameBoard[x][y];
                    int Y = y + 1;/*右边的一列*/
                    while ((Y <= 3) && gameBoard[x][Y] == 0) {
                        Y++;
                        flag = 1;
                    }
                    if (Y == 4) {/*最右一列为0*/
                        gameBoard[x][3] = value;
                        gameBoard[x][y] = 0;
                    } else if (gameBoard[x][Y] != value) {/*右边的列与该值不相等，flag为0，不操作*/
                        if (flag == 1) {/*flag为1，中有空列，让该列左边一列为该值*/
                            gameBoard[x][Y - 1] = value;
                            gameBoard[x][y] = 0;
                        }
                    } else if(gameBoard[x][Y]==gameBoard[x][y]){

                        gameBoard[x][Y] *= 2;
                        gameBoard[x][y] = 0;
                        score+=gameBoard[x][Y];
                    }
                }
            }
        }
    }

    public void pushLeft() {
        System.out.println("push left........");
        for (int x = 0; x < 4; x++) {
            for (int y = 1; y < 4; y++) {/*第2列开始*/
                int flag = 0;//判断标志
                if (gameBoard[x][y] != 0) {
                    int value = gameBoard[x][y];
                    int Y = y - 1;/*为左边一列*/
                    while ((Y >= 0) && gameBoard[x][Y] == 0) {
                        Y--;
                        flag = 1;
                    }
                    if (Y == -1) {
                        gameBoard[x][0] = value;
                        gameBoard[x][y] = 0;/*第一列为0*/
                    } else if (gameBoard[x][Y] != value) {/*与前一列不相等，flag为0，不操作*/
                        if (flag == 1) {/*flag=1，中间有空列，让它右边一列为该值*/
                            gameBoard[x][Y + 1] = value;
                            gameBoard[x][y] = 0;
                        }
                    } else if(gameBoard[x][Y]==gameBoard[x][y]){
                        gameBoard[x][Y] *= 2;
                        gameBoard[x][y] = 0;
                        score+=gameBoard[x][Y];
                    }
                }
            }
        }
    }

    /*出现2048返回true*/
    public boolean checkFor2048() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (gameBoard[x][y] == 2048)
                    return true;
            }
        }
        return false;
    }

    /*格子满了返回true*/
    public boolean checkBoardFull() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (gameBoard[x][y] == 0)
                    return false;
            }
        }
        return true;
    }

    /*判断是否还能移动，能移动返回true*/
    public boolean checkHasMoves() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (x == 0) {
                    if (y != 0) {
                        if (gameBoard[x][y] == gameBoard[x][y - 1])/*第一行234列和123列有相等的数*/
                            return true;
                    }
                } else {
                    if (y != 0) {
                        if (gameBoard[x][y] == gameBoard[x][y - 1])
                            return true;
                    }
                    if (gameBoard[x][y] == gameBoard[x - 1][y])
                        return true;
                }
            }
        }
        return false;
    }

    public void checkStatus() {
        if (checkFor2048())
            status = GameStatus.WIN;
        else if (checkBoardFull()) {
            if(checkHasMoves()){
                status=GameStatus.CONTINUE;
            }else {
                status=GameStatus.LOSE;
            }
        }else {
            status =GameStatus.CONTINUE;
        }
    }
}
