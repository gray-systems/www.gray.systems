package systems.gray.www.services.graypass.client;

import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

public class KeyValueStore {
    /**
     * @param store
     */
    public KeyValueStore() {
        this.store = Maps.newHashMap();
    }

    private final Map<String, String> store;
    
    public String get(String key) {
        String result = store.get(key);
        return Strings.nullToEmpty(result);
    }
    
    public void put(String key, String value) {
        String sanitaryKey = Strings.nullToEmpty(key);
        String sanitaryValue = Strings.nullToEmpty(value);
        store.put(sanitaryKey, sanitaryValue);
    }
}