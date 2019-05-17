package com.logistics.dao.neo4j;

public interface MapDAO {
    Integer getDistanceBetweenCities(String cityFrom, String cityTo);
    double[] getCityCoordinates(String city);
}
