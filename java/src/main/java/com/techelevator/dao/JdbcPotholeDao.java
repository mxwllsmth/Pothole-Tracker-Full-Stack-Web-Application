package com.techelevator.dao;

import com.techelevator.model.Pothole;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPotholeDao implements PotholeDao{

    //JDBC Template Instantiation
    private final JdbcTemplate jdbcTemplate;

    //Constructor
    public JdbcPotholeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Pothole> listAllPotholes() {
        List<Pothole> potholeList = new ArrayList<>();

        String sql = "SELECT pothole_id, user_id, progress_status_id, severity_id, latitude, longitude, street_address, reported_date, inspected_date, repaired_date, notes FROM potholes;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while(results.next()) {
            Pothole pothole = mapRowToPothole(results);
            potholeList.add(pothole);
        }
        return potholeList;
    }

    @Override
    public Pothole getPotholeByPotholeId(Integer potholeId) {
        Pothole pothole = null;

        String sql = "SELECT pothole_id, user_id, progress_status_id, severity_id, latitude, longitude, street_address, reported_date, inspected_date, repaired_date, notes FROM potholes WHERE pothole_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, potholeId);

        if (results.next()) {
            pothole = mapRowToPothole(results);
        }
        return pothole;
    }

    @Override
    public List<Pothole> getPotholesByUserId(Integer userId) {
        List<Pothole> potholeList = new ArrayList<>();

        String sql = "SELECT pothole_id, user_id, progress_status_id, severity_id, latitude, longitude, street_address, reported_date, inspected_date, repaired_date, notes FROM potholes WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

        while(results.next()) {
            Pothole pothole = mapRowToPothole(results);
            potholeList.add(pothole);
        }
        return potholeList;
    }

    @Override
    public void createPothole(Pothole pothole) {
        String sql = "INSERT INTO potholes (user_id, progress_status_id, severity_id, latitude, longitude, street_address, reported_date, inspected_date, repaired_date, notes) " +
                    "VALUES (?, ?, ?, ?, ?, ?, DEFAULT, ?, ?, ?);";
        jdbcTemplate.update(sql,
                pothole.getUserId(), pothole.getProgressStatusId(), pothole.getSeverityId(),
                pothole.getLatitude(), pothole.getLongitude(), pothole.getStreetAddress(),
                pothole.getInspectedDate(), pothole.getRepairedDate(), pothole.getNotes());
    }

    @Override
    public void updatePothole(Pothole pothole, Integer potholeId) {
        String sql = "UPDATE potholes SET progress_status_id = ?, severity_id = ?, inspected_date = ?, repaired_date = ?, notes = ? " +
                "WHERE pothole_id = ?;";
        jdbcTemplate.update(sql, pothole.getProgressStatusId(), pothole.getSeverityId(), pothole.getInspectedDate(), pothole.getRepairedDate(), pothole.getNotes(), potholeId);
    }

    @Override
    public void deletePothole(Integer potholeId) {
        String sql = "DELETE FROM potholes WHERE pothole_id = ?;";
        jdbcTemplate.update(sql, potholeId);
    }

    private Pothole mapRowToPothole(SqlRowSet rs) {
        Pothole pothole = new Pothole();
        pothole.setPotholeId(rs.getInt("pothole_id"));
        pothole.setUserId(rs.getInt("user_id"));
        pothole.setProgressStatusId(rs.getInt("progress_status_id"));
        pothole.setSeverityId(rs.getInt("severity_id"));
        pothole.setLatitude(rs.getString("latitude"));
        pothole.setLongitude(rs.getString("longitude"));
        pothole.setStreetAddress(rs.getString("street_address"));
        pothole.setReportedDate(rs.getDate("reported_date"));
        pothole.setInspectedDate(rs.getString("inspected_date"));
        pothole.setRepairedDate(rs.getString("repaired_date"));
        pothole.setNotes(rs.getString("notes"));

        return pothole;
    }
}
