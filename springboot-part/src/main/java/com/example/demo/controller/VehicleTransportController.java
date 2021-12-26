package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.entity.VehicleTransport;
import com.example.demo.mapper.VehicleTransportMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@ResponseBody
@RestController
@RequestMapping("/vehicleTransport")
public class VehicleTransportController {

    @Resource
    VehicleTransportMapper vehicleTransportMapper;
    @GetMapping
    public Result<?> findPage(){
        LambdaQueryWrapper<VehicleTransport> wrapper=Wrappers.<VehicleTransport>lambdaQuery();
        Page<VehicleTransport> vehicleTransportPage = vehicleTransportMapper.selectPage(new Page<>(), wrapper);
        return Result.success(vehicleTransportPage);
    }

    @GetMapping("/{id}")
    public Result<?> find(@PathVariable Long id){
        LambdaQueryWrapper<VehicleTransport> wrapper=Wrappers.<VehicleTransport>lambdaQuery();
        wrapper.eq(VehicleTransport::getVehicleId,id);
        Page<VehicleTransport> vehicleTransportPage = vehicleTransportMapper.selectPage(new Page<>(), wrapper);
        return Result.success(vehicleTransportPage);
    }

    @PostMapping
    public Result<?> save(@RequestBody VehicleTransport vehicleTransport){
        LambdaQueryWrapper<VehicleTransport> wrapper=Wrappers.<VehicleTransport>lambdaQuery();
        VehicleTransport res = vehicleTransportMapper.selectOne(wrapper.eq(VehicleTransport::getTransportId,vehicleTransport.getTransportId()));

        if(res == null)
        vehicleTransportMapper.insert(vehicleTransport);

        return Result.success();
    }


    @PutMapping
    public Result<?> update(@RequestBody VehicleTransport VehicleTransport){
        vehicleTransportMapper.updateById(VehicleTransport);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id){
        vehicleTransportMapper.delete(Wrappers.<VehicleTransport>lambdaQuery().eq(VehicleTransport::getTransportId,id));
        return Result.success();
    }

}
