package com.project.country.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.project.country.model.Countries;
import com.project.country.model.Country;
import com.project.country.repository.CountryRepository;

@Service
public class CountryService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CountryRepository repo;

    private Random random = new Random();

    private static final String COUNTRY_API_URL = "https://restcountries.com/v2/all?fields=name,capital,region,languages,population,flags";

    public List<Country> getCountries() {
        //refactor this to use a logger. question: do we need just one logger?
        System.out.println("CALLING COUNTRY-API TO RETRIEVE A LIST OF ALL COUNTRIES");
        ResponseEntity<String> respEntity = restTemplate.getForEntity(COUNTRY_API_URL, String.class);
        String payload = respEntity.getBody();
        ObjectMapper mapper = new ObjectMapper()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            JsonNode node = mapper.readTree(payload);
            ArrayNode arrayNode = (ArrayNode) node;
            for (JsonNode n : arrayNode){
                Country country = mapper.treeToValue(n, Country.class);
                country.formatPopulation();
                Countries.listOfAllCountries.add(country);
            }

        } catch (JsonMappingException e1) {
            e1.printStackTrace();
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }
        System.out.println("NUMBER OF COUNTRIES RETRIEVED: " + Countries.listOfAllCountries.size());
        return Countries.listOfAllCountries;
    }

    public void saveCountryList(List<Country> countries){
        repo.saveCountryList(countries);
    }

    public List<Country> getCountryList(){
        return repo.getCountries();
    }

    public Optional<Country> findCountry(String name){
        for (Country country : Countries.listOfAllCountries){
            if (country.getName().equalsIgnoreCase(name)){
                return Optional.of(country);
            }
        }
        return Optional.empty();
    }

    public boolean checkIfCountryListExists(){
        return repo.checkIfCountryListExists();
    }


    public boolean checkIfUserExists(String username){
        return repo.checkIfUserExists(username);
    }

    public List<Country> getUserCountriesFromDB(String username){
        return repo.getUserCountriesFromDB(username);
    }

    public void saveUser(String username, List<Country> userCountries){
        repo.saveUser(username, userCountries);
    }

    public Country findCountryByName(String countryName){
        for (Country c : Countries.listOfAllCountries){
            if (c.getName().equals(countryName)){
                return c;
            }
        }
        return null;
    }

    public Country getRandomCountry(){
        int randomNum = random.nextInt(0, Countries.listOfAllCountries.size());
        Country randomCountry = Countries.listOfAllCountries.get(randomNum);
        return randomCountry;
    }
    
}
