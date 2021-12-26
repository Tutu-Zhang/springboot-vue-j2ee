package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Vehicle;
import com.example.demo.mapper.VehicleMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@ResponseBody
@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Resource
    VehicleMapper vehicleMapper;
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<Vehicle> wrapper=Wrappers.<Vehicle>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Vehicle::getVehicleInfo,search);
        }
        Page<Vehicle> vehiclePage = vehicleMapper.selectPage(new Page<>(pageNum,pageSize), wrapper);
        return Result.success(vehiclePage);
    }

    @PostMapping
    public Result<?> save(@RequestBody Vehicle vehicle){
        vehicle.setDriverId(0);
        vehicleMapper.insert(vehicle);
        return Result.success();
    }


    @PutMapping
    public Result<?> update(@RequestBody Vehicle vehicle){
        vehicleMapper.updateById(vehicle);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id){
        vehicleMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id){
        return Result.success(vehicleMapper.selectById(id));
    }

}
