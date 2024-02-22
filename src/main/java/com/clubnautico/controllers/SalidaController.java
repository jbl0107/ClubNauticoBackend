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

import com.clubnautico.dto.SalidaDTO;
import com.clubnautico.services.SalidaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/salidas")
public class SalidaController {
	
	@Autowired
	private SalidaService salidaServiceImpl;
	
	@PostMapping
	public ResponseEntity<SalidaDTO> save(@RequestBody @Valid SalidaDTO salidaDTO){
		return new ResponseEntity<>(salidaServiceImpl.save(salidaDTO), HttpStatus.CREATED);
	}
	
	@GetMapping
	public List<SalidaDTO> findAll(){
		return salidaServiceImpl.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SalidaDTO> findById(@PathVariable(name = "id") long id){
		return new ResponseEntity<>(salidaServiceImpl.findById(id), HttpStatus.OK);
	}
	
	@GetMapping("/barco/{id}")
	public ResponseEntity<List<SalidaDTO>> findByBarcoId(@PathVariable(name = "id") long id){
		return new ResponseEntity<>(salidaServiceImpl.findByBarcoId(id), HttpStatus.OK);
	}
	
	@GetMapping("/patron/{id}")
	public ResponseEntity<List<SalidaDTO>> findByPatronId(@PathVariable(name = "id") long id){
		return new ResponseEntity<>(salidaServiceImpl.findByPatronId(id), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<SalidaDTO> update(@RequestBody @Valid SalidaDTO salidaDTO, 
			@PathVariable(name = "id") long id){
		return new ResponseEntity<>(salidaServiceImpl.update(id, salidaDTO), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
		salidaServiceImpl.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
