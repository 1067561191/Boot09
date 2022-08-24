package edu.cming.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "location")
public class Location {

    @Value("${location.address}")
    private String address;
    @Value("${location.lng}")
    private String lng;
    @Value("${location.lat}")
    private String lat;

    public Location() {
    }

    public Location(String address, String lng, String lat) {
        this.address = address;
        this.lng = lng;
        this.lat = lat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "Location{" +
                "address='" + address + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }
}
