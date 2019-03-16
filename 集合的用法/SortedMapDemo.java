package com.ibm.map;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 来源于mybatis源码  org.apache.ibatis.reflection.ParamNameResolver#ParamNameResolver
 *
 * TreeMap的put方法会根据key来进行排序
 *  public V put(K key, V value) {
 *         Entry<K,V> t = root;
 *         if (t == null) {
 *             compare(key, key); // type (and possibly null) check
 *
 *             root = new Entry<>(key, value, null);
 *             size = 1;
 *             modCount++;
 *             return null;
 *         }
 *
 *
 *
 *         利用有序集合通过其他方法来对集合中添加想要的元素,放入重复元素会被覆盖掉，说明key其实可以重复，只不过会覆盖原有的value
 */
public class SortedMapDemo {

    public static void main(String[] args) {

        SortedMap<Integer,String> map = new TreeMap<>();

        for (int index = 2; index < 10; index++) {

            String s = String.valueOf(map.size());

            map.put(index,s);

        }
        System.out.println(map);    //{2=99, 3=1, 4=2, 5=3, 6=4, 7=5, 8=6, 9=7}
        map.put(2,"100");
        System.out.println(map.size());   //8
        System.out.println(map);   //{2=100, 3=1, 4=2, 5=3, 6=4, 7=5, 8=6, 9=7}
    }

}
