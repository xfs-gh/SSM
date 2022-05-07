package com.zzc.crud.test;


import com.zzc.crud.bean.Department;
import com.zzc.crud.bean.Employee;
import com.zzc.crud.dao.DepartmentMapper;
import com.zzc.crud.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * @author zzc
 * @Description 测试DAO层
 * @create 2022-04-24 20:55
 * 1.导入springtest模块
 * 2.使用@ContextConfiguration指定配置文件的位置
 * 3.直接autowried要使用的组件即可
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    SqlSession sqlSession;

    @Test
    public void testCRUD(){
        /*//1.创建SpringIOC容器
        ApplicationContext ioc =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        //2.从容器中获取bean
        DepartmentExample bean = ioc.getBean(DepartmentExample.class);*/
        System.out.println(departmentMapper);
        Department department = departmentMapper.selectByPrimaryKey(1);
        System.out.println(department);
        /*Employee employee = employeeMapper.selectByPrimaryKey(1);
        System.out.println(employee);*/
        //departmentMapper.insertSelective(new Department(null,"开发部门"));
        //departmentMapper.insertSelective(new Department(null,"测试部门"));
        /*employeeMapper.insertSelective(
                new Employee(null,"Jerry","M","Jerry@qq.com",1)
        );*/

        //批量插入多个员工 使用可执行批量操作的sqlSession
        /*EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        for (int i = 0; i < 1000; i++) {
            String uid = UUID.randomUUID().toString().substring(0, 5)+i;
            mapper.insertSelective(
                    new Employee(null,uid,"M",uid+"@qq.com",1)
            );
        }
        System.out.println("=========================");*/
    }
}
