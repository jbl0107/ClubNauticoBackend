package com.clubnautico.services;

import java.util.List;

import com.clubnautico.dto.SalidaDTO;


public interface SalidaService {
	
	public SalidaDTO save(SalidaDTO salidaDTO);

	public List<SalidaDTO> findAll();

	public SalidaDTO findById(long id);

	public SalidaDTO update(long id, SalidaDTO salidaDTO);

	public void delete(long id);

	public List<SalidaDTO> findByBarcoId(long id);

	public List<SalidaDTO> findByPatronId(long id);

}
