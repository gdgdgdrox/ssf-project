package com.project.country.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.project.country.model.Countries;
import com.project.country.model.Country;


@Repository
public class CountryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveCountryList(List<Country> countries){
        System.out.println("Saving list of countries to Database");
        redisTemplate.opsForValue().set("countries", countries);
    }

    public boolean checkIfCountryListExists(){
        if (redisTemplate.hasKey("countries"))
            return true;
        return false;
    }

    public List<Country> getCountries(){
        System.out.println("Retrieving list of countries from Database");
        Countries.listOfAllCountries = (List<Country>)redisTemplate.opsForValue().get("countries");
        return Countries.listOfAllCountries;
    }

    public boolean checkIfUserExists(String username){
        return redisTemplate.hasKey(username) ? true : false;
    }


    public void saveUser(String username, List<Country> userCountries){
        redisTemplate.opsForValue().set(username, userCountries);
    }

    public List<Country> getUserCountriesFromDB(String username){
        List<Country> userCountries = (List<Country>)redisTemplate.opsForValue().get(username);
        return userCountries;
    }

    // public void deleteCountry(List<Country> countries, String capital){
    //     redisTemplate.
    // }




    
}
