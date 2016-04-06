package com.asmahusna.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ali on 10/18/2014.
 */
public class NamesImagesLoaded {
    private static NamesImagesLoaded ourInstance = null;
    public static List<ImageData> imageList=new ArrayList<ImageData>();
    private static  Context _context;
    public Map<String, String> arabicList = new HashMap<String, String>();
    public Map<String, String> latinList = new HashMap<String, String>();
    static Boolean  ToHide=false;


    public static NamesImagesLoaded getInstance(Context context,Boolean toHide) {
        if(ourInstance==null || ToHide!=toHide) {
            _context=context;
            ToHide=toHide;
            ourInstance=new NamesImagesLoaded();
        }
        return ourInstance;

    }

    private NamesImagesLoaded() {
        fillArabicNames();
        fillEnglishNames();
        FillImageCache();
    }


    public void FillImageCache()
    {
        imageList=new ArrayList<ImageData>();
        for(int i=0;i<=99;i++) {
            try {
                String Id=Integer.toString((i + 1));
                String fileAddress = "Names/" + Id + ".jpg";
                BitmapFactory.Options opt = new BitmapFactory.Options();
                int width = opt.outWidth;
                int height = opt.outHeight;
                opt.inSampleSize = 3;
                InputStream inputStream =_context.getAssets().open(fileAddress);
                Bitmap img = BitmapFactory.decodeStream(inputStream, new Rect(), opt);
                ImageData imageObject=new ImageData();
                imageObject.setId(Id);
                imageObject.setHidden(ToHide);
                imageObject.setImageBitmapData(img);
                imageObject.setNameArabic(GetNameTextid(Id,arabicList));
                imageList.add(imageObject);
            }
            catch (Exception ex) {
                String lol="stupid java";
            }
        }
    }

    public  String GetNameTextid(String Idvalue,Map<String, String> listToSearch) {

        for ( Map.Entry<String, String> entry : listToSearch.entrySet() ) {
            String key = entry.getKey();
            String value = entry.getValue();
            if(key.equalsIgnoreCase(Idvalue))
            {
                return value;
            }
        }
        return null;
    }

    private void fillArabicNames() {
        arabicList.put("1", "الرحمن");
        arabicList.put("2", "الرحيم ");
        arabicList.put("3", "الملك ");
        arabicList.put("4", "القدوس");
        arabicList.put("5", "السلام");
        arabicList.put("6", "المؤمن");
        arabicList.put("7", "المهيمن");
        arabicList.put("8", "العزيز");
        arabicList.put("9", "الجبار");
        arabicList.put("10","المتكبر");
        arabicList.put("11", "الخالق");
        arabicList.put("12", "البارئ");
        arabicList.put("13", "المصور ");
        arabicList.put("14", "الغفار ");
        arabicList.put("15", "القهار ");
        arabicList.put("16", "الوهاب ");
        arabicList.put("17", "الرزاق ");
        arabicList.put("18", "الفتاح ");
        arabicList.put("19", "العليم ");
        arabicList.put("20", "القابض ");
        arabicList.put("21", "الباسط ");
        arabicList.put("22", "الخافض ");
        arabicList.put("23", "الرافع ");
        arabicList.put("24", "المعز ");
        arabicList.put("25", "المذل ");
        arabicList.put("26", "السميع ");
        arabicList.put("27", "البصير ");
        arabicList.put("28", "الحكم ");
        arabicList.put("29", "العدل ");
        arabicList.put("30", "اللطيف ");
        arabicList.put("31", "الخبير ");
        arabicList.put("32", "الحليم ");
        arabicList.put("33", "العظيم ");
        arabicList.put("34", "الغفور ");
        arabicList.put("35", "الشكور ");
        arabicList.put("36", "العلي ");
        arabicList.put("37", "الكبير ");
        arabicList.put("38", "الحفيظ ");
        arabicList.put("39", "المقيت ");
        arabicList.put("40", "الحسيب ");
        arabicList.put("41", "الجليل ");
        arabicList.put("42", "الكريم ");
        arabicList.put("43", "الرقيب ");
        arabicList.put("44", "المجيب ");
        arabicList.put("45", "الواسع ");
        arabicList.put("46", "الحكيم ");
        arabicList.put("47", "الودود ");
        arabicList.put("48", "المجيد ");
        arabicList.put("49", "الباعث ");
        arabicList.put("50", "الشهيد ");
        arabicList.put("51", "الحق ");
        arabicList.put("52", "الوكيل ");
        arabicList.put("53", "القوي ");
        arabicList.put("54", "المتين ");
        arabicList.put("55", "الولي ");
        arabicList.put("56", "الحميد ");
        arabicList.put("57", "المحصي ");
        arabicList.put("58", "المبدئ");
        arabicList.put("59", "المعيد ");
        arabicList.put("60", "المحيي ");
        arabicList.put("61", "المميت ");
        arabicList.put("62", "الحي ");
        arabicList.put("63", "القيوم ");
        arabicList.put("64", "الواجد ");
        arabicList.put("65", "الماجد ");
        arabicList.put("66", "الواحد ");
        arabicList.put("67", "الاحد");
        arabicList.put("68", "الصمد ");
        arabicList.put("69", "القادر ");
        arabicList.put("70", "المقتدر ");
        arabicList.put("71", "المقدم ");
        arabicList.put("72", "المؤخر ");
        arabicList.put("73", "الأول ");
        arabicList.put("74", "الآخر ");
        arabicList.put("75", "الظاهر ");
        arabicList.put("76", "الباطن ");
        arabicList.put("77", "الوالي ");
        arabicList.put("78", "المتعالي ");
        arabicList.put("79", "البر ");
        arabicList.put("80", "التواب ");
        arabicList.put("81", "المنتقم ");
        arabicList.put("82", "العفو ");
        arabicList.put("83", "الرؤوف ");
        arabicList.put("84", "مالك الملك ");
        arabicList.put("85", "ذو الجلال والإكرام ");
        arabicList.put("86", "المقسط ");
        arabicList.put("87", "الجامع ");
        arabicList.put("88", "الغني ");
        arabicList.put("89", "المغني ");
        arabicList.put("90", "المانع ");
        arabicList.put("91", "الضار ");
        arabicList.put("92", "النافع ");
        arabicList.put("93", "النور ");
        arabicList.put("94", "الهادي ");
        arabicList.put("95", "البديع ");
        arabicList.put("96", "الباقي ");
        arabicList.put("97", "الوارث ");
        arabicList.put("98", "الرشيد ");
        arabicList.put("99", "الصبور ");

    }


