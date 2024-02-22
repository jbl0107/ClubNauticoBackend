package com.clubnautico.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubnautico.dto.SocioDTO;
import com.clubnautico.entities.Socio;
import com.clubnautico.exceptions.ResourceNotFoundException;
import com.clubnautico.exceptions.SocioBarcosException;
import com.clubnautico.repositories.BarcoRepository;
import com.clubnautico.repositories.SocioRepository;
import com.clubnautico.services.SocioService;

@Service
public class SocioServiceImpl implements SocioService{
	
	@Autowired
	private SocioRepository socioRepositoryImpl;
	
	@Autowired
	private BarcoRepository barcoRepositoryImpl;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private final String notFound = "Este socio no existe";

	
	
	@Override
	public SocioDTO save(SocioDTO socioDTO) {
		Socio socio = mappingDTOToSocio(socioDTO);
		Socio newSocio = socioRepositoryImpl.save(socio);
		return mappingSocioToDTO(newSocio);
	}

	@Override
	public List<SocioDTO> findAll() {
		List<Socio> socios = socioRepositoryImpl.findAll();
		List<SocioDTO> res = new ArrayList<>();
		for(Socio s: socios) {
			res.add(mappingSocioToDTO(s));
			
		}
		return res;
	}

	@Override
	public SocioDTO findById(long id) {
		Optional<Socio> socio = socioRepositoryImpl.findById(id);
		SocioDTO res;
		if(!socio.isPresent()) {
			throw new ResourceNotFoundException(notFound);
		}else {
			res = mappingSocioToDTO(socio.get());
		}
		
		return res;
	}

	@Override
	public SocioDTO update(long id, SocioDTO socioDTO) {
		Optional<Socio> socio = socioRepositoryImpl.findById(id);
		SocioDTO res;
		if(!socio.isPresent()) {
			throw new ResourceNotFoundException(notFound);
		}else {
			Socio aux = socio.get();
			aux.setDNI(socioDTO.getDNI());
			aux.setNombre(socioDTO.getNombre());
			aux.setApellidos(socioDTO.getApellidos());
			aux.setEdad(socioDTO.getEdad());
			aux.setFechaNacimiento(socioDTO.getFechaNacimiento());
			aux.setNumTelefono(socioDTO.getNumTelefono());
			aux.setNumeroSocio(socioDTO.getNumeroSocio());
			
			socioRepositoryImpl.save(aux);
			res = mappingSocioToDTO(socio.get());
		}
		
		return res;
		
	}

	@Override
	public void delete(long id) {
		Optional<Socio> socio = socioRepositoryImpl.findById(id);
		if(!socio.isPresent()) {
			throw new ResourceNotFoundException(notFound);
			
		}
		else if (!barcoRepositoryImpl.findBySocioId(id).isEmpty()) {
			throw new SocioBarcosException("No es posible eliminar un socio que tiene barcos asociados. Por favor, "
					+ "elimine primero los barcos correspondientes.");
			
		}else {
			socioRepositoryImpl.delete(socio.get());
		}
	
		
	}
	
	
	
	private SocioDTO mappingSocioToDTO(Socio socio) {
		SocioDTO socioDTO = modelMapper.map(socio, SocioDTO.class);
		return socioDTO;
		
	}
	
	private Socio mappingDTOToSocio(SocioDTO socioDTO) {
		Socio socio = modelMapper.map(socioDTO, Socio.class);
		return socio;
	}
	
	

}
