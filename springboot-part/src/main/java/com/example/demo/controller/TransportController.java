package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Transport;
import com.example.demo.mapper.TransportMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@ResponseBody
@RestController
@RequestMapping("/transport")
public class TransportController {

    @Resource
    TransportMapper transportMapper;

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<Transport> wrapper=Wrappers.<Transport>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Transport::getCargoInfo,search).or().
                    like(Transport::getReceiver,search).or().
                    like(Transport::getSender,search).or().
                    like(Transport::getSenderNum,search).or().
                    like(Transport::getReceiverNum,search).or().
                    like(Transport::getReceiverAddress,search).or().
                    like(Transport::getSenderAddress,search).or().
                    like(Transport::getCargoSituation,search).or().
                    like(Transport::getCargoPosition,search).or().
                    like(Transport::getVehicleInfo,search);
        }
        Page<Transport> TransportPage = transportMapper.selectPage(new Page<>(pageNum,pageSize), wrapper);
        return Result.success(TransportPage);
    }

    @GetMapping("/receipt/{id}/{username}/{number}")
    public Result<?> loadReceipt(@PathVariable(name = "id") Long id,@PathVariable(name = "username") String username,@PathVariable(name = "number") String number){
        LambdaQueryWrapper<Transport> wrapper=Wrappers.<Transport>lambdaQuery();

        wrapper.eq(Transport::getReceiver,username).eq(Transport::getReceiverNum,number);
        wrapper.or().eq(Transport::getReceiverId,id);
        Page<Transport> TransportPage = transportMapper.selectPage(new Page<>(1,10), wrapper);
        return Result.success(TransportPage);
    }

    @GetMapping("/shipment/{id}")
    public Result<?> loadShipment(@PathVariable Integer id){
        LambdaQueryWrapper<Transport> wrapper=Wrappers.<Transport>lambdaQuery();
        wrapper.eq(Transport::getSenderId,id);

        Page<Transport> TransportPage = transportMapper.selectPage(new Page<>(1,10), wrapper);
        return Result.success(TransportPage);
    }

    @PostMapping
    public Result<?> save(@RequestBody Transport transport){
        if(transport.getCargoInfo() == null){
            transport.setCargoInfo("????????????");
        }
        transport.setCargoSituation("?????????");
        transport.setVehicleId(0);
        transportMapper.insert(transport);
        return Result.success();
    }


    @PutMapping
    public Result<?> update(@RequestBody Transport transport){
        if(transport.getVehicleId() == null){
            transport.setVehicleId(0);
        }
        transportMapper.updateById(transport);
        return Result.success();
    }

    @PutMapping("/receive")
    public Result<?> receive(@RequestBody Transport transport){
        transport.setCargoSituation("?????????");
        transportMapper.updateById(transport);
        return Result.success();
    }

    @PutMapping("/ship")
    public Result<?> ship(@RequestBody Transport transport){
        transport.setCargoSituation("?????????");
        transportMapper.updateById(transport);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id){
        transportMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id){
        return Result.success(transportMapper.selectById(id));
    }

}
