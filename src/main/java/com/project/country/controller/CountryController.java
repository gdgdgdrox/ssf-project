package com.project.country.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.project.country.model.Countries;
import com.project.country.model.Country;
import com.project.country.service.CountryService;


@Controller
public class CountryController {

    @Autowired
    private CountryService service;

    @PostConstruct
    public void prep(){
        boolean countryListExistsInDatabase = service.checkIfCountryListExists();
        if (!countryListExistsInDatabase){
            service.getCountries();
            service.saveCountryList(Countries.listOfAllCountries);
        }
        else {
            service.getCountryList();
        }
    }

    @GetMapping("/random-country")
    public String randomCountryPage(Model model){
        Country randomCountry = service.getRandomCountry();
        model.addAttribute("randomCountry", randomCountry);
        return "random-country";
    }


    @PostMapping("/save")
    public String returnRandomCountryPage(@RequestBody MultiValueMap<String,String> requestBody, Model model){
        String username = requestBody.get("username").get(0);
        String countryName = requestBody.get("countryName").get(0);
        Country country = service.findCountryByName(countryName);
        boolean userExists = service.checkIfUserExists(username);
        if (userExists){
            List<Country> userCountries = service.getUserCountriesFromDB(username);
            userCountries.add(country);
            service.saveUser(username, userCountries);
        }
        else{
            List<Country> userCountries = new ArrayList<>();
            userCountries.add(country);
            service.saveUser(username, userCountries);
        }
        model.addAttribute("randomCountry", service.getRandomCountry());
        return "random-country";
    }

    @GetMapping("/login")
    public String userCountryPage(){
        return "user/login";
    }

    @PostMapping("/userCountries")
    public String userCountryPage2(@RequestBody String body, Model model){
        String username = body.substring(body.indexOf("=")+1);
        boolean userExists = service.checkIfUserExists(username);
        if (userExists){
            model.addAttribute("userCountries", service.getUserCountriesFromDB(username));
            model.addAttribute("username", username);
            return "user/user-countries";
        }
        model.addAttribute("username", username);
        return "userDoesNotExist";

    }

    @DeleteMapping("/delete/{username}/{countryName}")
    public String userCountryPage2(@PathVariable String username, @PathVariable String countryName, Model model){
        List<Country> userCountries = service.getUserCountriesFromDB(username);
        userCountries.removeIf(c -> (c.getName()).equalsIgnoreCase(countryName));
        service.saveUser(username, userCountries);
        model.addAttribute("userCountries", userCountries);
        model.addAttribute("username", username);
        return "user/user-countries";
    }

    
} 
