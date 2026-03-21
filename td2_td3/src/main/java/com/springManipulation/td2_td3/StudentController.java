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
            return ResponseEntity.badRequest().body("Aucun étudiant fourni dans le corps de la requête");
        }

        students.addAll(newStudents);

        String message = "Étudiants ajoutés avec succès (" + newStudents.size() + " ajoutés)";
        return ResponseEntity.ok(message);
    }

    @GetMapping("/students")
    public ResponseEntity<?> getStudentNames(
            @RequestHeader(value = "Accept", defaultValue = "*/*") String acceptHeader) {

        System.out.println("Accept header reçu : " + acceptHeader);

        if (acceptHeader == null ||
                (!acceptHeader.contains(MediaType.TEXT_PLAIN_VALUE) &&
                        !acceptHeader.contains(MediaType.APPLICATION_JSON_VALUE) &&
                        !acceptHeader.contains("*/*"))) {

            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Format non supporté");
        }

        if (students.isEmpty()) {
            if (acceptHeader.contains(MediaType.TEXT_PLAIN_VALUE)) {
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body("Aucun etudiant enregistré");
            } else {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ArrayList<>());
            }
        }

        if (acceptHeader.contains(MediaType.TEXT_PLAIN_VALUE)) {
            String names = students.stream()
                    .map(s -> s.getFirstName() + " " + s.getLastName())
                    .collect(Collectors.joining("\n"));

            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(names);
        } else {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(students);
        }
    }
}
