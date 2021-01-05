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
    public int nRecords = 0;
    private String[] language1;
    private String[] language2;
    private String[] category;
    private String[] type;
    private HashMap<String,List<Integer>> categoryMap;
    private HashMap<String,Integer> language1Map;
    private HashMap<String,Integer> language2Map;
    private HashMap<Integer,Integer> learnMap;
    private Integer[] learnList;
    private int nLearnList;
    private int nLearnRepeat;
    private double learnFrac;
    private double maxLearnFrac;

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

        nLearnRepeat = 5;
        learnFrac =  0.1;
        maxLearnFrac =  0.8;

        try {
            InputStream stream = mngr.open(fileName);
            reader = new CSVReader(new InputStreamReader( stream ));

            allRecords = reader.readAll();
            nRecords  = allRecords.size();
            language1 = new String[nRecords];
            language2 = new String[nRecords];
            type      = new String[nRecords];
            category  = new String[nRecords];
            learnList = new Integer[nRecords];
            learnMap  = new HashMap<Integer,Integer>();
            nLearnList = 0;

            int i = 0;
            for( String[] line : allRecords )
            {
                language1[i] = line[0];
                language2[i] = line[1];
                type[i]      = line[2];
                category[i]  = line[3];
                if(line[4].equals("1")) {
                    learnList[nLearnList++]=i;
                    learnMap.put(i,nLearnRepeat);
                }
                i++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // create the type map
        categoryMap  = new HashMap<String,List<Integer>>();
        language1Map = new HashMap<String,Integer>();
        language2Map = new HashMap<String,Integer>();

        for( int i = 0; i < nRecords; i++ ) {
            if( !categoryMap.containsKey(category[i]) ) {
                List<Integer> categoryList = new ArrayList<Integer>();
                categoryMap.put(category[i],categoryList);
            };
            categoryMap.get(category[i]).add(i);
            language1Map.put(language1[i],i);
            language2Map.put(language2[i],i);
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
        return sampleRecords( n, null );
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WordRecord[] sampleRecords(int n, String filterCategory ) {

        // deal with trivial bad data cases
        WordRecord[] records = new WordRecord[n];
        if( n < 1 || nRecords < 1 )
            return records;

        // pick a record at random
        Random rnd = ThreadLocalRandom.current();
        int idx;
        int nRec;
        List<Integer> categoryIndicies;
        int indices[] = new int[n];
        if( filterCategory == null ) {
            // get from the learn list if applicable
            double rLearn = rnd.nextDouble();
            if( nLearnList > 0 && rLearn < maxLearnFrac && rLearn < ( learnFrac * nLearnList ) ) {
                // pick from the learn list
                idx = 0;
                if( nLearnList > 1 )
                    idx = rnd.nextInt(nLearnList - 1);
                idx = learnList[idx];
            }
            else {
                idx = rnd.nextInt(nRecords - 1);
            }
            categoryIndicies = categoryMap.get(category[idx]);
            nRec = categoryIndicies.size();

             // now we need to select the index of the first chosen record from the category list
             for (int i = 0; i < nRec; i++) {
                 if (categoryIndicies.get(i) == idx)
                    indices[0] = i;
            }
        } else {
            // pick out the category of interest
            categoryIndicies = categoryMap.get(filterCategory);
            nRec = categoryIndicies.size();

            if( nRec == 0 )
                return records;

            indices[0] = rnd.nextInt(nRec- 1);;
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

    public void addLearnWord( String word, int language )
    {
        int wordIndex;
        if( language == 0 ) {
            wordIndex = language1Map.get(word);
        } else {
            wordIndex = language2Map.get(word);
        }

        // add to learn list if not a current word
        if( !learnMap.containsKey(wordIndex) ) {
            learnList[nLearnList++]=wordIndex;
        }

        learnMap.put(wordIndex,nLearnRepeat);
    }

    public void correctLearnWord( String word, int language )
    {
        int wordIndex;
        if( language == 0 ) {
            wordIndex = language1Map.get(word);
        } else {
            wordIndex = language2Map.get(word);
        }

        if( !learnMap.containsKey(wordIndex))
            return;

        // now see how many times it has been repeated
        int nRepeat = learnMap.get(wordIndex);
        if( nRepeat > 1 ) {
            learnMap.put(wordIndex, nRepeat - 1);
        }
        else {
            // now we have done the maximum number of times we can remove;
            for( int i = 0; i < nLearnList; i++ ) {
                if( learnList[i]== wordIndex)
                    learnList[i] = learnList[ nLearnList-1];
            }
            nLearnList--;
            learnMap.remove(wordIndex);
        }
    }


}
