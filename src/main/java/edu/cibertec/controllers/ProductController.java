package edu.cibertec.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import edu.cibertec.dtos.ProductDTO;

@Controller
public class ProductController {

	@Value("${backend.url}")
	private String apiUrl;
	private RestTemplate restTemplate;
	
	public ProductController(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.rootUri("").build();
	}
	
	@GetMapping("/{id}")
	public String findById(@PathVariable Long id, Model model) {
		ResponseEntity<ProductDTO> product = this.restTemplate.getForEntity(this.apiUrl + '/' + id, ProductDTO.class);
		model.addAttribute("producto", product.getBody());
		return "detalle-producto";
	}
	
}
