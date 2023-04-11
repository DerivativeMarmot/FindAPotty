package com.example.findapotty.search;

import com.example.findapotty.model.RestroomsManager;

public class NearbyRestroomsManager extends RestroomsManager<NearbyRestroom> {

    private static NearbyRestroomsManager instance;

    private NearbyRestroomsManager() {
    }

    public static NearbyRestroomsManager getInstance() {
        if (instance == null) {
            instance = new NearbyRestroomsManager();
        }
        return instance;
    }

    public NearbyRestroom getRestroomByMarkerId(String markId) {
        for (NearbyRestroom restroom : getRestroomsList()) {
            if (restroom.getMarkerId().equals(markId)) {
                return restroom;
            }
        }
        return null;
    }

    public int getRestroomIndexByMarkerId(String markId) {
        for (int i = 0; i < getRestroomsList().size(); i++) {
            if (getRestroomByIndex(i).getMarkerId().equals(markId)) {
                return i;
            }
        }
        return -1;
    }

    public void sortByDistance() {
        getRestroomsList().sort(
                (nearbyRestroom, other) ->
                        Long.compareUnsigned(nearbyRestroom.getCurrentDistance(), other.getCurrentDistance())
        );
    }
}