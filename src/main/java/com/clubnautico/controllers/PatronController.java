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

import com.clubnautico.dto.PatronDTO;
import com.clubnautico.services.PatronService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/patrones")
public class PatronController {
	
	@Autowired
	private PatronService patronServiceImpl;
	
	@PostMapping
	public ResponseEntity<PatronDTO> save(@RequestBody @Valid PatronDTO patronDTO){
		return new ResponseEntity<>(patronServiceImpl.save(patronDTO), HttpStatus.CREATED);
	}
	
	
	@GetMapping
	public List<PatronDTO> findAll(){
		return patronServiceImpl.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PatronDTO> findById(@PathVariable(name = "id") long id){
		return new ResponseEntity<>(patronServiceImpl.findById(id), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PatronDTO> update(@PathVariable(name = "id") long id, @RequestBody @Valid PatronDTO patronDTO){
		return new ResponseEntity<>(patronServiceImpl.update(id, patronDTO), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") long id){
		patronServiceImpl.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
