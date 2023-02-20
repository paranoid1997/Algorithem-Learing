package com.liu.Hash;

import java.util.HashMap;

/**
 * 设计hashmap一个API接口setAll,其功能就是调用
 * setAll这个接口的时候,hashmap里的所有value会改成
 * 你传进去那个数据的值。
 * 要求：不允许遍历，做到时间复杂度O(1)
 */
public class SetAll {

    public static class MyVaulue<V> {
        public V value;
        public long time;

        public MyVaulue(V value, long time) {
            this.value = value;
            this.time = time;
        }
    }

    public static class MyHashMap<K,V> {
        private HashMap<K,MyVaulue<V>> map;
        private long time;
        private MyVaulue<V> setAll;


        public MyHashMap() {
            map = new HashMap<>();
            time = 0;
            setAll = new MyVaulue<V>(null,-1);
        }

        public void put(K key, V value) {
            map.put(key,new MyVaulue<>(value,time++));
        }

        public void setAll(V value) {
            //setAll中的time赋值为当前的时间戳
            //然后time + 1
            setAll = new MyVaulue<>(value,time++);
        }

        public V get(K key) {
            if (!map.containsKey(key)) {
                return null;
            }
            if (map.get(key).time > setAll.time) {
                //重新在put一条数据后
                return map.get(key).value;
            }else {
                //前面的setAll之前的所有key对应的value都改成
                //你传进去的值
                return setAll.value;
            }
        }
    }
}
