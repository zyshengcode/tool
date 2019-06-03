package com.ibm.interf.rs.util;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class CsvUtil<T> {

    /**
     * 生成为CVS文件
     *
     * @param exportData 源数据List
     * @param fileds
     * @param map        csv文件的列表头map
     * @param passway 文件路径==路径+文件名字  如  c://a/b.csv
     * @return
     */
    public FileOutputStream createCSVFile(List<T> exportData, String[] fileds, LinkedHashMap map, String passway) {
        FileOutputStream csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        try {
            File file = new File(passway);
            //如果不存在就创建，存在就追加
            if (!file.exists()) {
                file.createNewFile();
                csvFile =new FileOutputStream(passway);
                csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(csvFile, "GBK"), 1024);
                // 写入文件头部
                for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext(); ) {
                    Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
                    csvFileOutputStream.write((String) propertyEntry.getValue() != null ? new String(((String) propertyEntry.getValue()).getBytes("GBK"), "GBK") : "");
                    if (propertyIterator.hasNext()) {
                        csvFileOutputStream.write(",");
                    }
                }
                csvFileOutputStream.write("\r\n");
            }else{
                csvFile =new FileOutputStream(passway,true);
                csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(csvFile, "GBK"), 1024);
            }
            // 写入文件内容,
            // ============ //第一种格式：Arraylist<实体类>填充实体类的基本信息==================
            for (int j = 0; exportData != null && !exportData.isEmpty() && j < exportData.size(); j++) {
                T t = (T) exportData.get(j);
                Class clazz = t.getClass();
                String[] contents = new String[fileds.length];
                for (int i = 0; fileds != null && i < fileds.length; i++) {
                    String filedName = toUpperCaseFirstOne(fileds[i]);
                    Method method = clazz.getMethod(filedName);
                    method.setAccessible(true);
                    Object obj = method.invoke(t);
                    String str = String.valueOf(obj);
                    if (str == null || str.equals("null"))
                        str = "";
                    contents[i] = str;
                }
                for (int n = 0; n < contents.length; n++) {
                    // 将生成的单元格添加到工作表中
                    csvFileOutputStream.write(contents[n]);
                    csvFileOutputStream.write(",");

                }
                csvFileOutputStream.write("\r\n");
            }
            csvFileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvFileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }

    /**
     * 反射获取createCSVFile方法的第二个参数String[] fileds
     *
     * @return
     */
    public String[] getFileds(T t) {
        Field[] strings1 = t.getClass().getDeclaredFields();
        List<Field> orderedField = getOrderedField(strings1);
        String fileds[] = new String[orderedField.size()];
        for (int i = 0;i<orderedField.size();i++){
            fileds[i] = orderedField.get(i).getName();
        }
        return fileds;
    }

    /**
     * 反射获取createCSVFile方法的第三个参数 LinkedHashMap map
     *
     * @return
     */
    public LinkedHashMap getTitle(T t) {
        LinkedHashMap map = new LinkedHashMap();
        Field[] strings1 = t.getClass().getDeclaredFields();
        List<Field> orderedField = getOrderedField(strings1);
     for (int i = 0;i<orderedField.size();i++){
         Field field = orderedField.get(i);
         String filename = "\"" + field.getName() + "\"";
         int listNum = i+1;
         map.put(String.valueOf(listNum), filename);
     }
        return map;
    }

    /**
     * 反射根据实体类属性排序
     *
     * @return
     */
    private static List<Field> getOrderedField(Field[] fields){
        // 用来存放所有的属性域
        List<Field> fieldList = new ArrayList<>();
        // 过滤带有注解的Field
        for(Field f:fields){
            if(f.getAnnotation(BeanFieldAnnotation.class)!=null){
                fieldList.add(f);
            }
        }
        // 这个比较排序的语法依赖于java 1.8
        fieldList.sort(Comparator.comparingInt(
                m -> m.getAnnotation(BeanFieldAnnotation.class).order()
        ));
        return fieldList;
    }

    /**
     * 将第一个字母转换为大写字母并和get拼合成方法
     *
     * @param origin
     * @return
     */
    private static String toUpperCaseFirstOne(String origin) {
        StringBuffer sb = new StringBuffer(origin);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        sb.insert(0, "get");
        return sb.toString();
    }

    /**
     * 获取CSV文件名字
     *
     * @param origin
     * @return
     */
    public   String getFileName(T t) {
        String simpleName = t.getClass().getSimpleName();
        return simpleName.concat(".").concat("csv");
    }
}
