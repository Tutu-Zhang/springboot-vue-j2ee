package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Transport;
import com.example.demo.mapper.EmployeeMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@ResponseBody
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    EmployeeMapper employeeMapper;
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<Employee> wrapper=Wrappers.<Employee>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Employee::getNickName,search);
        }
        Page<Employee> employeePage = employeeMapper.selectPage(new Page<>(pageNum,pageSize), wrapper);
        return Result.success(employeePage);
    }

    @GetMapping("/driver")
    public Result<?> loadDriver(@RequestParam(defaultValue = "司机") String position){
        LambdaQueryWrapper<Employee> wrapper=Wrappers.<Employee>lambdaQuery();
        wrapper.eq(Employee::getPosition,position);
        Page<Employee> EmployeePage = employeeMapper.selectPage(new Page<>(1,10), wrapper);
        return Result.success(EmployeePage);
    }

    @PostMapping
    public Result<?> save(@RequestBody Employee employee){
        if(employee.getPosition() == null){
            employee.setPosition("新进人员");
        }
        employee.setSituation("空闲");
        employeeMapper.insert(employee);
        return Result.success();
    }


    @PutMapping
    public Result<?> update(@RequestBody Employee employee){
        if(employee.getPosition() == null){
            employee.setPosition("新进人员");
        }
        if(employee.getSituation() == null){
            employee.setSituation("空闲");
        }
        employeeMapper.updateById(employee);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id){
        employeeMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id){
        return Result.success(employeeMapper.selectById(id));
    }

}
