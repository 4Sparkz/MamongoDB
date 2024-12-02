package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MongoHandler mh = new MongoHandler();
        Scanner sc = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            String linha = sc.nextLine();

            switch (linha) {
                case "Add":
                    addFunction(mh);
                    break;
                case "findAll":
                    findAll(mh);
                case "exit":
                    loop = false;
                default:
                    break;
            }
        }
        sc.close();
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
}