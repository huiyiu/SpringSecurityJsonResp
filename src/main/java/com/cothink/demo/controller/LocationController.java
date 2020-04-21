package com.cothink.demo.controller;

import com.cothink.demo.model.Location;
import com.cothink.demo.model.User;
import com.cothink.demo.repo.UserRepo;
import com.cothink.demo.service.LocationService;
import com.cothink.demo.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("location")
public class LocationController {
    @Autowired
    LocationService locationService;
    @Autowired
    UserRepo userRepo;

    @RequestMapping("/report")
    public JsonResult report(Location location, Principal principal){
        String userId = principal.getName();
        User user = userRepo.findByCardId(userId);
        location.setUserId(user.getId());
        return locationService.report(location);
    }

    @RequestMapping("/list")
    public JsonResult list(Principal principal){
        String cardId = principal.getName();
        User user = userRepo.findByCardId(cardId);
        return locationService.list(user.getId());
    }
}
