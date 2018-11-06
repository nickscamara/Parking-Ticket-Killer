package com.example.nick.ptk;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by cc1057 on 2/10/18.
 */

public class Utils {

    public static LatLng GetDestinationPoint(double latitude, double longtitude, double bearing, double distance) {
        LatLng latlng;

        double radius = 6371000.0; // earth's mean radius in m
        double lat1 = Math.toRadians(latitude);
        double lng1 = Math.toRadians(longtitude);
        double lat2 = Math.asin(Math.sin(lat1) * Math.cos(distance / radius) + Math.cos(lat1) * Math.sin(distance / radius) * Math.cos(bearing));
        double lng2 = lng1 + Math.atan2(Math.sin(bearing) * Math.sin(distance / radius) * Math.cos(lat1), Math.cos(distance / radius) - Math.sin(lat1) * Math.sin(lat2));
        lng2 = (lng2 + Math.PI) % (2 * Math.PI) - Math.PI;

        // normalize to -180...+180
        if (lat2 == 0 || lng2 == 0) {
            latlng = new LatLng(0.0,0.0);
        } else {
            latlng = new LatLng(Math.toDegrees(lat2),Math.toDegrees(lng2));
        }

        return latlng;
    }
}
