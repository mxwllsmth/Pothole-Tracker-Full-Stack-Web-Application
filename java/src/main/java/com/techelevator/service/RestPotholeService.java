package com.techelevator.service;

import com.techelevator.dao.PotholeDao;
import com.techelevator.model.Pothole;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestPotholeService implements PotholeService {

    private final PotholeDao potholeDao;

    public RestPotholeService(PotholeDao potholeDao) {
        this.potholeDao = potholeDao;
    }

    public List<Pothole> allPotholes() {
        return potholeDao.listAllPotholes();
    }

    public Pothole potholeByPotholeId(Integer potholeId) {
        return potholeDao.getPotholeByPotholeId(potholeId);
    }

    public List<Pothole> potholesByUserId(Integer userId) {
        return potholeDao.getPotholesByUserId(userId);
    }

    public boolean create(Pothole pothole) {
        boolean isCreated = false;

        try {
            potholeDao.createPothole(pothole);
            isCreated = true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return isCreated;
    }

    public boolean update(Pothole pothole, Integer potholeId) {
        boolean isUpdated = false;

        try {
            potholeDao.updatePothole(pothole, potholeId);
            isUpdated = true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return isUpdated;
    }

    public boolean delete(Integer potholeId) {
        boolean isDeleted = false;

        try {
            potholeDao.deletePothole(potholeId);
            isDeleted = true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return isDeleted;
    }
}
