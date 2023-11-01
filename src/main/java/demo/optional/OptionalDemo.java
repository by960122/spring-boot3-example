package demo.optional;

import java.util.Optional;

import com.example.constant.GlobalResponseEnum;
import com.example.exception.CustomException;

/**
 * @author: BYDylan
 * @date: 2024/3/29
 * @description:
 */
public class OptionalDemo {
    public static void main(String[] args) {
        Student student = null;
        // if(!Objects.isNull(student)){
        // String name = student.getName();
        // }
        Optional.ofNullable(student).ifPresent(System.out::println);

        // if (!Objects.isNull(student)) {
        // String address = student.getAddress();
        // if (!Objects.isNull(address)) {
        // if (StringUtils.hasLength(address)) {
        // System.out.println("address = " + address);
        // }
        // }
        // }
        // throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR, "param error.");
        Optional.ofNullable(student).map(Student::getAddress)
            .orElseThrow(() -> new CustomException(GlobalResponseEnum.PARAMETER_ERROR, "param error."));

        // if (!Objects.isNull(student)) {
        // if ("aaa".equalsIgnoreCase(student.getName())) {
        // return student;
        // }
        // } else {
        // student = new Student();
        // student.setAge(1);
        // }
        Optional.ofNullable(student).filter(stu -> "aaa".equalsIgnoreCase(student.getName())).orElseGet(() -> {
            Student student1 = new Student();
            student1.setAge(1);
            return student1;
        });

    }
}
