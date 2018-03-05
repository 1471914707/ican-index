package com.ican.controller;

import com.ican.config.Constant;
import com.ican.model.City;
import com.ican.util.BaseResultUtil;
import com.ican.util.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 基本数据接口
 * autohor: lin jiayu
 * 2018-02-16 16：:13
 */
@Api("基本数据接口")
@RestController
public class DataController {
    private final static Logger logger = LoggerFactory.getLogger(DataController.class);

    @ApiOperation("获取国家数据接口")
    @RequestMapping(value = "/countryJson",method = RequestMethod.GET)
    public BaseResult countryJson(){
        BaseResult result = BaseResultUtil.initResult();
        try {
            List<City> cityList = Constant.ServiceFacade.getCityService().list(0,1,null,1,250);
            return BaseResultUtil.setSuccess(result, cityList);
        } catch (Exception e) {
            logger.error("获取国家数据出错", e);
            return result;
        }
    }

    @ApiOperation("获取相关省份数据接口")
    @RequestMapping(value = "/provinceJson/{parentId}",method = RequestMethod.GET)
    public BaseResult provinceJson(@PathVariable("parentId") int parentId){
        BaseResult result = BaseResultUtil.initResult();
        try {
            List<City> cityList = Constant.ServiceFacade.getCityService().list(parentId,2,null,1,250);
            return BaseResultUtil.setSuccess(result, cityList);
        } catch (Exception e) {
            logger.error("获取相关省份数出错", e);
            return result;
        }
    }

    @ApiOperation("获取相关城市数据接口")
    @RequestMapping(value = "/cityJson/{parentId}",method = RequestMethod.GET)
    public BaseResult cityJson(@PathVariable("parentId") int parentId){
        BaseResult result = BaseResultUtil.initResult();
        try {
            List<City> cityList = Constant.ServiceFacade.getCityService().list(parentId,3,null,1,250);
            return BaseResultUtil.setSuccess(result, cityList);
        } catch (Exception e) {
            logger.error("获取相关城市数出错", e);
            return result;
        }
    }
}
