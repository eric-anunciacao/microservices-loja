package com.easys.loja.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Compra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long pedidoId;

	private Integer tempoDePreparo;

	private String enderecoDestino;

	private LocalDate dataParaEntrega;

	private Long voucher;

	@Enumerated(EnumType.STRING)
	private StatusCompra status;
}
