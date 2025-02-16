package edu.cibertec.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping("/")
	public String findAll(Model model) {
		ResponseEntity<ProductDTO[]> products = this.restTemplate.getForEntity(this.apiUrl, ProductDTO[].class);
		model.addAttribute("productos", products.getBody());
		return "productos";
	}
	
	@GetMapping("/{id}")
	public String findById(@PathVariable Long id, Model model) {
		ResponseEntity<ProductDTO> product = this.restTemplate.getForEntity(this.apiUrl + '/' + id, ProductDTO.class);
		model.addAttribute("producto", product.getBody());
		return "detalle-producto";
	}
	
	@GetMapping("/nuevo")
	public String showFormToRegister(Model model) {
		model.addAttribute("producto", new ProductDTO());
		return "formulario-nuevo";
	}
	
	@PostMapping("/grabar")
	public String save(ProductDTO productDTO, Model model) {
		this.restTemplate.postForEntity(this.apiUrl, productDTO, ProductDTO.class);
		ResponseEntity<ProductDTO[]> products = this.restTemplate.getForEntity(this.apiUrl, ProductDTO[].class);
		model.addAttribute("productos", products.getBody());
		return "productos";
	}
	
}
