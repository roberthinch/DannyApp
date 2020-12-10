package com.example.myapplication;


import android.content.res.AssetManager;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.opencsv.CSVReader;
import org.apache.commons.lang3.ArrayUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class WordStore {

    private AssetManager mngr;
    private String fileName;
    public int nRecords;
    private String[] language1;
    private String[] language2;
    private String[] type;

    public WordStore(AssetManager appAssets, String storeFileName ) {
        mngr = appAssets;
        fileName = storeFileName;
        this.loadData();
    }

    public WordRecord getWordRecord( int idx ) {
        WordRecord record = new WordRecord( language1[idx], language2[idx], type[idx]);
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

            int i = 0;
            for( String[] line : allRecords )
            {
                language1[i] = line[0];
                language2[i] = line[1];
                type[i++]      = line[2];
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        Random rnd = ThreadLocalRandom.current();
        int indices[] = new int[n];
        int idx;
        int nRecords = MainActivity.NOUN_STORE.nRecords;
        if ( n >= nRecords ) {
            for( int i = 0; i < n; i++ )
                indices[i] = i % nRecords;
            shuffleIntArray(indices);
        } else {
            for( int i = 0 ; i < n; i++ ) {
                idx = rnd.nextInt( nRecords -1 );
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

        WordRecord[] records = new WordRecord[n];
        for( int i = 0; i < n; i++ )
            records[i] = getWordRecord(indices[i]);

        return records;
    }
}
