package weather;

/**
 * Created by mist36 on 2015/11/25.
 */
public class run {

    /**
     * URL�擾
     */
    private static OutputFile outputFile;

    /**
     * �t�@�C���ǂݍ���
     */
    private static ReadTextFile readTextFile;

    /**
     * �R���X�g���N�^
     */
    public static void main(String args[]) {
        outputFile = new OutputFile();
        readTextFile = new ReadTextFile();
    }
}