package com.asmahusna.app;

import android.graphics.Bitmap;

/**
 * Created by Ali on 10/18/2014.
 */
public class ImageData {

    private String Id;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    private Bitmap ImageBitmapData;

    public Bitmap getImageBitmapData() {
        return ImageBitmapData;
    }

    public void setImageBitmapData(Bitmap imageBitmapData) {
        ImageBitmapData = imageBitmapData;
    }

    private String NameArabic;

    public String getNameArabic() {
        return NameArabic;
    }

    public void setNameArabic(String nameArabic) {
        NameArabic = nameArabic;
    }

    private String NameLatin;

    public String getNameLatin() {
        return NameLatin;
    }

    public void setNameLatin(String nameLatin) {
        NameLatin = nameLatin;
    }

    private Boolean Hidden;

    public Boolean getHidden() {
        return Hidden;
    }

    public void setHidden(Boolean hidden) {
        Hidden = hidden;
    }

    private Boolean Hinted=false;

    public Boolean getHinted() {
        return Hinted;
    }

    public void setHinted(Boolean hinted) {
        Hinted = hinted;
    }

}
