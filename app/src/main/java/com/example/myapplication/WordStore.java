package com.example.myapplication;


import android.content.res.AssetManager;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class WordStore {

    private AssetManager mngr;
    private String fileName;
    public int nRecords;
    private String[] language1;
    private String[] language2;
    private String[] category;
    private String[] type;
    private HashMap<String,List<Integer>> categoryMap;

    public WordStore(AssetManager appAssets, String storeFileName ) {
        mngr = appAssets;
        fileName = storeFileName;
        this.loadData();
    }

    public WordRecord getWordRecord( int idx ) {
        WordRecord record = new WordRecord( language1[idx], language2[idx], category[idx], type[idx]);
        return record;
    }

    public void loadData() {
        CSVReader reader;
        List<String[]> allRecords;

        try {
            InputStream stream = mngr.open(fileName);
            reader = new CSVReader(new InputStreamReader( stream ));

            allRecords = reader.readAll();
            nRecords  = allRecords.size();
            language1 = new String[nRecords];
            language2 = new String[nRecords];
            type      = new String[nRecords];
            category  = new String[nRecords];

            int i = 0;
            for( String[] line : allRecords )
            {
                language1[i]  = line[0];
                language2[i]  = line[1];
                type[i]       = line[2];
                category[i++] = line[3];
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // create the type map
        categoryMap = new HashMap<String,List<Integer>>();

        for( int i = 0; i < nRecords; i++ ) {
            if( !categoryMap.containsKey(category[i]) ) {
                List<Integer> categoryList = new ArrayList<Integer>();
                categoryMap.put(category[i],categoryList);
            };
            categoryMap.get(category[i]).add(i);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    static void shuffleIntArray(int[] ar)
    {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WordRecord[] sampleRecords(int n ) {

        // deal with trivial bad data cases
        WordRecord[] records = new WordRecord[n];
        if( n < 1 || nRecords < 1 )
            return records;

        // pick a record at random, this decides the category
        Random rnd = ThreadLocalRandom.current();
        int idx;
        int indices[] = new int[n];
        idx = rnd.nextInt( nRecords -1 );
        List<Integer> categoryIndicies = categoryMap.get(category[idx]);
        int nRec = categoryIndicies.size();

        // now we need to select the index of the first chosen record from the category list
        for( int i = 0; i < nRec; i++ ) {
            if (categoryIndicies.get(i) == idx)
                indices[0] = i;
        }

        // pick the other n-1 elements from the category list
        if ( n >= nRec ) {
            for( int i = 1; i < n; i++ )
                indices[i] = i % nRec;
            shuffleIntArray(indices);
        } else {
            for( int i = 1; i < n; i++ ) {
                idx = rnd.nextInt( nRec -1 );
                boolean found = false;
                for( int j = 0; j < i; j++ ) {
                    if( indices[j] == idx )
                        found = true; }
                if( found ) {
                    i--;
                } else {
                    indices[i] = idx;
                }
            }
        }

        // retrieve the records
        for( int i = 0; i < n; i++ )
            records[i] = getWordRecord(categoryIndicies.get(indices[i]));

        return records;
    }
}
