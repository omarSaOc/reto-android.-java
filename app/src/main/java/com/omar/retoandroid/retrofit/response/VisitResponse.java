package com.omar.retoandroid.retrofit.response;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.omar.retoandroid.retrofit.response.Location;

@Entity(tableName = "visit")
public class VisitResponse {
    @SerializedName("streetName")
    @Expose
    private String streetName;
    @SerializedName("suburb")
    @Expose
    private String suburb;
    @SerializedName("visited")
    @Expose
    private Boolean visited;
    @SerializedName("location")
    @Expose
    private Location location;

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public Boolean getVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
