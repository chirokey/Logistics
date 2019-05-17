package com.logistics.dao.neo4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MapDAOImpl implements MapDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public MapDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String GET_DISTANCE_QUERY = "MATCH (cityFrom:City{name:{1}, country:'Russia'})" +
            "-[p:PATH]-" +
            "(cityTo:City{name:{2}, country:'Russia'})" +
            "return p.distance as dist";
    @Override
    public Integer getDistanceBetweenCities(String cityFrom, String cityTo) {
        if (cityFrom.equals(cityTo)) return 0;
        return jdbcTemplate.queryForObject(GET_DISTANCE_QUERY,
                (rs, rowNum) -> (int)rs.getDouble("dist"),
                cityFrom, cityTo);
    }

    private String GET_COORDINATES_QUERY = "MATCH (c:City{name:{1}, country:'Russia'}) " +
            "return c.location.x as x, c.location.y as y";
    @Override
    public double[] getCityCoordinates(String city) {
        return jdbcTemplate.queryForObject(GET_COORDINATES_QUERY,
                (rs, rowNum) -> new double[] {rs.getDouble("x"), rs.getDouble("y") },
                city);
    }
}
