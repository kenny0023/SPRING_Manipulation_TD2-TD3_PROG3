package com.springManipulation.td2_td3.validator;

import com.springManipulation.td2_td3.model.Student;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentValidator {
    public String validate(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return "Aucun étudiant fourni dans le corps de la requête";
        }

        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            String error = validateSingleStudent(s, i);
            if (error != null) {
                return error;
            }
        }
        return null;
    }

    private String validateSingleStudent(Student student, int index) {
        if (student == null) {
            return "L'étudiant à la position " + index + " est null";
        }
        if (isBlank(student.getReference())) {
            return "La référence est obligatoire pour l'étudiant à la position " + index;
        }
        if (isBlank(student.getFirstName())) {
            return "Le prénom est obligatoire pour l'étudiant à la position " + index;
        }
        if (isBlank(student.getLastName())) {
            return "Le nom est obligatoire pour l'étudiant à la position " + index;
        }
        if (student.getAge() == null || student.getAge() <= 0) {
            return "L'âge doit être un nombre positif pour l'étudiant à la position " + index;
        }
        return null;
    }

    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
}
