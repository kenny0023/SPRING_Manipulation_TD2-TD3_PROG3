package com.springManipulation.td2_td3;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StudentController {

    private static final List<Student> students = new ArrayList<>();

    @GetMapping("/welcome")
    public String welcome(@RequestParam("name") String name) {
        return "Welcome " + name;
    }

    @PostMapping("/students")
    public ResponseEntity<String> addStudents(@RequestBody List<Student> newStudents) {
        if (newStudents == null || newStudents.isEmpty()) {
            return ResponseEntity.badRequest().body("Aucun étudiant fourni");
        }
        students.addAll(newStudents);
        return ResponseEntity.ok("Étudiants ajoutés avec succès (" + newStudents.size() + " ajoutés)");
    }

    @GetMapping(value = "/students", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> getStudentNames(
            @RequestHeader(value = "Accept", required = false) String acceptHeader) {

        if (acceptHeader == null ||
                !acceptHeader.contains(MediaType.TEXT_PLAIN_VALUE) &&
                        !acceptHeader.contains("*/*")) {

            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Format non supporté");
        }

        if (students.isEmpty()) {
            return ResponseEntity.ok("Aucun étudiant enregistré");
        }

        String names = students.stream()
                .map(s -> s.getFirstName() + " " + s.getLastName())
                .collect(Collectors.joining("\n"));

        return ResponseEntity.ok(names);
    }
}
