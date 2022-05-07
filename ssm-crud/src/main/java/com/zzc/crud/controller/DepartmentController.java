package com.zzc.crud.controller;

import com.zzc.crud.bean.Department;
import com.zzc.crud.bean.Msg;
import com.zzc.crud.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author zzc
 * @Description 处理和部门有关的请求
 * @create 2022-05-05 9:58
 */
@Controller
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    /**
     * 返回所有部门信息
     */
    @RequestMapping("/depts")
    @ResponseBody
    public Msg getDepts(){
        List<Department> list = departmentService.getDepts();
        return Msg.success().add("depts",list);
    }
}
