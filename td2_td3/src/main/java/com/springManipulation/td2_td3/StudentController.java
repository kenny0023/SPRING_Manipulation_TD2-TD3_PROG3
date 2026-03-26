package com.springManipulation.td2_td3;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StudentController {

    private static final List<Student> students = new ArrayList<>();

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(@RequestParam(value = "name", required = false) String name) {

        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Le paramètre 'name' est obligatoire");
        }

        String message = "Welcome " + name;
        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(message);
    }

    @PostMapping("/students")
    public ResponseEntity<String> addStudents(@RequestBody List<Student> newStudents) {

        if (newStudents == null || newStudents.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Aucune liste d'étudiants fournie dans le corps de la requête");
        }

        try {
            students.addAll(newStudents);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .header("Location", "/students")
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(newStudents.size() + " étudiant(s) créé(s) avec succès");

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Erreur serveur lors de la création des étudiants");
        }
    }

    @GetMapping("/students")
    public ResponseEntity<?> getStudents(
            @RequestHeader(value = "Accept", defaultValue = "*/*") String acceptHeader) {

        System.out.println("Accept header reçu : " + acceptHeader);

        if (students.isEmpty()) {
            if (acceptHeader.contains(MediaType.TEXT_PLAIN_VALUE)) {
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body("Aucun étudiant enregistré");
            } else {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ArrayList<Student>());  // [] vide
            }
        }

        if (acceptHeader.contains(MediaType.TEXT_PLAIN_VALUE)) {
            String names = students.stream()
                    .map(s -> s.getFirstName() + " " + s.getLastName())
                    .collect(Collectors.joining("\n"));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(names);
        }

        if (acceptHeader.contains(MediaType.APPLICATION_JSON_VALUE) ||
                acceptHeader.contains("*/*")) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(students);
        }

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .contentType(MediaType.TEXT_PLAIN)
                .body("Format non supporté. Utilisez text/plain ou application/json");
    }
}
