package weather;

import java.io.*;

/**
 * Created by mist36 on 2015/11/25.
 *
 * �t�@�C���̍쐬�Ə������݂��s���B
 */
public class OutputFile {

    /** URL�擾 */
    private static GettingURL gettingURL;

    /**
     * �R���X�g���N�^
     */
    public OutputFile(){
        gettingURL = new GettingURL();
        createFile();
        fileOutputStream();
    }

    /**
     * �V�����t�@�C�����쐬���܂��B
     */
    public void createFile(){
        try {
            File newFile = new File("C:\\Users\\kanyoku\\weather.txt");
            newFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * �t�@�C���֏������݂܂��B
     */
    public void fileOutputStream() {
        String outputFileName = "C:\\Users\\kanyoku\\weather.txt";
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

