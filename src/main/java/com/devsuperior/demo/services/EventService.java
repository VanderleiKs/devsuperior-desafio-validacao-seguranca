package com.devsuperior.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.entities.Event;
import com.devsuperior.demo.repositories.CityRepository;
import com.devsuperior.demo.repositories.EventRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CityRepository cityRepository;

    @Transactional(readOnly = true)
    public Page<EventDTO> findAll(Pageable pageable){
        var events = eventRepository.findAll(pageable);
        return new PageImpl<>(events.stream().map(e -> new EventDTO(e)).toList());
    }
    

    @Transactional
    public EventDTO insert(EventDTO dto){
        City city = cityRepository.getReferenceById(dto.getCityId());
            var event = new Event();
            event.setName(dto.getName());
            event.setDate(dto.getDate());
            event.setUrl(dto.getUrl());
            event.setCity(city);
            event = eventRepository.save(event);
        return new EventDTO(event);
    }

}
