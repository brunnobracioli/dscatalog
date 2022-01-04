package com.bbtech.dscatalog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbtech.dscatalog.dto.CategoryDTO;
import com.bbtech.dscatalog.entities.Category;
import com.bbtech.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	/*
	 A camada de serviço precisa retornar um DTO e para isso recebemos um dado do repository da entidade
	 Category e convertemos para uma lista de CategoryDTO.
	 */
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		List<Category> list = repository.findAll();	
		
		//solução para converter uma lista de Category para uma lista de CategoryDTO
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		
		/*
		outra solução para converter uma lista de Category para uma lista de CategoryDTO
		List<CategoryDTO> listDto = new ArrayList<>();
		for(Category cat : list) {
			listDto.add(new CategoryDTO(cat));
		}
		
		return listDto;
		*/
	}

}
