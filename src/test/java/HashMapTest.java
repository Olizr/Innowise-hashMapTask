import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class HashMapTest {

    private HashMap<String, Integer> getFilledMap(int size) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        for (int i = 0; i < size; i++) {
            map.put(Integer.toString(i + (i * 10) + (i * 100)), i);
        }

        return map;
    }

    private Collection<Entry<String, Integer>> getFilledCollection(int size) {
        Collection<Entry<String, Integer>> collection = new ArrayList<Entry<String, Integer>>();

        for (int i = 0; i < size; i++) {
            collection.add(new Entry<String, Integer>(Integer.toString(i + (i * 10) + (i * 100)), i));
        }

        return collection;
    }

    @Test
    public void sizeEmptyMap() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        Assert.assertEquals(0, map.size());
    }

    @Test
    public void sizeFilledMap() {
        int mapSize = 60;
        HashMap<String, Integer> map = getFilledMap(mapSize);

        Assert.assertEquals(mapSize, map.size());
    }

    @Test
    public void isEmptyMapEmpty() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        Assert.assertTrue(map.isEmpty());
    }

    @Test
    public void isEmptyMapFilled() {
        int mapSize = 60;
        HashMap<String, Integer> map = getFilledMap(mapSize);

        Assert.assertFalse(map.isEmpty());
    }

    @Test
    public void containsKeyNoKey() {
        int mapSize = 60;
        HashMap<String, Integer> map = getFilledMap(mapSize);

        Assert.assertFalse(map.containsKey("aaa"));
    }

    @Test
    public void containsKeyExistKey() {
        String key = "aaa";
        int mapSize = 60;
        HashMap<String, Integer> map = getFilledMap(mapSize);

        map.put(key, 2);
        Assert.assertTrue(map.containsKey(key));
    }

    @Test
    public void containsValueNoValue() {
        int mapSize = 60;
        int value = 9876;
        HashMap<String, Integer> map = getFilledMap(mapSize);

        Assert.assertFalse(map.containsValue(value));
    }

    @Test
    public void containsValueExistValue() {
        String key = "aaa";
        int value = 9876;
        int mapSize = 60;
        HashMap<String, Integer> map = getFilledMap(mapSize);

        map.put(key, value);
        Assert.assertTrue(map.containsValue(value));
    }

    @Test
    public void getExistKey() {
        String key = "aaa";
        int value = 9876;
        int mapSize = 60;
        HashMap<String, Integer> map = getFilledMap(mapSize);
        map.put(key, value);

        Assert.assertEquals(value, (int)map.get(key));
    }

    @Test
    public void getExistNotExistKey() {
        String key = "aaa";
        int mapSize = 60;
        HashMap<String, Integer> map = getFilledMap(mapSize);

        Assert.assertNull(map.get(key));
    }

    @Test
    public void getExistNullKey() {
        String key = null;
        int value = 9876;
        int mapSize = 60;
        HashMap<String, Integer> map = getFilledMap(mapSize);
        map.put(key, value);

        Assert.assertEquals(value, (int)map.get(key));
    }

    @Test
    public void put() {
        String key = "aaa";
        int value = 9876;
        int mapSize = 60;
        HashMap<String, Integer> map = getFilledMap(mapSize);
        map.put(key, value);

        Assert.assertEquals(value, (int)map.get(key));
    }

    @Test
    public void putNullValue() {
        String key = "aaa";
        Integer value = null;
        int mapSize = 60;
        HashMap<String, Integer> map = getFilledMap(mapSize);
        map.put(key, value);

        Assert.assertNull(map.get(key));
    }

    @Test
    public void removeExistKey() {
        String key = "aaa";
        Integer value = 9876;
        int mapSize = 60;
        HashMap<String, Integer> map = getFilledMap(mapSize);
        map.put(key, value);

        Assert.assertEquals(value, map.remove(key));
    }

    @Test
    public void removeNotExistKey() {
        String key = "aaa";
        int mapSize = 60;
        HashMap<String, Integer> map = getFilledMap(mapSize);

        Assert.assertNull(map.remove(key));
    }

    @Test
    public void putAll() {
        int mapSize = 60;
        int collectionSize = 3;

        HashMap<String, Integer> map = getFilledMap(mapSize);

        Collection<Entry<String, Integer>> collection = getFilledCollection(collectionSize);
        map.putAll(collection);

        for (Entry<String, Integer> i: collection) {
            Assert.assertEquals(i.getValue(), map.get(i.getKey()));
        }
    }

    @Test
    public void clear() {
        String key = "aaa";
        Integer value = 9876;
        int mapSize = 60;
        HashMap<String, Integer> map = getFilledMap(mapSize);
        map.put(key, value);

        map.clear();

        Assert.assertEquals(0, map.size());
        Assert.assertNull(map.get(key));
    }

    @Test
    public void values() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        int collectionSize = 3;
        Collection<Entry<String, Integer>> collection = getFilledCollection(collectionSize);

        map.putAll(collection);

        Collection<Integer> valueCollection = map.values();

        for (Entry<String, Integer> i: collection) {
            Assert.assertTrue(valueCollection.contains(i.getValue()));
        }
    }

    @Test
    public void keys() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        int collectionSize = 3;
        Collection<Entry<String, Integer>> collection = getFilledCollection(collectionSize);

        map.putAll(collection);

        Collection<String> valueCollection = map.keys();

        for (Entry<String, Integer> i: collection) {
            Assert.assertTrue(valueCollection.contains(i.getKey()));
        }
    }

    @Test
    public void entries() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        int collectionSize = 3;
        Collection<Entry<String, Integer>> collection = getFilledCollection(collectionSize);

        map.putAll(collection);

        Collection<Entry<String, Integer>> valueCollection = map.entries();

        for (Entry<String, Integer> i: collection) {
            Assert.assertTrue(valueCollection.contains(i));
        }
    }
}