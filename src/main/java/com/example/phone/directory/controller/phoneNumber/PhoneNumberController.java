package com.example.phone.directory.controller.phoneNumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.phone.directory.dto.page.PhoneNumberPageDTO;
import com.example.phone.directory.dto.phone.PhoneNumberDTO;
import com.example.phone.directory.model.search.SearchObject;
import com.example.phone.directory.service.phoneNumber.PhoneNumberServiceImpl;

import java.awt.PageAttributes.MediaType;
import java.util.List;

@RestController
@RequestMapping(value = "phoneNumber")
public class PhoneNumberController {

    @Autowired
    PhoneNumberServiceImpl phoneNumberService;

    @RequestMapping(method = RequestMethod.GET )
    public PhoneNumberPageDTO getAllPhoneNumbers(@RequestParam(required = false) Integer page, 
    		@RequestParam(required = false) Integer size){
        return phoneNumberService.getAllPhoneNumbers(page , size);
    }
   
    @RequestMapping(method = RequestMethod.POST, path = "search", consumes="application/json")
    public PhoneNumberPageDTO searchPhoneNumbers(@RequestParam(required = false) Integer page, 
    		@RequestParam(required = false) Integer size,
    		@RequestBody(required = false) SearchObject search){
        return phoneNumberService.getPhoneNumbers(page, size, search);
    }
    
    
    

}
