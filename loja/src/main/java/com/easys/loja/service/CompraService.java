package com.easys.loja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easys.loja.client.FornecedorClient;
import com.easys.loja.dto.CompraDTO;
import com.easys.loja.model.Compra;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CompraService {

	@Autowired
	private FornecedorClient fornecedorClient;

	public Compra realizaCompra(CompraDTO compra) {
		final String estado = compra.getEndereco().getEstado();
		log.info("Buscando informações do fornecedor de {}", estado);
		var info = fornecedorClient.getInfoPorEstado(estado);
		log.info(info.getEndereco());

		log.info("Realizando um pedido...");
		var pedido = fornecedorClient.realizaPedido(compra.getItens());

		var compraSalva = new Compra();
		compraSalva.setPedidoId(pedido.getId());
		compraSalva.setTempoDePreparo(pedido.getTempoDePreparo());
		compraSalva.setEnderecoDestino(compra.getEndereco().toString());

		return compraSalva;
	}

}
