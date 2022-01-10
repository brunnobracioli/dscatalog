package com.bbtech.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbtech.dscatalog.dto.CategoryDTO;
import com.bbtech.dscatalog.entities.Category;
import com.bbtech.dscatalog.repositories.CategoryRepository;
import com.bbtech.dscatalog.services.exceptions.ResourceNotFoundException;

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
	
	//O Optional cria um objeto que não seja nulo.
	@Transactional(readOnly = true)
	public CategoryDTO findByid(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not Found!")); 
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
	}
	
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getById(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new CategoryDTO(entity);
		}
		catch (EntityNotFoundException e){
			throw new ResourceNotFoundException("Id not found");
		}
	}

}
