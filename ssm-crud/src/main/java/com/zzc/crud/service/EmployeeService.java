package com.zzc.crud.service;

import com.zzc.crud.bean.Employee;
import com.zzc.crud.bean.EmployeeExample;
import com.zzc.crud.dao.EmployeeMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzc
 * @Description
 * @create 2022-04-25 21:20
 */
@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 查询所有员工
     * @return
     */
    public List<Employee> getAll(){

        return employeeMapper.selectByExampleWithDept(null);
    }

    /**
     * 员工保存
     * @param employee
     */
    public void saveEmp(Employee employee){
        employeeMapper.insertSelective(employee);
    }

    /**
     * 检验用户名是否可用
     * true:代表当前姓名可用
     */
    public boolean checkUser(String empName){
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        int count = employeeMapper.countByExample(example);
        return count==0;
    }

    /**
     * 按照员工id查询员工
     * @param id
     * @return
     */
    public Employee getEmp(Integer id) {
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        return employee;
    }

    /**
     * 员工更新
     * @param employee
     */
    public void updateEmp(Employee employee) {
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    /**
     * 员工删除
     * @param id
     */
    public void deleteEmp(Integer id) {
        employeeMapper.deleteByPrimaryKey(id);
    }

    public void deleteBatch(List<Integer> ids) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpIdIn(ids);
        employeeMapper.deleteByExample(example);
    }
    /*@Test
    public void testSelect(){
        List<Employee> employees = employeeMapper.selectByExampleWithDept(null);
        for (int i = 0; i < 10; i++) {
            System.out.println(employees.get(1));
        }
    }*/
}
