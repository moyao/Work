package com.guansu.management.utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

/**
 *
 * Created by dongyaoyao
 */

public class ParcelableUtil {

    public static String object2String(Parcelable object) {
        // 1.序列化
        Parcel p = Parcel.obtain();
        object.writeToParcel(p, 0);
        byte[] bytes = p.marshall();
        p.recycle();

        // 2.编码
        String str = Base64.encodeToString(bytes, Base64.DEFAULT);
        return str;
    }

    private static Parcel unmarshall(byte[] bytes) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0); // this is extremely important!
        return parcel;
    }

    public static <T> T string2Object(String str, Parcelable.Creator<T> creator) {
        // 1.解码
        byte[] bytes = Base64.decode(str, Base64.DEFAULT);
        // 2.反序列化
        Parcel parcel = unmarshall(bytes);
        return creator.createFromParcel(parcel);
    }

}
