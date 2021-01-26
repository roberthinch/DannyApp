package com.example.myapplication;

public class WordRecord {
    public String language1;
    public String language2;
    public String type;
    public String category;
    public String soundFileShort;

    public WordRecord( String l1, String l2, String t, String c, String sfs ) {
        language1 = l1;
        language2 = l2;
        type      = t;
        category  = c;
        soundFileShort = sfs;
    }
}