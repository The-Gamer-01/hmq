package extension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SPI机制的加载类.
 *
 * @author hyx
 **/

public class ExtensionLoader<T> {
    
    private ConcurrentHashMap<String, Holder> cachedInstance = new ConcurrentHashMap<>();
    
    private ExtensionLoader(String type) {
        this.type = type;
    }
    
    private static final String HMQ_EXTENSION_PATH = "META-INF/hmq/";
    
    private String type;
    
    private void loadDirectory(String dir) {
        String path = dir + type;
        ClassLoader classLoader = ExtensionLoader.class.getClassLoader();
        try {
            Enumeration<URL> resources = classLoader.getResources(path);
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    line = line.trim();
                    String[] split = line.split("=");
                    String key = split[0];
                    String value = split[1];
                    try {
                        Class<?> clazz = classLoader.loadClass(value);
                        Object instance = clazz.newInstance();
                        cachedInstance.put(key, new Holder(instance));
                    } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public T getExtension(String name) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("name为非法数据！");
        }
        Holder holder = cachedInstance.get(name);
        if (holder == null) {
            holder = new Holder();
            cachedInstance.putIfAbsent(name, holder);
        }
        Object value = holder.getValue();
        if (value == null) {
            loadDirectory(HMQ_EXTENSION_PATH);
            holder = cachedInstance.get(name);
            value = holder.getValue();
        }
        return (T) value;
    }
}
