package com.example.demo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/empleados")
public class ControllerEmpleados {
    
    @Autowired
    private RepositoryEmpleados repositoryEmpleados;
    
    @GetMapping
    public ResponseEntity<List<Empleado>> list() {
        List<Empleado> empleados = repositoryEmpleados.findAll();
        return ResponseEntity.ok(empleados);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> get(@PathVariable Long id) {
        Optional<Empleado> res = repositoryEmpleados.findById(id);
        if (res.isPresent()) {
            return ResponseEntity.ok(res.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable Long id, @RequestBody Empleado input) {
        Optional<Empleado> existingEmpleado = repositoryEmpleados.findById(id);
        if (existingEmpleado.isPresent()) {
            Empleado updatedEmpleado = existingEmpleado.get();
            updatedEmpleado.setNombre(input.getNombre());
            repositoryEmpleados.save(updatedEmpleado);
            return ResponseEntity.ok(updatedEmpleado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no encontrado");
        }
    }
    
    @PostMapping
    public ResponseEntity<Empleado> post(@RequestBody Empleado input) {
        Empleado newEmpleado = repositoryEmpleados.save(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEmpleado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Empleado> existingEmpleado = repositoryEmpleados.findById(id);
        if (existingEmpleado.isPresent()) {
            repositoryEmpleados.delete(existingEmpleado.get());
            return ResponseEntity.ok("Empleado borrado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        }
    }
}