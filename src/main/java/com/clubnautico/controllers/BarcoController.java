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

import com.clubnautico.dto.BarcoDTO;
import com.clubnautico.services.BarcoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/barcos")
public class BarcoController {
	
	@Autowired
	private BarcoService barcoServiceImpl;
	
	@PostMapping
	public ResponseEntity<BarcoDTO> save(@RequestBody @Valid BarcoDTO barcoDTO){
		return new ResponseEntity<>(barcoServiceImpl.save(barcoDTO), HttpStatus.CREATED);
	}
	
	@GetMapping
	public List<BarcoDTO> findAll(){
		return barcoServiceImpl.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BarcoDTO> findById(@PathVariable(name = "id") long id) {
		return new ResponseEntity<>(barcoServiceImpl.findById(id), HttpStatus.OK);
	}
	
	@GetMapping("/socio/{id}")
	public ResponseEntity<List<BarcoDTO>> findBySocioId(@PathVariable(name = "id") long id) {
		return new ResponseEntity<>(barcoServiceImpl.findBySocioId(id), HttpStatus.OK);
	}
	
	@GetMapping("/patron/{id}")
	public ResponseEntity<List<BarcoDTO>> findByPatronId(@PathVariable(name = "id") long id) {
		return new ResponseEntity<>(barcoServiceImpl.findByPatronId(id), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<BarcoDTO> update(@PathVariable(name = "id") long id, @RequestBody @Valid 
			BarcoDTO barcoDTO){
		return new ResponseEntity<>(barcoServiceImpl.update(id, barcoDTO), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
		barcoServiceImpl.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	

}
