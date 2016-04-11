package ru.zhukov.utils;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.zhukov.domain.Database;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by Zhukov on 15.03.2016.
 */
public class ReadFileWithDatabase {

    private List<Database> databaseList;


    public ReadFileWithDatabase(){

    }


    public List<Database> getDatabaseList(){
        loadFileWithdatabase();
        return databaseList;
    }

    private void loadFileWithdatabase(){
        Properties properties = new Properties();
        try{
           if(!Paths.get(System.getProperty("user.dir")+"/database/database.json").toFile().exists()){
               Files.createDirectories(Paths.get(System.getProperty("user.dir")+"/database/"));
               createJsonFileDatabase();
           }
          databaseList =  loadJsonFileDatabase();

        }catch(IOException ex){

        }

    }


    private void createJsonFileDatabase(){
        Gson mapper = new GsonBuilder()
                .disableHtmlEscaping()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        List<Database> databaseList = new ArrayList<>();
        databaseList.add(new Database("Готэк","ait_db","г01"));
        databaseList.add(new Database("Готэк-ЦПУ","ait_cpu","Г05"));

        try(FileWriter fileWriter = new FileWriter(Paths.get(System.getProperty("user.dir")+"/database/database.json").toString())){
           fileWriter.write(mapper.toJson(databaseList));


        }catch(IOException e){
            e.printStackTrace();
        }


    }

    private List<Database> loadJsonFileDatabase(){
       Gson mapper = new GsonBuilder()
               .disableHtmlEscaping()
               .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
               .setPrettyPrinting()
               .serializeNulls()
               .create();
        List<Database> stringDatabaseMap = new ArrayList<>();
       try(BufferedReader bufferedReader = new BufferedReader(new FileReader(Paths.get(System.getProperty("user.dir") + "/database/database.json").toString()))) {
           Type collectionType = new TypeToken<Collection<Database>>(){}.getType();
           stringDatabaseMap = mapper.fromJson(bufferedReader,collectionType);
        }catch (IOException ex){
            ex.printStackTrace();
        }


        return  stringDatabaseMap;
    }





}
