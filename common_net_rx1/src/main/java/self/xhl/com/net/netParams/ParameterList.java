package self.xhl.com.net.netParams;

import java.util.HashMap;
import java.util.Set;

/**
 * @author bingo 不改
 * @version 1.0.0
 */
//请求Boby参数列表（用于sign的计算）
public class ParameterList {
    private HashMap<String, String> params = null;

    public ParameterList() {
        this.params = new HashMap();
    }

    public ParameterList(int initialCapacity) {
        this.params = new HashMap(initialCapacity);
    }

    public final void put(String name, String value) {
        if(name != null && name.length() != 0 && value != null) {
            this.params.put(name, value);
        }
    }

    public final Set<String> keySet() {
        return this.params.keySet();
    }

    public final String get(String key) {
        return (String)this.params.get(key);
    }

    public final boolean containsKey(String key) {
        return this.params.containsKey(key);
    }

    public final int size() {
        return this.params.size();
    }
}
