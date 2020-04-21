package com.cothink.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(chain = true)
public class JsonResult {
    private Boolean flag;
    private String msg;
    private Object result;
}
