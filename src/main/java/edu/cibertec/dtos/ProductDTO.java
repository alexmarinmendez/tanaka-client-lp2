package edu.cibertec.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductDTO {

	private Long id;
	private String name;
	private String description;
	private int stock;
	private Double price;
	private String image;
	
}
