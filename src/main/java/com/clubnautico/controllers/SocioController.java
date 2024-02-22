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

import com.clubnautico.dto.SocioDTO;
import com.clubnautico.services.SocioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/socios")
public class SocioController {
	
	@Autowired
	private SocioService socioServiceImpl;
	
	@PostMapping
	public ResponseEntity<SocioDTO> save(@RequestBody @Valid SocioDTO socioDTO){
		return new ResponseEntity<>(socioServiceImpl.save(socioDTO), HttpStatus.CREATED);
	}
	
	
	@GetMapping
	public List<SocioDTO> findAll(){
		return socioServiceImpl.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SocioDTO> findById(@PathVariable(name = "id") long id){
		return new ResponseEntity<>(socioServiceImpl.findById(id), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<SocioDTO> update(@PathVariable(name = "id") long id, @RequestBody @Valid SocioDTO socioDTO){
		return new ResponseEntity<>(socioServiceImpl.update(id, socioDTO), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") long id){
		socioServiceImpl.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