    private void fillEnglishNames() {

        latinList.put("1", "ar rahman");
        latinList.put("2",  "al rahim");
        latinList.put("3",  "al malim");
        latinList.put("4",  "al quddus");
        latinList.put("5",  "as salam");
        latinList.put("6",  "al mumin");
        latinList.put("7",  "ak muhaymin");
        latinList.put("8",  "al aziz");
        latinList.put("9",  "al jabbar");
        latinList.put("10", "al mutakabbir");
        latinList.put("11", "Al rahman");
        latinList.put("12", "Al rahman");
        latinList.put("13", "Al rahman");
        latinList.put("14", "Al rahman");
        latinList.put("15", "Al rahman");
        latinList.put("16", "Al rahman");
        latinList.put("17", "Al rahman");
        latinList.put("18", "Al rahman");
        latinList.put("19", "Al rahman");
        latinList.put("20", "Al rahman");
        latinList.put("21", "Al rahman");
        latinList.put("22", "Al rahman");
        latinList.put("23", "Al rahman");
        latinList.put("24", "Al rahman");
        latinList.put("25", "Al rahman");
        latinList.put("26", "Al rahman");
        latinList.put("27", "Al rahman");
        latinList.put("28", "Al rahman");
        latinList.put("29", "Al rahman");
        latinList.put("30", "Al rahman");
        latinList.put("31", "Al rahman");
        latinList.put("32", "Al rahman");
        latinList.put("33", "Al rahman");
        latinList.put("34", "Al rahman");
        latinList.put("35", "Al rahman");
        latinList.put("36", "Al rahman");
        latinList.put("37", "Al rahman");
        latinList.put("38", "Al rahman");
        latinList.put("39", "Al rahman");
        latinList.put("40", "Al rahman");
        latinList.put("41", "Al rahman");
        latinList.put("42", "Al rahman");
        latinList.put("43", "Al rahman");
        latinList.put("44", "Al rahman");
        latinList.put("45", "Al rahman");
        latinList.put("46", "Al rahman");
        latinList.put("47", "Al rahman");
        latinList.put("48", "Al rahman");
        latinList.put("49", "Al rahman");
        latinList.put("50", "Al rahman");
        latinList.put("51", "Al rahman");
        latinList.put("52", "Al rahman");
        latinList.put("53", "Al rahman");
        latinList.put("54", "Al rahman");
        latinList.put("55", "Al rahman");
        latinList.put("56", "Al rahman");
        latinList.put("57", "Al rahman");
        latinList.put("58", "Al rahman");
        latinList.put("59", "Al rahman");
        latinList.put("60", "Al rahman");
        latinList.put("61", "Al rahman");
        latinList.put("62", "Al rahman");
        latinList.put("63", "Al rahman");
        latinList.put("64", "Al rahman");
        latinList.put("65", "Al rahman");
        latinList.put("66", "Al rahman");
        latinList.put("67", "Al rahman");
        latinList.put("68", "Al rahman");
        latinList.put("69", "Al rahman");
        latinList.put("70", "Al rahman");
        latinList.put("71", "Al rahman");
        latinList.put("72", "Al rahman");
        latinList.put("73", "Al rahman");
        latinList.put("74", "Al rahman");
        latinList.put("75", "Al rahman");
        latinList.put("76", "Al rahman");
        latinList.put("77", "Al rahman");
        latinList.put("78", "Al rahman");
        latinList.put("79", "Al rahman");
        latinList.put("80", "Al rahman");
        latinList.put("81", "Al rahman");
        latinList.put("82", "Al rahman");
        latinList.put("83", "Al rahman");
        latinList.put("84", "Al rahman");
        latinList.put("85", "Al rahman");
        latinList.put("86", "Al rahman");
        latinList.put("87", "Al rahman");
        latinList.put("88", "Al rahman");
        latinList.put("89", "Al rahman");
        latinList.put("90", "Al rahman");
        latinList.put("91", "Al rahman");
        latinList.put("92", "Al rahman");
        latinList.put("93", "Al rahman");
        latinList.put("94", "Al rahman");
        latinList.put("95", "Al rahman");
        latinList.put("96", "Al rahman");
        latinList.put("97", "Al rahman");
        latinList.put("98", "Al rahman");
        latinList.put("99", "Al rahman");
    }
}
