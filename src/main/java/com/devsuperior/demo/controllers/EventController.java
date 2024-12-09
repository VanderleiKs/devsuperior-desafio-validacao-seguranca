package com.devsuperior.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.services.EventService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;
    
    @GetMapping()
    public ResponseEntity<Page<EventDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(eventService.findAll(pageable));
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<EventDTO> insert(@Valid @RequestBody EventDTO dto) {
        var newDto = eventService.insert(dto);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();
        
        return ResponseEntity.created(uri).body(newDto);
    }
    
    
}
