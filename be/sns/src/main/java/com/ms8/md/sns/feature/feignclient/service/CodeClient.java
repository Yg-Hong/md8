package com.ms8.md.sns.feature.feignclient.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ms8.md.sns.feature.feignclient.dto.CodeResponse;
import com.ms8.md.sns.feature.feignclient.dto.DetailCodeResponse;
import com.ms8.md.sns.global.common.response.SuccessResponse;

@FeignClient(name = "codeClient", url = "http://localhost:8080")
public interface CodeClient {

	@GetMapping(value = "/api/codes/search/{codeName}")
	SuccessResponse<CodeResponse> getCode(@PathVariable("codeName") String codeName);

	@GetMapping(value = "/api/detail-codes")
	SuccessResponse<DetailCodeResponse> getDetailCodeByName(@RequestParam("codeId") Integer codeId, @RequestParam("name") String detailCodeName);
}
