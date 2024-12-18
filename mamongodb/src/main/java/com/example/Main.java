package com.example;

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
        // MongoHandler mh = new MongoHandler();
        // Scanner sc = new Scanner(System.in);
        // boolean loop = true;
        // while (loop) {
        //     String linha = sc.nextLine();

        //     switch (linha) {
        //         case "Add":
        //             addFunction(mh);
        //             break;
        //         case "findAll":
        //             findAll(mh);
        //         case "exit":
        //             loop = false;
        //         default:
        //             break;
        //     }
        // }
        // sc.close();
    }

    public static void addFunction(MongoHandler mh){
        Scanner sc = new Scanner(System.in);
        String titulo = sc.nextLine();
        String tipo = sc.nextLine();
        String tags1 = sc.nextLine();
        ArrayList<String> tags = new ArrayList<>();
        while(!tags1.equals("-")){
            tags.add(tags1);
            tags1 = sc.nextLine();
        }
        String pessoa = sc.nextLine();
        int score = sc.nextInt();
        HashMap<String, Integer> mapa = new HashMap<>();
        mapa.put(pessoa, score);

        Entry e = new Entry(titulo, tipo, mapa, tags);
        mh.addEntry(e);
        sc.close();
    }

    public static void findAll(MongoHandler mh){
        mh.printAll();
    }



    public static void mongoHandler(){

        String uri = "mongodb+srv://areis04net:OaHxZtDOKs177scf@cluster0.rwzipne.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        MongoDatabase database;
        MongoCollection<Entry> collection;
        CodecRegistry pojoCodecRegistry ;
        CodecRegistry codecRegistry ;
        MongoClient monguito;

        System.out.println("1111");
        pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

        MongoClientSettings clientSettings = MongoClientSettings.builder()
                                                                .applyConnectionString(new ConnectionString(uri))
                                                                .codecRegistry(codecRegistry)
                                                                .build();

        try{
            System.out.println("qqqq");
            monguito = MongoClients.create(clientSettings);
            System.out.println("2222");
            database = monguito.getDatabase("mamongo");
            collection = database.getCollection("mamongo", Entry.class);

            //Testes
            HashMap<String, Integer> score = new HashMap<>();
            score.put("daniel", 0);
            ArrayList<String> listinha = new ArrayList<>();
            listinha.add("aa");
            listinha.add("bb");
            Entry e = new Entry("pila", "genital", score, listinha);

            addEntry(e, collection);


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public MongoCollection<Document> getCollection(String collection, MongoDatabase database) throws Exception{
        return database.getCollection(collection);
    }

    public MongoDatabase fetchDB(MongoClient monguito, CodecRegistry pojoCodecRegistry){
        return monguito.getDatabase("mamongo").withCodecRegistry(pojoCodecRegistry); 
    }

    public MongoCollection<Entry> fetchCollection(MongoDatabase database ){
        return  database.getCollection("mamongo", Entry.class);
    }

    public Entry findByTitle(String s, MongoCollection<Entry> collection){
        Entry entry = collection.find(eq("title", s)).first();
        return entry;
    }


    public static void addEntry(Entry e, MongoCollection<Entry> collection){
        collection.insertOne(e);
    }

    public static void close(MongoClient monguito){
        monguito.close();
    }
}