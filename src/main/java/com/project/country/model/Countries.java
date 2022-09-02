package com.project.country.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Countries {
    public static List<Country> listOfAllCountries = new ArrayList<>(250);
}
