package weather;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kanyoku on 2015/11/29.
 */
public class ConversionJson {

    /**
     * URL?��
     */
    private static GettingURL gettingURL;

    /**
     * �R���X�g���N�^
     */
    public ConversionJson() {

    }

        public void ToJson() throws Exception {
            // �N�����ɃI�v�V�������w�肵�Ȃ������ꍇ�́A���̃T���v���f�[�^���g�p����B
            String script =gettingURL.getLine();
            if (script.length() > 0) {
                File f = new File(args[0]);
                if (f.exists() && f.isFile()) {
                    // �N�����̑�P�������t�@�C���Ȃ�t�@�C������ǂݍ��݁ijava 6 �Ή��łȂ̂ŁAtry-with-resources ����g���܂���B���ۂ́A����ȏ����������ɂ����ƃG���[�������Ă��������j
                    byte[] buf = new byte[new Long(f.length()).intValue()];
                    FileInputStream fin = null; try { fin = new FileInputStream(f); fin.read(buf); } catch (Exception ex) { throw ex; } finally { if (fin != null) { fin.close(); }}
                    script = args.length > 1 ? new String(buf, args[1]) : new String(buf); // �t�@�C���̕����R�[�h���V�X�e���̕����R�[�h�ƈقȂ�ꍇ�́A��Q�����Ŏw��B�Ⴆ�΁uUTF-8�v��uJISAutoDetect�v�ȂǁB
                } else {
                    script = args[0]; // �t�@�C���łȂ���΁A��P�����̕���������̂܂� JSON �Ƃ��Ĉ���
                }
            }
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            // ScriptEngine �� eval �� JSON ��n�����́A���ʂň͂܂Ȃ��Ɨ�O���������܂��Beval �̓Z�L�����e�B�I�ɂ͍D�܂����Ȃ��̂ŁA���S�ł��邱�Ƃ��s���ȃf�[�^���������Ƃ͑z�肵�Ă��܂���B
            Object obj = engine.eval(String.format("(%s)", script));
            // Rhino �́Ajdk1.6,7�܂ł� JavaScript �G���W���Bjdk1.8�́ujdk.nashorn.api.scripting.NashornScriptEngine�v
            Map<String, Object> map = jsonToMap(obj, engine.getClass().getName().equals("com.sun.script.javascript.RhinoScriptEngine"));
            System.out.println(map.toString());
        }

        static Map<String, Object> jsonToMap(Object obj, boolean rhino) throws Exception {
            // Nashorn �̏ꍇ�� isArray �� obj ���z�񂩂ǂ������f�ł��܂����A���ɉ������Ȃ��Ă��z��ԍ����L�[�ɂ��Ēl���擾�� Map �Ɋi�[�ł���̂ŁA�����ł͖������Ă��܂��B
            // Rhino ���ƃC���f�b�N�X�𕶎���Ƃ��Ďw�肵���ꍇ�ɒl���Ԃ��Ă��Ȃ��悤�Ȃ̂ŁA�d���Ȃ�������؂蕪���܂����B
            // ���ۂ� HashMap �Ȃ񂩎g�킸�Ɏ����Œ�`�����N���X�i�z��͂��̃I�u�W�F�N�g�� List �v���p�e�B�j�Ƀ}�b�v���邱�ƂɂȂ�Ǝv���̂ŁA����T���v���Ƃ��Ă͂���Ȃ���ł�낵�����ƁB
            boolean array = rhino ? Class.forName("sun.org.mozilla.javascript.internal.NativeArray").isInstance(obj) : false;
            Class scriptObjectClass = Class.forName(rhino ? "sun.org.mozilla.javascript.internal.Scriptable" : "jdk.nashorn.api.scripting.ScriptObjectMirror");
            // �L�[�Z�b�g���擾
            Object[] keys = rhino ? (Object[])obj.getClass().getMethod("getIds").invoke(obj) : ((java.util.Set)obj.getClass().getMethod("keySet").invoke(obj)).toArray();
            // get ���\�b�h���擾
            Method method_get = array ? obj.getClass().getMethod("get", int.class, scriptObjectClass) : (rhino ? obj.getClass().getMethod("get", Class.forName("java.lang.String"), scriptObjectClass) : obj.getClass().getMethod("get", Class.forName("java.lang.Object")));
            Map<String, Object> map = new HashMap<String, Object>();
            for (Object key : keys) {
                Object val = array ? method_get.invoke(obj, (Integer)key, null) : (rhino ? method_get.invoke(obj, key.toString(), null) : method_get.invoke(obj, key));
                if (scriptObjectClass.isInstance(val)) {
                    map.put(key.toString(), jsonToMap(val, rhino));
                } else {
                    map.put(key.toString(), val.toString()); // �T���v���Ȃ̂ŁA�����ł͒P���� toString() ���Ă܂����A���ۂ� val �̌^��L���Ɋ��p���������ǂ��ł��傤�B
                }
            }
            return map;
        }
    }
