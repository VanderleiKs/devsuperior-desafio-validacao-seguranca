package com.devsuperior.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.repositories.CityRepository;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Transactional(readOnly = true)
    public List<CityDTO> findAllCities(){
        var cities = cityRepository.findAll(Sort.by("name"));
        return cities.stream().map(c -> new CityDTO(c)).toList();
    }
    

    @Transactional
    public CityDTO insertCity(CityDTO dto){
            var city = new City(null, dto.getName());
            city = cityRepository.save(city);
        return new CityDTO(city);
    }

}
