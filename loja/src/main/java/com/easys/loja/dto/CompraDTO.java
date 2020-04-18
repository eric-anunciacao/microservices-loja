package com.easys.loja.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompraDTO {

	@JsonIgnore
	private Long compraId;
	private List<ItemCompraDTO> itens;
	private EnderecoDTO endereco;

}
