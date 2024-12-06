package com.mongodb.quickstart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;



import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.lt;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.Arrays;

import com.mongodb.*;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;


public class Main {

    
    public static void main(String[] args) {

        mongoHandler();

    }




    public static void mongoHandler(){

        String uri = "mongodb+srv://areis04net:OaHxZtDOKs177scf@cluster0.rwzipne.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        MongoDatabase database;
        MongoCollection<Entry> collection;
        CodecRegistry pojoCodecRegistry ;
        CodecRegistry codecRegistry ;
        MongoClient monguito;

        pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

        MongoClientSettings clientSettings = MongoClientSettings.builder()
                                                                .applyConnectionString(new ConnectionString(uri))
                                                                .codecRegistry(codecRegistry)
                                                                .build();

        try{
            monguito = MongoClients.create(clientSettings);
            database = monguito.getDatabase("mamongo");
            collection = database.getCollection("mamongo", Entry.class);

            //Testes
            HashMap<String, Integer> score = new HashMap<>();
            score.put("dadasad", 10);
            ArrayList<String> listinha = new ArrayList<>();
            listinha.add("ee");
            listinha.add("ff");
            Entry e = new Entry("pila", "buraco", score, listinha);

            addEntry(e, collection);

            printAll(findAllByTitle("pila", collection));
            // Entry e = findByTitle("pila", collection);
            // System.out.println(e.getType());


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public MongoCollection<Document> getCollection(String collection, MongoDatabase database) throws Exception{
        return database.getCollection(collection);
    }

    public static MongoDatabase fetchDB(MongoClient monguito, CodecRegistry pojoCodecRegistry){
        return monguito.getDatabase("mamongo").withCodecRegistry(pojoCodecRegistry); 
    }

    public static MongoCollection<Entry> fetchCollection(MongoDatabase database ){
        return  database.getCollection("mamongo", Entry.class);
    }

    public static Entry findByTitle(String s, MongoCollection<Entry> collection){
        Entry entry = collection.find(eq("title", s)).first();
        return entry;
    }


    public static void addEntry(Entry e, MongoCollection<Entry> collection){
        collection.insertOne(e);
    }

    public static void close(MongoClient monguito){
        monguito.close();
    }

    public static MongoCursor<Entry> findAllByTitle(String s,  MongoCollection<Entry> collection){
        MongoCursor<Entry> manyEntry = collection.find(eq("title", s)).iterator();
        return manyEntry;
    }

    public static void printAll(MongoCursor<Entry> manyEntry){
        while(manyEntry.hasNext()) {
            System.out.println(manyEntry.next().toString());
        }
    }
}