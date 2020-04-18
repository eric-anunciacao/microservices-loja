package com.easys.loja.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easys.loja.client.FornecedorClient;
import com.easys.loja.client.TransportadorClient;
import com.easys.loja.dto.CompraDTO;
import com.easys.loja.dto.InfoEntregaDTO;
import com.easys.loja.model.Compra;
import com.easys.loja.model.StatusCompra;
import com.easys.loja.repository.CompraRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CompraService {

	@Autowired
	private FornecedorClient fornecedorClient;

	@Autowired
	private TransportadorClient transportadorClient;

	@Autowired
	private CompraRepository compraRepository;

	@HystrixCommand(threadPoolKey = "getByIdThreadPool")
	public Compra getById(Long id) {
		return compraRepository.findById(id).orElse(new Compra());
	}

	@HystrixCommand(fallbackMethod = "realizaCompraFallback", threadPoolKey = "realizaCompraThreadPool")
	public Compra realizaCompra(CompraDTO compra) {
		log.info("Realizando um pedido...");
		var compraSalva = new Compra();
		compraSalva.setEnderecoDestino(compra.getEndereco().toString());
		compraSalva.setStatus(StatusCompra.RECEBIDO);
		compraRepository.save(compraSalva);
		compra.setCompraId(compraSalva.getId());

		var info = fornecedorClient.getInfoPorEstado(compra.getEndereco().getEstado());
		var pedido = fornecedorClient.realizaPedido(compra.getItens());
		compraSalva.setPedidoId(pedido.getId());
		compraSalva.setTempoDePreparo(pedido.getTempoDePreparo());
		compraSalva.setStatus(StatusCompra.PEDIDO_REALIZADO);
		compraRepository.save(compraSalva);

		var entrega = new InfoEntregaDTO();
		entrega.setEnderecoOrigem(info.getEndereco());
		entrega.setPedidoId(pedido.getId());
		entrega.setDataParaEntrega(LocalDate.now().plusDays(pedido.getTempoDePreparo()));
		entrega.setEnderecoDestino(compra.getEndereco().toString());
		var voucher = transportadorClient.reservaEntrega(entrega);
		compraSalva.setDataParaEntrega(voucher.getPrevisaoParaEntrega());
		compraSalva.setVoucher(voucher.getNumero());
		compraSalva.setStatus(StatusCompra.RESERVA_ENTREGA_REALIZADA);
		compraRepository.save(compraSalva);

		return compraSalva;
	}

	public Compra realizaCompraFallback(CompraDTO compra) {
		if (compra.getCompraId() != null) {
			return compraRepository.getOne(compra.getCompraId());
		}
		var compraFallback = new Compra();
		compraFallback.setEnderecoDestino(compra.getEndereco().toString());
		return compraFallback;
	}

}
