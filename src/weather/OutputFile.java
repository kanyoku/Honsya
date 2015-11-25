package weather;

import java.io.*;

/**
 * Created by mist36 on 2015/11/25.
 *
 * ファイルの作成と書き込みを行う。
 */
public class OutputFile {

    /** URL取得 */
    private static GettingURL gettingURL;

    /**
     * コンストラクタ
     */
    public OutputFile(){
        gettingURL = new GettingURL();
        createFile();
        fileOutputStream();
    }

    /**
     * 新しいファイルを作成します。
     */
    public void createFile(){
        try {
            File newFile = new File("C:\\Users\\mist36\\weather.txt");
            newFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        } ;
    }

    /**
     * ファイルへ書き込みます。
     */
    public void fileOutputStream() {
        String outputFileName = "C:\\Users\\mist36\\weather.txt";
        File outputFile = new File(outputFileName);

        try{
            FileOutputStream fos = new FileOutputStream(outputFile);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            PrintWriter pw = new PrintWriter(osw);

            pw.println(gettingURL.getLine());
            pw.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}

