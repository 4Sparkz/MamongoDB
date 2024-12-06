package com.mongodb.quickstart;

import java.util.ArrayList;
import java.util.HashMap;

public class Entry {
    
    String title; //titulo do manga ex. Overgueared
    String type; //manhwa, manga ou manhua
    HashMap<String, Integer> score; //par pessoa que deu score e o score ex. Martim - 10. Score de 0 a 10
    ArrayList<String> tags; //ex. nsfw, martial arts, swords

    public Entry(){}

    public Entry(String title, String type, HashMap<String, Integer> score, ArrayList<String> tags){
        this.title = title;
        this.tags = tags;
        this.type = type;
        this.score = score;
    }

    public String getTitle(){
        return this.title;
    }

    public String getType(){
        return this.type;
    }

    public HashMap<String, Integer> getScores(){
        return this.score;
    }

    public ArrayList<String> getTags(){
        return this.tags;
    }

    public int getScore(String name){
        if(score.containsKey(name)){
            return score.get(name);
        }
        return -1;
    }

    public void setTitle(String s){
        this.title = s;
    }

    public void setType(String t){
        this.type = t;
    }

    public void setScores(HashMap<String, Integer> m){
        this.score = m;
    }

    public void setTags(ArrayList<String> t){
        this.tags = t;
    }

    public void addTag(String tag){
        tags.add(tag);
    }

    public void removeTag(String tag){
        tags.remove(tag);
    }

    public void addScore(String name, int s){
        score.put(name, s);
    }

    public void alterScore(String name, int s){
        score.replace(name, s);
    }

    public void removeScore(String name, int s){
        score.remove(name, s);
    }


    @Override
    public String toString(){
        StringBuilder s = new StringBuilder("Nome: "+ title);
        //String s = ;
        s.append("\n");
        s.append("-tipo: "+type + "\n");
        s.append("tags: ");
        for(String tag : tags){
            s.append(tag + ",");
        }
        s.append("\n");
        s.append("scores: "+ "\n");
        for(String key : score.keySet()){
            s.append(key + " - " + score.get(key) + "\n");
        }
        return s.toString();
    }

}
