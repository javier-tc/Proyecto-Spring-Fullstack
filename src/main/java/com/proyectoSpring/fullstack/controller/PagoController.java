package com.proyectoSpring.fullstack.controller;

import com.proyectoSpring.fullstack.model.Pago;
import com.proyectoSpring.fullstack.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoController {
    private final PagoService pagoService;

    @Autowired
    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    public List<Pago> getAllPagos() {
        return pagoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> getPagoById(@PathVariable Long id) {
        return pagoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pago> createPago(@RequestBody Pago pago) {
        return ResponseEntity.ok(pagoService.save(pago));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> updatePago(@PathVariable Long id, @RequestBody Pago pago) {
        return pagoService.findById(id)
                .map(existing -> {
                    pago.setId(id);
                    return ResponseEntity.ok(pagoService.save(pago));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePago(@PathVariable Long id) {
        pagoService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}