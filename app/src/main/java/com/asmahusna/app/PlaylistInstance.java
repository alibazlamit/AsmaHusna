package com.asmahusna.app;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ali on 9/9/2014.
 */
public class PlaylistInstance {

    public ArrayList<String> items = new ArrayList<String>();
    public Map<String, String> songsList = new HashMap<String, String>();

    private static PlaylistInstance playlistfilesInstance = null;

    public static PlaylistInstance getInstance() {

        if(playlistfilesInstance==null)
        {
            playlistfilesInstance=new PlaylistInstance();
        }
        return playlistfilesInstance;
    }

    private PlaylistInstance() {
        fillPlaylist();
        getRawFiles();
    }

    public  String GetSongId(String songValue) {
        for ( Map.Entry<String, String> entry : songsList.entrySet() ) {
            String key = entry.getKey();
            String value = entry.getValue();
            if(value.equalsIgnoreCase(songValue))
            {
                return key;
            }
        }
        return songValue;
    }

    private void fillPlaylist() {
        songsList.put("n01", "Imad Rami (Acoustic)");
        songsList.put("n02", "Mohammad fou'ad");
        songsList.put("n03", "Group");
        songsList.put("n04", "Sami Yusuf");
        songsList.put("n05", "Ehab Tawfik");
        songsList.put("n06", "Sabah Fakhri(low Quality)");
        songsList.put("n07", "Lutfi Boshnak");
        songsList.put("n08", "Naqshabndi");
        songsList.put("n09", "Turkish 01");
        songsList.put("n10", "Turkish 02");
        songsList.put("n11", "Mustafa Özcan");

    }

    private void getRawFiles() {
        Field[] fields = R.raw.class.getFields();
        // loop for every file in raw folder
        for (int count = 0; count < fields.length; count++) {
            // Use that if you just need the file name
            String fileName=fields[count].getName();
            String toAdd=songsList.get(fileName);

            items.add(toAdd);
        }
    }
}

