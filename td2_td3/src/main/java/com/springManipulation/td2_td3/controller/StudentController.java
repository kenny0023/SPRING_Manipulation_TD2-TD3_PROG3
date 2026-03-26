package com.springManipulation.td2_td3.controller;

import com.springManipulation.td2_td3.model.Student;
import com.springManipulation.td2_td3.service.StudentService;
import com.springManipulation.td2_td3.validator.StudentValidator;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    private final StudentService studentService;
    private final StudentValidator studentValidator;

    public StudentController(StudentService studentService, StudentValidator studentValidator) {
        this.studentService = studentService;
        this.studentValidator = studentValidator;
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(@RequestParam(value = "name", required = false) String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Le paramètre 'name' est obligatoire");
        }
        return ResponseEntity.ok("Welcome " + name);
    }

    @PostMapping("/students")
    public ResponseEntity<String> addStudents(@RequestBody List<Student> newStudents) {

        String validationError = studentValidator.validate(newStudents);
        if (validationError != null) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(validationError);
        }

        studentService.addAll(newStudents);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.TEXT_PLAIN)
                .body(newStudents.size() + " étudiant(s) ajouté(s) avec succès");
    }

    @GetMapping("/students")
    public ResponseEntity<?> getStudents(
            @RequestHeader(value = "Accept", defaultValue = "*/*") String acceptHeader) {

        if (studentService.isEmpty()) {
            if (acceptHeader.contains(MediaType.TEXT_PLAIN_VALUE)) {
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body("Aucun étudiant enregistré");
            } else {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(List.of());
            }
        }

        if (acceptHeader.contains(MediaType.TEXT_PLAIN_VALUE)) {
            String names = studentService.getAllStudents().stream()
                    .map(s -> s.getFirstName() + " " + s.getLastName())
                    .collect(java.util.stream.Collectors.joining("\n"));

            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(names);
        } else {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(studentService.getAllStudents());
        }
    }
}