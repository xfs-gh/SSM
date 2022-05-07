package com.zzc.crud.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzc.crud.bean.Employee;
import com.zzc.crud.bean.Msg;
import com.zzc.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zzc
 * @Description 处理员工增删改查
 * @create 2022-04-25 21:14
 */
@Controller
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    /**
     * 单个  批量删除二合一
     * 批量删除：1-2-3
     * 单个删除：1
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{ids}",method = RequestMethod.DELETE)
    public Msg deleteEmp(@PathVariable("ids") String ids){
        if(ids.contains("-")){
            //批量删除
            String[] strIds = ids.split("-");
            List<Integer> delIds = new ArrayList<>();
            //组装id的集合
            for(String id:strIds){
                delIds.add(Integer.parseInt(id));
            }
            employeeService.deleteBatch(delIds);
        }else {
            //单个删除
            int id = Integer.parseInt(ids);
            employeeService.deleteEmp(id);
        }

        return Msg.success();
    }

    /**
     * 如果直接发送ajax=PUT请求
     * 封装的数据
     * Employee{empId=1008, empName='null', gender='null', email='null', dId=null, department=null}
     * 问题：
     * 请求体中有数据，但是Employee对象封装不上
     * 原因：
     * Tomcat：1.将请求体的数据 封装成一个map
     * 2.request.getParameter("empName")就会从这个map中取值
     * 3.SpringMVC封装pojo对象的时候，会把pojo中每个属性的值 request.getParameter("email")
     *
     * AJAX发送put请求：
     * put请求，请求体中的数据，request.getParameter("email")拿不到
     * Tomcat不会封装put请求中的数据为map，只有post请求才封装请求体
     * 我们要能支持直接发送put请求还要封装请求体中的数据
     * 配置上FormContentFilter；
     * 作用：将请求体中的数据解析包装成map
     * request被重新包装 request.getParameter()被重写，就会从自己封装的map中取数据
     * @param employee
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{empId}",method = RequestMethod.PUT)
    public Msg saveEmp(Employee employee,HttpServletRequest request){
        System.out.println(request.getParameter("gender"));
        System.out.println("将要更新的数据");
        System.out.println(employee);
        employeeService.updateEmp(employee);
        return Msg.success();
    }

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    @RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Msg getEmp(@PathVariable("id") Integer id){
        Employee employee=employeeService.getEmp(id);
        return Msg.success().add("emp",employee);
    }

    /**
     * 检查用户名是否可用
     * @param empName
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkuser")
    public Msg checkuser(@RequestParam("empName") String empName){
        //先判断用户名是否是合法的表达式；
        String regx="(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
        if(!empName.matches(regx)){
            return Msg.fail().add("va_msg","用户名必须是2-5位中文或者6-16位英文和数字的组合");
        }
        //数据库用户名重复校验
        boolean b = employeeService.checkUser(empName);
        if(b){
            return Msg.success();
        }else {
            return Msg.fail().add("va_msg","用户名不可用");
        }
    }

    /**
     * 员工保存
     * 1.支持JSR303校验
     * 2.导入Hibernate-Validator
     * @return
     */
    @RequestMapping(value = "/emp",method = RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(@Valid Employee employee, BindingResult result){
        if(result.hasErrors()){
            //校验失败，应该返回失败，在模态框中显示校验失败的错误信息
            Map<String,Object> map = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError:errors){
                System.out.println("错误的字段名"+fieldError.getField());
                System.out.println("错误信息"+fieldError.getDefaultMessage());
                map.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errorFields",map);
        }else {
            employeeService.saveEmp(employee);
            return Msg.success();
        }
    }

    /**
     * 导入Jackson jar包
     * @param pn
     * @return
     */
    @RequestMapping("/emps")
    @ResponseBody
    public Msg getEmpsWithJson(
            @RequestParam(value = "pn",defaultValue = "1") Integer pn){
        //这不是分页查询
        //引入PageHelper分页插件
        //在查询功能之前使用PageHelper.startPage(int pageNum, int pageSize)开启分页功能
        PageHelper.startPage(pn,5);
        List<Employee> emps = employeeService.getAll();
        //使用pageInfo查询后的结果，只需要将pageInfo交给页面，封装了详细的分页信息，包括查看出来的数据
        PageInfo page = new PageInfo(emps,5);
        return Msg.success().add("pageInfo",page);
    }
    /**
     * 查询员工数据  分页查询
     * @return
     */
    /*@RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "pn",defaultValue = "1") Integer pn, Model model){
        //这不是分页查询
        //引入PageHelper分页插件
        //在查询功能之前使用PageHelper.startPage(int pageNum, int pageSize)开启分页功能
        PageHelper.startPage(pn,5);
        List<Employee> emps = employeeService.getAll();
        //使用pageInfo查询后的结果，只需要将pageInfo交给页面，封装了详细的分页信息，包括查看出来的数据
        PageInfo page = new PageInfo(emps,5);
        model.addAttribute("pageInfo",page);
        return "list";
    }*/
}
