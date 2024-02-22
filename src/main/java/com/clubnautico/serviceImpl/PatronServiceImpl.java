package com.clubnautico.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubnautico.dto.PatronDTO;
import com.clubnautico.entities.Patron;
import com.clubnautico.exceptions.PatronBarcosException;
import com.clubnautico.exceptions.PatronSalidasException;
import com.clubnautico.exceptions.ResourceNotFoundException;
import com.clubnautico.repositories.BarcoRepository;
import com.clubnautico.repositories.PatronRepository;
import com.clubnautico.repositories.SalidaRepository;
import com.clubnautico.services.PatronService;

@Service
public class PatronServiceImpl implements PatronService{
	
	
	@Autowired
	private PatronRepository patronRepositoryImpl;
	
	@Autowired
	private BarcoRepository barcoRepositoryImpl;
	
	@Autowired
	private SalidaRepository salidaRepositoryImpl;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private final String notFound = "Este patron no existe";
	
	
	
	@Override
	public PatronDTO save(PatronDTO patronDTO) {
		
		Patron patron = mappingDTOToPatron(patronDTO);
		Patron newPatron = patronRepositoryImpl.save(patron);
		return mappingPatronToDTO(newPatron);
		
	}
	
	
	@Override
	public List<PatronDTO> findAll() {
		List<Patron> patrones = patronRepositoryImpl.findAll();
		List<PatronDTO> res = new ArrayList<>();
		for(Patron p: patrones) {
			PatronDTO pDTO = mappingPatronToDTO(p);
			res.add(pDTO);
		}
		return res;
	}
	
	@Override
	public PatronDTO findById(long id) {
		Optional<Patron> patron = patronRepositoryImpl.findById(id);
		PatronDTO patronDTO;
		if(!patron.isPresent()) {
			throw new ResourceNotFoundException(notFound);
		}else {
			patronDTO = mappingPatronToDTO(patron.get());
		}
		
		return patronDTO;
		
		
	}
	
	@Override
	public PatronDTO update(long id, PatronDTO patronDTO) {
		Optional<Patron> patron = patronRepositoryImpl.findById(id);
		PatronDTO res;
		if(!patron.isPresent()) {
			throw new ResourceNotFoundException(notFound);
			
		}else {
			Patron aux = patron.get();
			aux.setDNI(patronDTO.getDNI());
			aux.setNombre(patronDTO.getNombre());
			aux.setApellidos(patronDTO.getApellidos());
			aux.setEdad(patronDTO.getEdad());
			aux.setFechaNacimiento(patronDTO.getFechaNacimiento());
			aux.setNumTelefono(patronDTO.getNumTelefono());
			
			patronRepositoryImpl.save(aux);
			res = mappingPatronToDTO(aux);
			
			
		}
		return res;
	}
	
	
	@Override
	public void delete(long id) {
		Optional<Patron> patron = patronRepositoryImpl.findById(id);
		if(!patron.isPresent()) {
			throw new ResourceNotFoundException(notFound);
			
		}
		else if (!barcoRepositoryImpl.findByPatronId(id).isEmpty()) {
			throw new PatronBarcosException("No es posible eliminar un patrón que tiene barcos asociados. Por favor, "
					+ "elimine primero los barcos correspondientes.");
			
		}
		else if(!salidaRepositoryImpl.findByPatronId(id).isEmpty()) {
			throw new PatronSalidasException("No es posible eliminar un patrón que tiene salidas asociadas. Por favor, "
					+ "elimine primero las salidas correspondientes.");
			
			
		}else {
			patronRepositoryImpl.delete(patron.get());
		}
		
	}
	
	
	
	
	
	private PatronDTO mappingPatronToDTO(Patron patron) {
		PatronDTO patronDTO = modelMapper.map(patron, PatronDTO.class);
		return patronDTO;
		
	}
	
	
	private Patron mappingDTOToPatron(PatronDTO patronDTO) {
		Patron patron = modelMapper.map(patronDTO, Patron.class);
		return patron;
		
	}


	
	


	

}
