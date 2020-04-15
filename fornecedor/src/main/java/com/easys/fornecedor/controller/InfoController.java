package com.easys.fornecedor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easys.fornecedor.model.InfoFornecedor;
import com.easys.fornecedor.service.InfoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/info")
public class InfoController {

	@Autowired
	private InfoService service;

	@GetMapping("/{estado}")
	public InfoFornecedor getInfoPorEstado(@PathVariable String estado) {
		log.info("Recebido pedido de informações do fornecedor de {}", estado);
		return service.getInfoPorEstado(estado);
	}
}
