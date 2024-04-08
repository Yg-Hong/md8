package com.ms8.md.recommendation.feature.reco.utils;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "faiss")
public interface FaissClient {
	@GetMapping("/api/faiss")
	List<Long> faissReco(@RequestParam Double lat, @RequestParam Double lon, @RequestParam Integer time, @RequestParam Integer dist, @RequestParam Integer k);
}
