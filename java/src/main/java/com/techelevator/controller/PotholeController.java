package com.techelevator.controller;

import com.techelevator.dao.PotholeDao;
import com.techelevator.model.Pothole;
import com.techelevator.service.RestPotholeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class PotholeController {

    private final RestPotholeService potholeService;

    public PotholeController(RestPotholeService potholeService) {
        this.potholeService = potholeService;
    }


    @PreAuthorize("permitAll")
    @RequestMapping(value = "/potholes", method = RequestMethod.GET)
    public List<Pothole> listAllPotholes(){
        return potholeService.allPotholes();
    }

    @PreAuthorize("permitAll")
    @RequestMapping(value = "/potholes/{potholeId}", method = RequestMethod.GET)
    public Pothole getPotholeByPotholeId(@PathVariable Integer potholeId) {
        return potholeService.potholeByPotholeId(potholeId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/potholes/user/{userId}", method = RequestMethod.GET)
    public List<Pothole> getPotholesByUserId(@PathVariable Integer userId) {
        return potholeService.potholesByUserId(userId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/potholes/createPothole", method = RequestMethod.POST)
    public boolean createPothole(@RequestBody Pothole pothole) {
        return potholeService.create(pothole);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/potholes/updatePothole/{potholeId}", method = RequestMethod.PUT)
    public boolean updatePothole(@RequestBody Pothole pothole, @PathVariable Integer potholeId) {
        return potholeService.update(pothole, potholeId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/potholes/deletePothole/{potholeId}", method = RequestMethod.DELETE)
    public boolean deletePothole(@PathVariable Integer potholeId) {
        return potholeService.delete(potholeId);
    }
}
