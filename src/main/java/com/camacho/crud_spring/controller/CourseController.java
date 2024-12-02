package com.camacho.crud_spring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.camacho.crud_spring.model.Course;
import com.camacho.crud_spring.repository.CourseRepository;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CourseController {

    private final CourseRepository courseRepository;

    @GetMapping
    public @ResponseBody List<Course> list(){
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id){
        return courseRepository.findById(id)
            .map(record -> ResponseEntity.ok().body(record))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Course course){
        List<Course> allCourse = courseRepository.findAll();
        Course existCourse = allCourse.stream().filter(c -> c.getName().equals(course.getName())).findFirst().orElse(null);
        if(existCourse!=null){
            throw new RuntimeException("Já existe um curso com o título informado");
        }
        //return courseRepository.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            courseRepository.save(course)
            );
    }

    @PutMapping("/{id}")
    public Course update(@PathVariable Long id, @RequestBody Course course){
        Course c = courseRepository.findById(id).orElse(null);
        if(c == null){
            throw new RuntimeException("Curso não encontrado na base de dados");
        }
        c.setName(course.getName());
        c.setCategory(course.getCategory());
        return courseRepository.save(c);
    }
}
