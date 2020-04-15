package com.easys.loja.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompraDTO {

	private List<ItemCompraDTO> itens;
	private EnderecoDTO endereco;

}
