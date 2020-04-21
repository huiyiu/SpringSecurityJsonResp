package com.cothink.demo.service;

import com.cothink.demo.model.Location;
import com.cothink.demo.repo.LocationRepo;
import com.cothink.demo.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LocationService {
    @Autowired
    LocationRepo locationRepo;

    /**
     *  上传位置信息
     * @param location
     * @return
     */
    public JsonResult report(Location location) {
        location.setUpdateTime(new Date());
        locationRepo.save(location);
        return new JsonResult().setFlag(true).setMsg("上报成功");
    }

    /**
     * 获取位置列表
     * @param userId
     * @return
     */
    public JsonResult list(String userId) {
        List<Location> locationList = locationRepo.findByUserIdOrderByUpdateTime(userId);
        return new JsonResult().setFlag(true).setResult(locationList);
    }
}
