package com.clubnautico.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clubnautico.dto.PersonaDTO;
import com.clubnautico.services.PersonaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/personas") // url base

//configurar cors para q solo localhost:4200 pueda consumir y acceder a los endpoints d este controlador
//@CrossOrigin(origins = "http://localhost:4200") //puede usarse tbm a nivel d método del controlador
public class PersonaController {

	@Autowired
	private PersonaService personaServiceImpl;

	// RequestBody es el cuerpo de la peticion. Valid es para validar los datos introducidos
	@PostMapping
	public ResponseEntity<PersonaDTO> save(@RequestBody @Valid PersonaDTO personaDTO) {
		return new ResponseEntity<>(personaServiceImpl.save(personaDTO), HttpStatus.CREATED);

	}


	//Cnd un metodo no devuelve un responseEntity, siempre devuelve un 200 ok, como en este caso
	@GetMapping
	public List<PersonaDTO> findAll() {
		List<PersonaDTO> res = personaServiceImpl.findAll();
		return res;
	}

	
	// PathVariable hace referencia a un parametro de la ruta (del path). Deben
	// llamarse igual (en este caso, 'id' ambos)
	@GetMapping("/{id}")
	public ResponseEntity<PersonaDTO> findById(@PathVariable(name = "id") long id) {
		PersonaDTO res = personaServiceImpl.findById(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<PersonaDTO> update(@RequestBody @Valid PersonaDTO personaDTO, @PathVariable(name = "id") long id) {
		PersonaDTO res = personaServiceImpl.update(personaDTO, id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
		personaServiceImpl.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
