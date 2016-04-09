package com.asmahusna.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ali on 9/30/2014.
 */
public class GridViewCustomAdapter extends ArrayAdapter {
    Context context;
    List<ImageData> _imageData;
    public Map<String, String> _arabicList = new HashMap<String, String>();
    public Map<String, String> _latinList = new HashMap<String, String>();
    Boolean ToHide;
    public NamesImagesLoaded imagesLoaded;
    public Integer count = 0;
    public Boolean Completed = false;
    public int hintsCount=0;
    public Drawable drw;


    public GridViewCustomAdapter(Context context, Boolean toHide) {
        super(context, 0);
        this.context = context;
        imagesLoaded = NamesImagesLoaded.getInstance(context, toHide);
        _imageData = imagesLoaded.imageList;
        _arabicList = imagesLoaded.arabicList;
        _latinList = imagesLoaded.latinList;
        ToHide = toHide;
        drw = context.getResources().getDrawable(R.drawable.hiddenimage);
        count = GetFoundCount();
        hintsCount=GetHintedCount();
    }

    @Override
    public int getCount() {
        return 99;
    }

    public int GetFoundCount() {
        int found = 0;
        for (int i = 0; i < _imageData.size(); i++) {
            if (!_imageData.get(i).getHidden()) {
                found++;
            }
        }
        if (found == 99) {
            Completed = true;
        }
        return found;
    }

    public int GetHintedCount() {
        int hinted = 0;
        for (int i = 0; i < _imageData.size(); i++) {
            if (_imageData.get(i).getHinted()) {
                hinted++;
            }
        }
        return hinted;
    }

    public ImageData GetImageByid(String imageId) {

        for (ImageData item : _imageData) {
            if (item.getId().equalsIgnoreCase(imageId)) {
                return item;
            }
        }
        return null;
    }

    public void ResetImageData(GridView gridView)
    {
        for (ImageData data:_imageData) {
            data.setHinted(false);
            data.setHidden(true);
        }

        hintsCount=0;
        count=0;
    }

    public Map<Integer,Integer> RefreshImage(String Id, GridView gridView,Boolean hinted) {
        Integer ImageId = Integer.parseInt(Id) - 1;
        gridView.smoothScrollToPosition(ImageId);
        ImageData data = _imageData.get(ImageId);
        if (data.getHidden()) {
            data.setHidden(false);
            if(!data.getHinted() && hinted) {
                data.setHinted(true);
                hintsCount++;
            }
            count++;
            try {
                final int size = gridView.getChildCount();
                View row = GetViewByPosition(ImageId, gridView);
                ImageView image = (ImageView) row.findViewWithTag(Integer.toString(ImageId + 1));
                image.setImageBitmap(data.getImageBitmapData());
            } catch (Exception ex) {
            }
        }
        Map<Integer,Integer> returnValues=new HashMap<Integer, Integer>();
        returnValues.put(count,hintsCount);
        return returnValues;
    }

    public View GetViewByPosition(int position, GridView gridView) {
        int firstPosition = gridView.getFirstVisiblePosition();
        int lastPosition = gridView.getLastVisiblePosition();

        if ((position < firstPosition) || (position > lastPosition))
            return null;

        return gridView.getChildAt(position - firstPosition);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        android.content.res.AssetManager assets = context.getAssets();
        try {
            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(R.layout.grid_row, parent, false);
            }
            ImageView image = (ImageView) row.findViewById(R.id.a);
            ImageData currentImageData = GetImageByid(Integer.toString((position + 1)));
            image.setTag(currentImageData.getId());
            if (currentImageData.getHidden()) {
                image.setImageDrawable(drw);
            } else {
                image.setImageBitmap(currentImageData.getImageBitmapData());
            }

        } catch (Exception ex) {
            String here = "here";
        }
        return row;
    }

}
