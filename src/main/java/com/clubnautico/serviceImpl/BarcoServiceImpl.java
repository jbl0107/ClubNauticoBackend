package com.clubnautico.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubnautico.dto.BarcoDTO;
import com.clubnautico.entities.Barco;
import com.clubnautico.entities.Patron;
import com.clubnautico.entities.Socio;
import com.clubnautico.exceptions.BarcoSalidasException;
import com.clubnautico.exceptions.OwnerBarcoException;
import com.clubnautico.exceptions.ResourceNotFoundException;
import com.clubnautico.repositories.BarcoRepository;
import com.clubnautico.repositories.PatronRepository;
import com.clubnautico.repositories.SalidaRepository;
import com.clubnautico.repositories.SocioRepository;
import com.clubnautico.services.BarcoService;

@Service
public class BarcoServiceImpl implements BarcoService {

	@Autowired
	private BarcoRepository barcoRepositoryImpl;

	@Autowired
	private PatronRepository patronRepositoryImpl;
	
	@Autowired
	private SocioRepository socioRepositoryImpl;
	
	@Autowired
	private SalidaRepository salidaRepositoryImpl;

	@Autowired
	private ModelMapper modelMapper;

	private final String notFound = "Este barco no existe";
	
	
	

	@Override
	public BarcoDTO save(BarcoDTO barcoDTO) {

		Barco barco = mappingDTOToBarco(barcoDTO);
		Barco newBarco;
		
		if(barcoDTO.getPatronId() != null && barcoDTO.getSocioId() != null) {
			throw new OwnerBarcoException("Un barco no puede tener dos propietarios");
		}
		
		else if(barcoDTO.getPatronId() != null) {
			Optional<Patron> patron = patronRepositoryImpl.findById(barcoDTO.getPatronId());
			if (!patron.isPresent()) {
				throw new ResourceNotFoundException("El patron especificado no existe");
			} else {
				barco.setPatron(patron.get());
				newBarco = barcoRepositoryImpl.save(barco);
			}

		}
		else if(barcoDTO.getSocioId() != null) {
			Optional<Socio> socio = socioRepositoryImpl.findById(barcoDTO.getSocioId());
			if (!socio.isPresent()) {
				throw new ResourceNotFoundException("El socio especificado no existe");
			} else {
				barco.setSocio(socio.get());
				newBarco = barcoRepositoryImpl.save(barco);
			}
			
		}

		newBarco = barcoRepositoryImpl.save(barco);

		return mappingBarcoToDTO(newBarco);
	}

	@Override
	public List<BarcoDTO> findAll() {
		List<Barco> barcos = barcoRepositoryImpl.findAll();
		List<BarcoDTO> res = new ArrayList<>();

		for (Barco b : barcos) {
			res.add(mappingBarcoToDTO(b));
		}

		return res;
	}

	@Override
	public BarcoDTO findById(long id) {
		Optional<Barco> barco = barcoRepositoryImpl.findById(id);
		BarcoDTO res;
		if (!barco.isPresent()) {
			throw new ResourceNotFoundException(notFound);

		} else {
			res = mappingBarcoToDTO(barco.get());
		}

		return res;

	}

	@Override
	public BarcoDTO update(long id, BarcoDTO barcoDTO) {
		Optional<Barco> barco = barcoRepositoryImpl.findById(id);
		BarcoDTO res;
		if (!barco.isPresent()) {
			throw new ResourceNotFoundException(notFound);

		} else {
			
			if(barcoDTO.getPatronId() != null && barcoDTO.getSocioId() != null) {
				throw new OwnerBarcoException("Un barco no puede tener dos propietarios");
			}
			
			else if(barcoDTO.getPatronId() != null) {
				Optional<Patron> patron = patronRepositoryImpl.findById(barcoDTO.getPatronId());
				System.out.println(patron);
				if (!patron.isPresent()) {
					throw new ResourceNotFoundException("El patron especificado no existe");
				} else {
					Barco aux = barco.get();
					aux.setMatricula(barcoDTO.getMatricula());
					aux.setNombre(barcoDTO.getNombre());
					aux.setNumeroAmarre(barcoDTO.getNumeroAmarre());
					aux.setCuota(barcoDTO.getCuota());
					aux.setPatron(patron.get());
					aux.setSocio(null);
					barcoRepositoryImpl.save(aux);
					res = mappingBarcoToDTO(aux);
					
				}
				
			}
			else if(barcoDTO.getSocioId() != null) {
				
				Optional<Socio> socio = socioRepositoryImpl.findById(barcoDTO.getSocioId());
				if (!socio.isPresent()) {
					throw new ResourceNotFoundException("El socio especificado no existe");
				} else {
					Barco aux = barco.get();
					aux.setMatricula(barcoDTO.getMatricula());
					aux.setNombre(barcoDTO.getNombre());
					aux.setNumeroAmarre(barcoDTO.getNumeroAmarre());
					aux.setCuota(barcoDTO.getCuota());
					aux.setSocio(socio.get());
					aux.setPatron(null);
					barcoRepositoryImpl.save(aux);
					res = mappingBarcoToDTO(aux);
					
				}

				
			}
			else {
				Barco aux = barco.get();
				aux.setMatricula(barcoDTO.getMatricula());
				aux.setNombre(barcoDTO.getNombre());
				aux.setNumeroAmarre(barcoDTO.getNumeroAmarre());
				aux.setCuota(barcoDTO.getCuota());
				aux.setPatron(null);
				aux.setSocio(null);
				barcoRepositoryImpl.save(aux);
				res = mappingBarcoToDTO(aux);
			}
			

		}
		return res;
	}
	

	@Override
	public void delete(long id) {
		Optional<Barco> barco = barcoRepositoryImpl.findById(id);
		if (!barco.isPresent()) {
			throw new ResourceNotFoundException(notFound);

		} 
		else if(!salidaRepositoryImpl.findByBarcoId(id).isEmpty()) {
			
			throw new BarcoSalidasException("No es posible eliminar un barco que tiene salidas asociadas. Por favor, "
					+ "elimine primero las salidas correspondientes.");
			
			

			
		}else {
			barcoRepositoryImpl.delete(barco.get());
		}

	}

	private Barco mappingDTOToBarco(BarcoDTO barcoDTO) {
		Barco barco = modelMapper.map(barcoDTO, Barco.class);
		return barco;
	}

	private BarcoDTO mappingBarcoToDTO(Barco barco) {
		BarcoDTO barcoDTO = modelMapper.map(barco, BarcoDTO.class);
		return barcoDTO;
	}

	@Override
	public List<BarcoDTO> findBySocioId(long id) {
		List<BarcoDTO> res = new ArrayList<>();
		Optional<Socio> socio = socioRepositoryImpl.findById(id);
		if(!socio.isPresent()) {
			throw new ResourceNotFoundException("Este socio no existe");
			
		}else {
			List<Barco> barcos = barcoRepositoryImpl.findBySocioId(id);
			for(Barco b: barcos) {
				res.add(mappingBarcoToDTO(b));
			}
		}
		
		return res;
	}

	@Override
	public List<BarcoDTO> findByPatronId(long id) {
		List<BarcoDTO> res = new ArrayList<>();
		Optional<Patron> patron = patronRepositoryImpl.findById(id);
		if(!patron.isPresent()) {
			throw new ResourceNotFoundException("Este patrón no existe");
			
		}else {
			List<Barco> barcos = barcoRepositoryImpl.findByPatronId(id);
			for(Barco b: barcos) {
				res.add(mappingBarcoToDTO(b));
			}
		}
		
		return res;
	}

}
