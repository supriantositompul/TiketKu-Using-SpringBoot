package com.kelempok7.serverapp.service.impl;

import com.kelempok7.serverapp.handler.NotFoundExceptionHandler;
import com.kelempok7.serverapp.models.dto.request.StudioRequest;
import com.kelempok7.serverapp.models.dto.response.CountEntityResponse;
import com.kelempok7.serverapp.models.entity.Studio;
import com.kelempok7.serverapp.repository.StudioRepository;
import com.kelempok7.serverapp.service.GenericServiceDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudioServiceImpl implements GenericServiceDto<Studio,Integer, StudioRequest> {

    private StudioRepository studioRepository;

    @Override
    public List<Studio> getAll() {
        return studioRepository.findAll();
    }

    @Override
    public Studio getById(Integer id) {
        Optional<Studio> optionalStudio =studioRepository.findById(id);
        if(!optionalStudio.isPresent()){
            throw new NotFoundExceptionHandler("Studio is Not Available");
        }
        return optionalStudio.get();
    }

    @Override
    public Studio create(StudioRequest studioRequest) {
        Studio studio = new Studio();
        studio.setName(studioRequest.getName());
        return studioRepository.save(studio);
    }

    @Override
    public Studio update(Integer id,StudioRequest studioRequest) {
        getById(id);
        Studio studio = new Studio();
        studio.setId(id);
        studio.setName(studioRequest.getName());
        return studioRepository.save(studio);
    }

    @Override
    public Studio delete(Integer id) {
        Studio studio = getById(id);
        studioRepository.delete(studio);
        return studio;
    }

    public CountEntityResponse countStudio(){
        return new CountEntityResponse("Studio",studioRepository.count());
    }

    public Studio searchByName(String name){
        return studioRepository.findByName(name);
    }

    public List<Studio> searchStudioByName(String keyword){
        return studioRepository.searchStudioByKeyword(keyword);
    }
}
