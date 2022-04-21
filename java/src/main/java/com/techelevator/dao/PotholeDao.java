package com.techelevator.dao;

import com.techelevator.model.Pothole;

import java.util.List;

public interface PotholeDao {

    List<Pothole> listAllPotholes();

    Pothole getPotholeByPotholeId(Integer potholeId);

    List<Pothole> getPotholesByUserId(Integer userId);

    void createPothole(Pothole pothole);

    void updatePothole(Pothole pothole, Integer potholeId);

    void deletePothole(Integer potholeId);

}
