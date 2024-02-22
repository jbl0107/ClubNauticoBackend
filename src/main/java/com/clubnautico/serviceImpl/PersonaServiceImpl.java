package com.clubnautico.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubnautico.dto.PersonaDTO;
import com.clubnautico.entities.Patron;
import com.clubnautico.entities.Persona;
import com.clubnautico.entities.Socio;
import com.clubnautico.exceptions.PersonaConRolException;
import com.clubnautico.exceptions.ResourceNotFoundException;
import com.clubnautico.repositories.BarcoRepository;
import com.clubnautico.repositories.PersonaRepository;
import com.clubnautico.services.PersonaService;

@Service
public class PersonaServiceImpl implements PersonaService {

	@Autowired
	private PersonaRepository personaRepositoryImpl;
	
	@Autowired
	private BarcoRepository barcoRepositoryImpl;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private final String notFound = "Esta persona no existe";

	@Override
	public PersonaDTO save(PersonaDTO personaDTO) {

		
		Persona persona = mappingDTOToPersona(personaDTO);
		Persona newPersona = personaRepositoryImpl.save(persona);

		PersonaDTO personaRespuesta = mappingPersonaToDTO(newPersona);

		return personaRespuesta;
	}

	@Override
	public List<PersonaDTO> findAll() {
		List<Persona> personas = personaRepositoryImpl.findAll();
		
		List<PersonaDTO> res = new ArrayList<>();
		
		for(Persona p: personas) {
			PersonaDTO personaDTO = mappingPersonaToDTO(p);
			res.add(personaDTO);
		}
		
		return res;
		
	}
	
	
	@Override
	public PersonaDTO findById(long id) {
		Optional<Persona> persona = personaRepositoryImpl.findById(id);
		PersonaDTO res;
		
		if(!persona.isPresent()) {
			throw new ResourceNotFoundException(notFound);
		}else {
			res = mappingPersonaToDTO(persona.get());
		}
		
		return res;
		
	}
	
	
	@Override
	public PersonaDTO update(PersonaDTO personaDTO, long id) {
		Optional<Persona> personaOp = personaRepositoryImpl.findById(id);
		PersonaDTO res;
		
		if(!personaOp.isPresent()) {
			throw new ResourceNotFoundException(notFound);
			
		}else {
			Persona persona = personaOp.get();
			persona.setDNI(personaDTO.getDNI());
			persona.setNombre(personaDTO.getNombre());
			persona.setApellidos(personaDTO.getApellidos());
			persona.setEdad(personaDTO.getEdad());
			persona.setNumTelefono(persona.getNumTelefono());
			persona.setFechaNacimiento(personaDTO.getFechaNacimiento());
			
			personaRepositoryImpl.save(persona);
			res = mappingPersonaToDTO(persona);
		}
		
		
		return res;
		
	}
	
	
	
	@Override
	public void delete(long id) {
		Optional<Persona> persona = personaRepositoryImpl.findById(id);
		if(!persona.isPresent()) {
			throw new ResourceNotFoundException(notFound);
			
		}
		else if(persona.get() instanceof Patron && !barcoRepositoryImpl.findByPatronId(id).isEmpty()) {
			throw new PersonaConRolException("Esta persona está registrada como patrón y posee uno o mas barcos. "
					+ "Por favor, borre en primer lugar los barcos correspondientes");
			
		}
		else if(persona.get() instanceof Socio && !barcoRepositoryImpl.findBySocioId(id).isEmpty()) {
			throw new PersonaConRolException("Esta persona está registrada como socio y posee uno o mas barcos. "
					+ "Por favor, borre en primer lugar los barcos correspondientes");
			
		}else {
			personaRepositoryImpl.delete(persona.get());
		}
		
	}
	
	
	

	// Mapeamos de DTO a entidad
	private Persona mappingDTOToPersona(PersonaDTO personaDTO) {
		Persona persona = modelMapper.map(personaDTO, Persona.class);
		return persona;
	}

	
	// Mapeamos de entidad a DTO
	private PersonaDTO mappingPersonaToDTO(Persona persona) {
		PersonaDTO personaDTO = modelMapper.map(persona, PersonaDTO.class);
		return personaDTO;

	}




}
