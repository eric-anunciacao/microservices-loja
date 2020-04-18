package com.easys.loja.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.easys.loja.dto.InfoEntregaDTO;
import com.easys.loja.dto.VoucherDTO;

@FeignClient("transportador")
public interface TransportadorClient {

	@RequestMapping(method = RequestMethod.POST, value = "/entrega")
	VoucherDTO reservaEntrega(InfoEntregaDTO entregaDTO);

}
