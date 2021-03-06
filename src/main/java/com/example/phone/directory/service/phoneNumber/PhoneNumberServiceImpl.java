package com.example.phone.directory.service.phoneNumber;

import java.util.ArrayList;
import java.util.List;

import com.example.phone.directory.dto.country.CountryDTO;
import com.example.phone.directory.dto.page.PhoneNumberPageDTO;
import com.example.phone.directory.dto.phone.PhoneNumberDTO;
import com.example.phone.directory.service.country.CountryService;
import com.example.phone.directory.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.phone.directory.helper.validator.PhoneNumberValidator;
import com.example.phone.directory.mappers.phone.PhoneMapper;
import com.example.phone.directory.model.country.Country;
import com.example.phone.directory.model.customer.Customer;
import com.example.phone.directory.model.search.SearchObject;

@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {

	@Autowired
	CustomerService customerService;
	
	@Autowired
	CountryService countryService;

	@Autowired
	PhoneNumberValidator phoneNumberValidator;
	
	@Autowired
	PhoneMapper phoneMapper;
	
	public PhoneNumberPageDTO getAllPhoneNumbers(Integer page, Integer size)
	{
		Page<Customer> customers;
		Pageable pageable = Pageable.unpaged();
		if(page == null || size == null)
			 customers = customerService.getCustomerPaged(pageable);
		else {
			pageable = PageRequest.of(page, size);
			customers = customerService.getCustomerPaged(pageable);
		}
		return getPhoneNumbersFromCustomers(customers);
	}
	
	
	public PhoneNumberPageDTO getPhoneNumbers(Integer page, Integer size, SearchObject search){
		if(search ==  null)
				return getAllPhoneNumbers(page, size);
		else {
			Pageable pageable ;
			
			if(page == null || size == null)
				pageable = Pageable.unpaged();
			else
				pageable = PageRequest.of(page, size);
			
			Page<Customer> customersPage =  customerService.getCustomersPagedAndFiltered(pageable, search);
			return getPhoneNumbersFromCustomers(customersPage);
			 
		}
	}
	
	private PhoneNumberPageDTO getPhoneNumbersFromCustomers(Page<Customer> customers){
		List<PhoneNumberDTO> phoneNumbers = new ArrayList<PhoneNumberDTO>();
		for(Customer customer: customers.getContent()) 
		{
			Country country = customer.getCountry();
			PhoneNumberDTO phoneNumberDTO = phoneMapper.phoneNumberToPhoneNumberDTO(customer.getPhoneNumber(), country);
			phoneNumbers.add(phoneNumberDTO);
		}
		
		return phoneMapper.phoneNumberDtosToPhoneNumberPageDTO(customers.getTotalElements(), (long)customers.getTotalPages(), phoneNumbers);
	}
	
}
