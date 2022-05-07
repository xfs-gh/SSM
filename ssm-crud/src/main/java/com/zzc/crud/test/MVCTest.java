package com.zzc.crud.test;

import com.github.pagehelper.PageInfo;
import com.zzc.crud.bean.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.PrintWriter;
import java.util.List;

/**
 * @author zzc
 * @Description 使用spring测试模块测试请求功能
 * @create 2022-04-26 15:25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        locations = {"classpath:applicationContext.xml",
        "file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml"}
        )
public class MVCTest {
    //传入springmvc的ioc
    @Autowired
    WebApplicationContext context;
    //虚拟mvc请请求
    MockMvc mockMvc;
    @Before
    public void initMockMvc(){
        mockMvc=MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    public void testPage() throws Exception {
        //模拟请求拿到返回值
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                get("/emps").param("pn", "5")).
                andReturn();
        //取出请求域中的pageInfo进行验证
        MockHttpServletRequest request = result.getRequest();
        PageInfo pi = (PageInfo)request.getAttribute("pageInfo");
        System.out.println(pi);
        System.out.println(pi.getPageNum());
        System.out.println(pi.getPages());
        System.out.println(pi.getTotal());
        int[] nums = pi.getNavigatepageNums();
        for (int i:nums){
            System.out.print(i+" ");
        }
        List<Employee> list = pi.getList();
        for(Employee e:list){
            System.out.println(e);
        }
    }
    @Test
    public void testGit(){
        System.out.println("hello git");
        System.out.println("hot-fix test");
    }
}
