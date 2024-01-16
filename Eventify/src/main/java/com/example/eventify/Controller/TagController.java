package com.example.eventify.Controller;

import com.example.eventify.DTO.CreateTag.CreateTagModel;
import com.example.eventify.DTO.CreateTag.TagResponse;
import com.example.eventify.Kernel.GenericResponse.ApiResponse.ApiResponse;
import com.example.eventify.Repositories.Services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    private final TagService _tagService;

    @Autowired
    public TagController(TagService _tagService) {
        this._tagService = _tagService;
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<String>> CreateTag(@RequestBody CreateTagModel model){
        return _tagService.CreateTag(model);
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<List<TagResponse>>> GetAllTags(){
        return _tagService.GetAllTags();
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<TagResponse>> GetVenueById(@PathVariable Long id){
        return _tagService.GetTagById(id);
    }
}
