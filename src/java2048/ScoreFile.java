package java2048;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ScoreFile {
    private int score;
    public int readFile() {
        int num = 0;
        char[] buf = new char[1024];
        FileReader fr = null;
        File file = new File("F:\\学校\\2048game\\score.txt");/*创建文件目录*/
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fr = new FileReader("F:\\学校\\2048game\\score.txt");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        //取出字符存到buf数组中
        while (true) {
            try {
                if (!((num = fr.read(buf)) != -1)) break;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //String(char[] cbuf,a,b),从cbuf的位置a开始取出连续的b个char组成字符串
            score = Integer.parseInt(new String(buf, 0, num));
        }
        return score;
    }
}



