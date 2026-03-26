package com.springManipulation.td2_td3.service;

import com.springManipulation.td2_td3.model.Student;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final List<Student> students = new ArrayList<>();

    public void addAll(List<Student> newStudents) {
        students.addAll(newStudents);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public boolean isEmpty() {
        return students.isEmpty();
    }
}
