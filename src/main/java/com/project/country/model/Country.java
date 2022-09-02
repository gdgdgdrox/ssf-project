package com.project.country.model;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Country implements Serializable{
    private String name;
    private String capital;
    private String region;
    private String population;
    private List<Language> languages;
    @JsonSetter("flags")
    private Flag flag;

    public void formatPopulation(){
        Integer intPopulation = Integer.parseInt(this.population);
        this.population = String.format("%,d", intPopulation);
    }

    @Getter
    @Setter
    @ToString
    public class Flag implements Serializable{
        @JsonSetter("png")
        private String image;
    }

    @Getter
    @Setter
    @ToString
    //this nested class needs to be static else Jackson throws InvalidDefinitionException
    public static class Language implements Serializable{ 
        private String name;
    }



}
