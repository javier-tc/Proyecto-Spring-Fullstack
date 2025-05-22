package com.proyectoSpring.fullstack.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class InventarioRequest {
    @NotBlank(message = "El código del producto es obligatorio")
    private String codigo;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción del producto es obligatoria")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    private BigDecimal precio;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 0, message = "La cantidad debe ser mayor o igual a 0")
    private Integer cantidad;

    @NotNull(message = "El stock mínimo es obligatorio")
    @Min(value = 0, message = "El stock mínimo debe ser mayor o igual a 0")
    private Integer stockMinimo;

    @NotNull(message = "El stock máximo es obligatorio")
    @Min(value = 0, message = "El stock máximo debe ser mayor o igual a 0")
    private Integer stockMaximo;

    @NotNull(message = "La categoría es obligatoria")
    private Long categoriaId;
} 