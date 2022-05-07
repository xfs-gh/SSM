package com.zzc.crud.service;

import com.zzc.crud.bean.Department;
import com.zzc.crud.dao.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zzc
 * @Description
 * @create 2022-05-05 9:59
 */
@Service
public class DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    public List<Department> getDepts(){
        List<Department> list = departmentMapper.selectByExample(null);
        return list;
    }
}
