package com.clubnautico.services;

import java.util.List;

import com.clubnautico.dto.PersonaDTO;

public interface PersonaService {
	
	public PersonaDTO save(PersonaDTO personaDTO);
	
	public List<PersonaDTO> findAll();
	
	public PersonaDTO findById(long id);
	
	public PersonaDTO update(PersonaDTO personaDTO, long id);
	
	public void delete(long id);

}
