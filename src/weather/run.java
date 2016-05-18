package weather;

/**
 * Created by mist36 on 2015/11/25.
 */
public class run {

    /**
     * URL取得
     */
    private static OutputFile outputFile;

    /**
     * ファイル読み込み
     */
    private static ReadTextFile readTextFile;

    /**
     * コンストラクタ
     */
    public static void main(String args[]) {
        outputFile = new OutputFile();
        readTextFile = new ReadTextFile();
    }
}