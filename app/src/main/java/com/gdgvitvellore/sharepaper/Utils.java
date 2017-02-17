package com.gdgvitvellore.sharepaper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;

/**
 * Created by ramkishorevs on 28/01/17.
 */

public class Utils {


    public static final String HEADING_FONT="HEADING";
    public static final String NORMAL_FONT="HEADING";

    public Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }


    public  int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    public Typeface getFontType(String s, Context context)
    {
        if (s.equals(Utils.HEADING_FONT))
        {
            Typeface heading = Typeface.createFromAsset(context.getResources().getAssets(), "Montserrat-Regular.ttf");
            return heading;
        }
        else if (s.equals(Utils.NORMAL_FONT))
        {
            Typeface normal= Typeface.createFromAsset(context.getResources().getAssets(),"Segoe-Regular.ttf");
            return normal;
        }

        else
        {
            return null;
        }
    }
}
