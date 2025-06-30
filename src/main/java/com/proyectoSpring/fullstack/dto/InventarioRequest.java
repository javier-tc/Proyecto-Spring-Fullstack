package com.proyectoSpring.fullstack.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
public class InventarioRequest {
    @NotBlank(message = "El código es obligatorio")
    private String codigo;
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
    
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser positivo")
    private BigDecimal precio;
    
    @NotNull(message = "La cantidad es obligatoria")
    @PositiveOrZero(message = "La cantidad debe ser mayor o igual a 0")
    private Integer cantidad;
    
    @NotNull(message = "El stock mínimo es obligatorio")
    @PositiveOrZero(message = "El stock mínimo debe ser mayor o igual a 0")
    private Integer stockMinimo;
    
    @NotNull(message = "El stock máximo es obligatorio")
    @Positive(message = "El stock máximo debe ser positivo")
    private Integer stockMaximo;
    
    @NotNull(message = "El ID de categoría es obligatorio")
    @Positive(message = "El ID de categoría debe ser positivo")
    private Long categoriaId;
} 