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

    @Autowired
    private RepositoryDepartamentos repositoryDepartamentos;

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
    public ResponseEntity<Empleado> updateEmpleado(
            @PathVariable Long id,
            @RequestBody Empleado empleadoRequest) {

        Empleado empleado = repositoryEmpleados.findById(id).orElse(null);
        if (empleado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        empleado.setNombre(empleadoRequest.getNombre());
        empleado.setDireccion(empleadoRequest.getDireccion());
        empleado.setTelefono(empleadoRequest.getTelefono());

        if (empleadoRequest.getDepto() != null) {
            Departamentos depto = repositoryDepartamentos.findById(empleadoRequest.getDepto().getClave()).orElse(null);
            if (depto != null) {
                empleado.setDepto(depto);
            }
        }
        Empleado actualizado = repositoryEmpleados.save(empleado);
        return ResponseEntity.ok(actualizado);
    }

    @PostMapping
    public ResponseEntity<Empleado> createEmpleado(@RequestBody Empleado empleado) {
        if (empleado.getDepto() != null && empleado.getDepto().getClave() > 0) {
            Departamentos deptoExistente = repositoryDepartamentos.findById(empleado.getDepto().getClave())
                    .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));
            empleado.setDepto(deptoExistente);
        } else if (empleado.getDepto() != null) {
            Departamentos nuevoDepto = repositoryDepartamentos.save(empleado.getDepto());
            empleado.setDepto(nuevoDepto);
        }

        Empleado nuevoEmpleado = repositoryEmpleados.save(empleado);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEmpleado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Empleado> existingEmpleado = repositoryEmpleados.findById(id);
        if (existingEmpleado.isPresent()) {
            repositoryEmpleados.delete(existingEmpleado.get());
            return ResponseEntity.ok("Empleado borrado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no encontrado");
        }
    }
}
