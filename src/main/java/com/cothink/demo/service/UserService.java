package com.cothink.demo.service;

import com.cothink.demo.model.User;
import com.cothink.demo.repo.UserRepo;
import com.cothink.demo.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    /**
     * 注册
     * @param user
     * @return
     */
    public JsonResult register(User user) {
        if(userRepo.findByPhone(user.getPhone()) != null || userRepo.findByCardId(user.getCardId()) != null){
            return new JsonResult().setFlag(false).setMsg("手机号或者身份证已注册！");
        }
        userRepo.save(user);
        return new JsonResult().setFlag(true).setMsg("注册成功！");
    }
}
