package ajmitchell.android.thenewsroom.utils;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import ajmitchell.android.thenewsroom.models.NewsModel;

public class NewsTypeConverter {

    @TypeConverter
    public static String nullToString (String data) {
        if (data == null)
            return nullToString(data);

        return data;
    }
}
