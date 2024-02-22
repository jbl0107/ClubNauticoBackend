package com.clubnautico.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubnautico.dto.SalidaDTO;
import com.clubnautico.entities.Barco;
import com.clubnautico.entities.Patron;
import com.clubnautico.entities.Salida;
import com.clubnautico.exceptions.ResourceNotFoundException;
import com.clubnautico.repositories.BarcoRepository;
import com.clubnautico.repositories.PatronRepository;
import com.clubnautico.repositories.SalidaRepository;
import com.clubnautico.services.SalidaService;

@Service
public class SalidaServiceImpl implements SalidaService{

	
	@Autowired
	private SalidaRepository salidaRepositoryImpl;
	
	@Autowired
	private PatronRepository patronRepositoryImpl;
	
	@Autowired
	private BarcoRepository barcoRepositoryImpl;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private final String notFound = "Esta salida no existe";
	
	
	
	@Override
	public SalidaDTO save(SalidaDTO salidaDTO) {
		Salida salida = mappingDTOToSalida(salidaDTO);
		Salida newSalida = salidaRepositoryImpl.save(salida);
		
		SalidaDTO salidaRespuesta = mappingSalidaToDTO(newSalida);
		
		return salidaRespuesta;
	}

	@Override
	public List<SalidaDTO> findAll() {
		List<Salida> salidas = salidaRepositoryImpl.findAll();
		List<SalidaDTO> res = new ArrayList<>();
		for(Salida s: salidas) {
			res.add(mappingSalidaToDTO(s));
		}
		return res;
	}
	

	@Override
	public SalidaDTO findById(long id) {
		Optional<Salida> salida = salidaRepositoryImpl.findById(id);
		SalidaDTO salidaDTO;
		
		if(!salida.isPresent()) {
			throw new ResourceNotFoundException(notFound);
		}else {
			salidaDTO = mappingSalidaToDTO(salida.get());
		}
		
		return salidaDTO;
	}
	
	@Override
	public List<SalidaDTO> findByBarcoId(long id) {
		Optional<Barco> barco = barcoRepositoryImpl.findById(id);
		List<SalidaDTO> res = new ArrayList<>();
		
		if(!barco.isPresent()) {
			throw new ResourceNotFoundException("Este barco no existe");
		}else {
			List<Salida> salidas = salidaRepositoryImpl.findByBarcoId(id);
			
			for(Salida s: salidas) {
				res.add(mappingSalidaToDTO(s));
			}
		}
		
		return res;
	}

	
	@Override
	public SalidaDTO update(long id, SalidaDTO salidaDTO) {
		Optional<Salida> salida = salidaRepositoryImpl.findById(id);
		SalidaDTO res;
		
		
		if(!salida.isPresent()) {
			throw new ResourceNotFoundException(notFound);
		}else {
			
			Optional<Patron> patron = patronRepositoryImpl.findById(salidaDTO.getPatronId());
			Optional<Barco> barco = barcoRepositoryImpl.findById(salidaDTO.getBarcoId());
			
			if(!patron.isPresent()) {
				throw new ResourceNotFoundException("El patron especificado no existe");
			}
			else if (!barco.isPresent()) {
				throw new ResourceNotFoundException("El barco especificado no existe");
			}
			
			
			Salida aux = salida.get();
			aux.setDestino(salidaDTO.getDestino());
			aux.setFechaSalida(salidaDTO.getFechaSalida());
			aux.setBarco(barco.get());
			aux.setPatron(patron.get());
			
			salidaRepositoryImpl.save(aux);
			res = mappingSalidaToDTO(aux);
			
		}
		
		return res;
	}

	@Override
	public void delete(long id) {
		Optional<Salida> salida = salidaRepositoryImpl.findById(id);
		if(!salida.isPresent()) {
			throw new ResourceNotFoundException(notFound);
		}else {
			salidaRepositoryImpl.delete(salida.get());
		}
		
		
	}
	
	
	private SalidaDTO mappingSalidaToDTO(Salida salida) {
		SalidaDTO salidaDTO = modelMapper.map(salida, SalidaDTO.class);
		return salidaDTO;
	}
	
	
	private Salida mappingDTOToSalida(SalidaDTO salidaDTO) {
		Salida salida = modelMapper.map(salidaDTO, Salida.class);
		return salida;
	}

}
