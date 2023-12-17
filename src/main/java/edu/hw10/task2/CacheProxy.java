package edu.hw10.task2;

import edu.hw10.task2.annotation.Cache;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class CacheProxy implements InvocationHandler {
    private final Object object;
    public final Map<String, Object> cacheMap;
    public String cacheDir = "src/main/java/edu/hw10/task2/cacheStorage/";

    public CacheProxy(Object object) {
        this.object = object;
        this.cacheMap = new HashMap<>();
    }

    public static <T> T create(T object, Class<?> objectInterface) {
        CacheProxy cacheProxy = new CacheProxy(object);

        return (T) Proxy.newProxyInstance(
            objectInterface.getClassLoader(),
            new Class<?>[] {objectInterface},
            cacheProxy
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        if (method.isAnnotationPresent(Cache.class)) {
            Cache cacheAnnotation = method.getAnnotation(Cache.class);
            String key = getCacheKey(method, args);
            if (cacheAnnotation.persist()) {
                if (isObjectInFileCache(key)) {
                    result = returnObjectFromFileCache(key);
                } else {
                    result = method.invoke(object, args);
                    putObjectToFileCache(key, result);
                }
            } else {
                if (isObjectInMapCache(key)) {
                    result = returnObjectFromMapCache(key);
                } else {
                    result = method.invoke(object, args);
                    putObjectToMapCache(key, result);
                }
            }
        }
        return result;
    }

    private boolean isObjectInMapCache(String key) {
        return cacheMap.containsKey(key);
    }

    private boolean isObjectInFileCache(String key) {
        File file = new File(getFileCachePath(key));
        return file.exists();
    }

    private Object returnObjectFromMapCache(String key) {
        return cacheMap.get(key);
    }

    private Object returnObjectFromFileCache(String key) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(getFileCachePath(key)))) {
            return inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void putObjectToFileCache(String key, Object object) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(getFileCachePath(key)))) {
            outputStream.writeObject(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void putObjectToMapCache(String key, Object object) {
        cacheMap.put(key, object);
    }

    private String getFileCachePath(String key) {
        return cacheDir + key;
    }

    private String getCacheKey(Method method, Object[] args) {
        String hash = String.valueOf(method.hashCode());
        StringBuilder key = new StringBuilder(hash);
        for (Object arg : args) {
            key.append(arg.toString());
        }
        return new String(key);
    }
}
