package com.racoonberus.hotelHero;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileFetcher {

    public static void fetch(File in, String query, int limit, List result) {
        try (BufferedReader br = new BufferedReader(new FileReader(in))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) continue;
                if (line.toUpperCase().startsWith(query.toUpperCase())) {
                    result.add(line);
                    if (result.size() == limit) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
