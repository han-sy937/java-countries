package com.lambdaschool.countries.controllers;

import com.lambdaschool.countries.models.Country;
import com.lambdaschool.countries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController {
    @Autowired
    CountryRepository countryrepos;

    private List<Country> findCountries(List<Country> myList, CheckCountry tester) {
        List<Country> tempList = new ArrayList<>();

        for (Country c : myList) {
            if (tester.test(c)) {
                tempList.add(c);
            }
        }
        return tempList;
    }

    //http://localhost:2019/names/all
    @GetMapping(value = "/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountries() {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }
    //http://localhost:2019/names/start/u
    @GetMapping(value = "/names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> listCountriesWithU(@PathVariable char letter) {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        List<Country> luList = findCountries(myList, c -> c.getName().charAt(0) == letter);

        for (Country c : luList) {
            System.out.println(c);
        }

        return new ResponseEntity<>(luList, HttpStatus.OK);
    }
    //http://localhost:2019/population/total
    @GetMapping(value = "/population/total", produces = {"application/json"})
    public ResponseEntity<?> totalPopulation() {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);

        long total = 0;
        for (Country c : myList) {
            total = total + c.getPopulation();
        }
        System.out.println("Total Population is " + total);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    //http://localhost:2019/population/min
    @GetMapping(value = "/population/min", produces = {"application/json"})
    public ResponseEntity<?> minPopulation() {
        List<Country> minList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(minList::add);

        minList.sort((c1, c2) -> (int) (c1.getPopulation() - c2.getPopulation()));
        return new ResponseEntity<>(minList.get(0), HttpStatus.OK);
    }

    //http://localhost:2019/population/max
    @GetMapping(value = "/population/max", produces = {"application/json"})
    public ResponseEntity<?> maxPopulation() {
        List<Country> maxList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(maxList::add);

        maxList.sort((c1, c2) -> (int) (c2.getPopulation() - c1.getPopulation()));
        return new ResponseEntity<>(maxList.get(0), HttpStatus.OK);
    }
}
