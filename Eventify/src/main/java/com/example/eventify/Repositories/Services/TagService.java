package com.example.eventify.Repositories.Services;

import com.example.eventify.DTO.CreateTag.CreateTagModel;
import com.example.eventify.DTO.CreateTag.TagResponse;
import com.example.eventify.DTO.Event.EventResponse;
import com.example.eventify.Entities.Event;
import com.example.eventify.Entities.Tag;
import com.example.eventify.Kernel.Constants.Constants;
import com.example.eventify.Kernel.GenericResponse.ApiResponse.ApiResponse;
import com.example.eventify.Repositories.Interfaces.ITagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    private final ITagRepository _tagRepository;
    private final ModelMapper _mapper;

    @Autowired
    public TagService(ITagRepository tagRepository, ModelMapper mapper) {
        _tagRepository = tagRepository;
        _mapper = mapper;
    }

    public ResponseEntity<ApiResponse<String>> CreateTag(CreateTagModel model){
        if (model.getTagName().isEmpty()) {
            return new ResponseEntity<>(new ApiResponse<>(Constants.TagNameNull), HttpStatus.BAD_REQUEST);
        }

        Tag tag = new Tag();
        tag.setTagName(model.getTagName());

        _tagRepository.save(tag);

        return new ResponseEntity<>(new ApiResponse<>(), HttpStatus.OK);
    }


    public ResponseEntity<ApiResponse<List<TagResponse>>> GetAllTags(){
        List<Tag> tags = _tagRepository.findAll();

        List<TagResponse> response = tags.stream()
                .map(tag -> _mapper.map(tag, TagResponse.class))
                .toList();


        return new ResponseEntity<>(new ApiResponse<>(response), HttpStatus.OK);
    }


    public ResponseEntity<ApiResponse<TagResponse>> GetTagById(Long Id){
        Optional<Tag> tag = _tagRepository.findById(Id);

        if (tag.isEmpty()){
            return new ResponseEntity<>(new ApiResponse<>(Constants.InvalidTag), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ApiResponse<>(_mapper.map(tag.get(), TagResponse.class)), HttpStatus.OK);
    }

}
