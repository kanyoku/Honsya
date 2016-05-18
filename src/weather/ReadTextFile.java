package weather;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by mist36 on 2016/04/27.
 *
 * テキストファイル読み込み
 */
public class ReadTextFile {

    public ReadTextFile(){
        readFile();
    }


    public void readFile() {

        File file = new File("C:\\Users\\mist36\\weather.txt");
        try {
            FileReader fileReader = new FileReader(file);
            int ch = fileReader.read();

            StringBuilder stringBuilder = new StringBuilder();

            while (ch != -1) {
                ch = fileReader.read();

                stringBuilder.append((char) ch);
            }

            System.out.print(stringBuilder);

            fileReader.close();

       } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
