package com.example;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import com.mongodb.*;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoHandler {
    
    private final String uri = "mongodb+srv://areis04net:OaHxZtDOKs177scf@cluster0.rwzipne.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    public MongoClient monguito;
    MongoDatabase database;
    MongoCollection<Entry> collection;
    CodecProvider pojoCodecProvider;
    CodecRegistry pojoCodecRegistry;

    public MongoHandler(){

        pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));


        try{
            monguito = MongoClients.create(uri);
            database = monguito.getDatabase("mamongo").withCodecRegistry(pojoCodecRegistry);
            collection = database.getCollection("mamongo", Entry.class);



        }catch(Exception e){
            e.printStackTrace();
        }
    

    }

    public MongoCollection<Document> getCollection(String collection) throws Exception{
        return database.getCollection(collection);
    }

    public void fetch(){
        database = monguito.getDatabase("mamongo").withCodecRegistry(pojoCodecRegistry);
        collection = database.getCollection("mamongo", Entry.class);
    }

    public Entry findByTitle(String s){
        Entry entry = collection.find(eq("title", "Back to the Future")).first();
        return entry;
    }

    // TODO findall 
    // public Entry findAllByTitle(String s){
    //     collection.
    //     FindIterable<Entry> manyEntry = collection.find(eq("title", "Back to the Future"));

    //     return entry;
    // }

    public void close(){
        monguito.close();
    }
}
