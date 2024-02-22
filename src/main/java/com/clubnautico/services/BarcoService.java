package com.clubnautico.services;

import java.util.List;

import com.clubnautico.dto.BarcoDTO;

public interface BarcoService {
	
	public BarcoDTO save(BarcoDTO barcoDTO);
	
	public List<BarcoDTO> findAll();
	
	public BarcoDTO findById(long id);
	
	public BarcoDTO update(long id, BarcoDTO barcoDTO);
	
	public void delete(long id);
	
	public List<BarcoDTO> findBySocioId(long id);
	
	public List<BarcoDTO> findByPatronId(long id);
	

}
