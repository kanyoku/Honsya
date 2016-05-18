package weather;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mist36 on 2016/04/27.
 */
public class sample {

    public static void main(String[] args) throws Exception {
        // 起動時にオプションを指定しなかった場合は、このサンプルデータを使用する。
        String script = "{ \"key1\" : \"val1\", \"key2\" : \"val2\", \"key3\" : { \"ckey1\" : \"cval1\", \"ckey2\" : [ \"cval2-1\", \"cval2-2\" ] } }";
        if (args.length > 0) {
            java.io.File f = new java.io.File(args[0]);
            if (f.exists() && f.isFile()) {

                byte[] buf = new byte[new Long(f.length()).intValue()];
                java.io.FileInputStream fin = null; try { fin = new java.io.FileInputStream(f); fin.read(buf); } catch (Exception ex) { throw ex; } finally { if (fin != null) { fin.close(); }}
                script = args.length > 1 ? new String(buf, args[1]) : new String(buf);
            } else {
                script = args[0];
            }
        }
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        Object obj = engine.eval(String.format("(%s)", script));

        Map<String, Object> map = jsonToMap(obj, engine.getClass().getName().equals("com.sun.script.javascript.RhinoScriptEngine"));
        System.out.println(map.toString());
    }

    static Map<String, Object> jsonToMap(Object obj, boolean rhino) throws Exception {

        boolean array = rhino ? Class.forName("sun.org.mozilla.javascript.internal.NativeArray").isInstance(obj) : false;
        Class scriptObjectClass = Class.forName(rhino ? "sun.org.mozilla.javascript.internal.Scriptable" : "jdk.nashorn.api.scripting.ScriptObjectMirror");

        Object[] keys = rhino ? (Object[])obj.getClass().getMethod("getIds").invoke(obj) : ((java.util.Set)obj.getClass().getMethod("keySet").invoke(obj)).toArray();

        Method method_get = array ? obj.getClass().getMethod("get", int.class, scriptObjectClass) : (rhino ? obj.getClass().getMethod("get", Class.forName("java.lang.String"), scriptObjectClass) : obj.getClass().getMethod("get", Class.forName("java.lang.Object")));
        Map<String, Object> map = new HashMap<String, Object>();
        for (Object key : keys) {
            Object val = array ? method_get.invoke(obj, (Integer)key, null) : (rhino ? method_get.invoke(obj, key.toString(), null) : method_get.invoke(obj, key));
            if (scriptObjectClass.isInstance(val)) {
                map.put(key.toString(), jsonToMap(val, rhino));
            } else {
                map.put(key.toString(), val.toString());
            }
        }
        return map;
    }
}
