package org.example.dienluc.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MapperUtil {
    //    clazz: Để biết được lớp nào (và kiểu dữ liệu nào) cần tạo các thể hiện và thiết lập giá trị.
//    fields: Để biết được tên các trường trong lớp mà bạn muốn thiết lập giá trị từ kết quả query.
    public static <T> List<T> mapResults(List<Object[]> results, Class<T> clazz, String[] fields) {
        return results.stream().map(result -> {
            try {
                T instance = clazz.getDeclaredConstructor().newInstance();
                for (int i = 0; i < fields.length; i++) {
                    Field field = clazz.getDeclaredField(fields[i]);
                    field.setAccessible(true);
                    field.set(instance, result[i]);
                }
                return instance;
            } catch (Exception e) {
                System.err.println("Error mapping result: " + Arrays.toString(result) +
                        " to class: " + clazz.getSimpleName());
                e.printStackTrace(); // In toàn bộ stack trace
                throw new RuntimeException("Failed to map result to " + clazz.getSimpleName(), e);
            }
        }).collect(Collectors.toList());
    }
}
