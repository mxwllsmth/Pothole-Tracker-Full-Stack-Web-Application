package com.techelevator.service;

import com.techelevator.model.Pothole;

import java.util.List;

public interface PotholeService {

    List<Pothole> allPotholes();

    Pothole potholeByPotholeId(Integer potholeId);

    List<Pothole> potholesByUserId(Integer userId);

    boolean create(Pothole pothole);

    boolean update(Pothole pothole, Integer potholeId);

    boolean delete(Integer potholeId);

}
