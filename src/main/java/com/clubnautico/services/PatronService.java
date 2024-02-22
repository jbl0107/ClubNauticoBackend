package com.clubnautico.services;

import java.util.List;

import com.clubnautico.dto.PatronDTO;


public interface PatronService {
	
	public PatronDTO save(PatronDTO patronDTO);
	
	public List<PatronDTO> findAll();
	
	public PatronDTO findById(long id);

	public PatronDTO update(long id, PatronDTO patronDTO);
	
	public void delete(long id);
}
