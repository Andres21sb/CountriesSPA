/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progra.countries.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Escinf
 */
public class Service {
    private static Service uniqueInstance;
    
    public static Service instance(){
        if (uniqueInstance == null){
            uniqueInstance = new Service();
        }
        return uniqueInstance; 
    }

    HashMap<String,Country> countries;
    
    private Service(){
        countries = new HashMap();
        Country c;
        c=new Country("Argentina", "Buenos Aires", 43590400, 2780400, new ArrayList<>(Arrays.asList((new Integer[]{-34,-64}))), "https://flagcdn.com/ar.svg");
        countries.put(c.name, c);

        c=new Country("Belize", "Belmopan", 370300, 22966, new ArrayList<>(Arrays.asList(new Integer[]{17,-88})), "https://flagcdn.com/bo.svg");
        countries.put(c.name, c);
        
        c = new Country("Brazil", "Brasília", 209288278, 8515767, new ArrayList<>(Arrays.asList(new Integer[]{-15, -47})), "https://flagcdn.com/br.svg");
        countries.put(c.name, c);

        c = new Country("Chile", "Santiago", 19107216, 756102, new ArrayList<>(Arrays.asList(new Integer[]{-33, -70})), "https://flagcdn.com/cl.svg");
        countries.put(c.name, c);

    c = new Country("Colombia", "Bogotá", 50372424, 1141748, new ArrayList<>(Arrays.asList(new Integer[]{4, -74})), "https://flagcdn.com/co.svg");
    countries.put(c.name, c);

    c = new Country("Peru", "Lima", 32510453, 1285216, new ArrayList<>(Arrays.asList(new Integer[]{-10, -76})), "https://flagcdn.com/pe.svg");
    countries.put(c.name, c);

    c = new Country("Uruguay", "Montevideo", 3461734, 176215, new ArrayList<>(Arrays.asList(new Integer[]{-34, -56})), "https://flagcdn.com/uy.svg");
    countries.put(c.name, c);
    }

    public Country read(String name)throws Exception{
        Country c = countries.get(name);
        if (c!=null) return c;
        else throw new Exception("Country does not exist");
    }

    public List<Country> find(String patron){
        return countries.values().stream().
                filter( c-> c.name.contains(patron)).
                collect(Collectors.toList());
    }
    
    public void delete(String name) {
    countries.remove(name);
    }
    
    public void add(Country country) {
    countries.put(country.getName(), country);
}
}
