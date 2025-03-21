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
		model.addAttribute("titulo", "Agregar nuevo producto");
		model.addAttribute("accion", "/grabar");
		model.addAttribute("textoBoton", "Grabar");
		return "formulario-nuevo";
	}
	
	@PostMapping("/grabar")
	public String save(ProductDTO productDTO, Model model) {
		this.restTemplate.postForEntity(this.apiUrl, productDTO, ProductDTO.class);
//		ResponseEntity<ProductDTO[]> products = this.restTemplate.getForEntity(this.apiUrl, ProductDTO[].class);
//		model.addAttribute("productos", products.getBody());
//		return "productos";
		return "redirect:/";
	}
	
	
	@GetMapping("/editar/{id}")
	public String showFormToEdit(@PathVariable Long id, Model model) {
		ResponseEntity<ProductDTO> product = this.restTemplate.getForEntity(this.apiUrl + '/' + id, ProductDTO.class);
		model.addAttribute("producto", product.getBody());
		model.addAttribute("titulo", "Editar producto");
		model.addAttribute("accion", "/actualizar");
		model.addAttribute("textoBoton", "Editar datos");
		return "formulario-nuevo";
	}
	
	@PostMapping("/actualizar")
	public String update(ProductDTO productDTO) {
		this.restTemplate.put(this.apiUrl + '/' + productDTO.getId(), productDTO);
		return "redirect:/";
	}
	
	
	@GetMapping("/eliminar/{id}")
	public String showFormToDelete(@PathVariable Long id, Model model) {
		ResponseEntity<ProductDTO> product = this.restTemplate.getForEntity(this.apiUrl + '/' + id, ProductDTO.class);
		model.addAttribute("producto", product.getBody());
		model.addAttribute("titulo", "Eliminar producto");
		model.addAttribute("accion", "/borrar/" + product.getBody().getId());
		model.addAttribute("textoBoton", "Eliminar");
		return "formulario-nuevo";
	}
	
	@PostMapping("/borrar/{id}")
	public String delete(@PathVariable Long id) {
		this.restTemplate.delete(this.apiUrl + '/' + id);
		return "redirect:/";
	}
	
}
