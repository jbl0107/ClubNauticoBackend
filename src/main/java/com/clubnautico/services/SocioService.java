package com.clubnautico.services;

import java.util.List;

import com.clubnautico.dto.SocioDTO;

public interface SocioService {

	public SocioDTO save(SocioDTO socioDTO);

	public List<SocioDTO> findAll();

	public SocioDTO findById(long id);

	public SocioDTO update(long id, SocioDTO socioDTO);

	public void delete(long id);

}
