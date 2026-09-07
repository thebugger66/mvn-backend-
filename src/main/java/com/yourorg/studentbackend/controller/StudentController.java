package com.yourorg.studentbackend.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // fine for local dev; tighten this before going to production
public class StudentController {

    // Simple in-memory store - no database involved.
    // Data resets every time the app restarts.
    private final Map<Long, String> students = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    public StudentController() {
        long id1 = idCounter.incrementAndGet();
        students.put(id1, "Alice");
        long id2 = idCounter.incrementAndGet();
        students.put(id2, "Bob");
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }

    @GetMapping("/students")
    public Map<Long, String> getStudents() {
        return students;
    }

    @PostMapping("/students")
    public Map<String, Object> addStudent(@RequestBody Map<String, String> body) {
        long id = idCounter.incrementAndGet();
        students.put(id, body.get("name"));
        return Map.of("id", id, "name", body.get("name"));
    }

    @DeleteMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id) {
        students.remove(id);
        return "Deleted";
    }

}
