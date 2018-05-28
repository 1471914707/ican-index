package com.ican.controller;

import com.ican.config.BaseConfig;
import com.ican.domain.User;
import com.ican.service.UserService;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Api(value = "controller信息")
@CrossOrigin
@Controller
@RequestMapping("/test")
public class TestController {

    final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    UserService userService;

    @Autowired
    private BaseConfig baseConfig;

    @Autowired
    private SolrClient solrClient;

    @ApiOperation(value = "测试swagger", notes = "这是一条注意信息")
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test() {

        return "test";
    }


    @ResponseBody
    @RequestMapping("/solr/{name}")
    public BaseResult testSolr(@PathVariable("name") String name) throws IOException, SolrServerException {
        BaseResult result = BaseResultUtil.initResult();
       // SolrDocument document = solrClient.getById("mycore_test", name);
        //System.out.println(document);
        //result.setData(document.toString());
        SolrQuery query = new SolrQuery();
        query.setQuery("name:" + name);
        QueryResponse response = solrClient.query(query);
        SolrDocumentList solrDocumentList = response.getResults();
        String res = "";
        for (SolrDocument solrDocument : solrDocumentList) {
            res += "\n" + solrDocument.getFieldValue("id") + "\n" + solrDocument.getFieldValue("name");
        }
        result.setData(res);
        return result;
    }
/*
    @ApiOperation(value = "上传图片", notes = "")
    @ResponseBody
    @RequestMapping(value = "/photoUpload",method = RequestMethod.POST)
    public BaseResult photoUpload(@RequestParam("file") MultipartFile file,
                                  HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            String time = DateUtil.getCurrentYM();
            if (!file.isEmpty()) {
                File normalFile = new File(file.getOriginalFilename());
                String ext = normalFile.getName().substring(normalFile.getName().lastIndexOf(".") + 1);
                String filename = System.currentTimeMillis() + "." + ext;
                String filePath = baseConfig.getBasePath() + "/"+ time;
                File path = new File(filePath);
                if (!path.exists()) {
                    path.mkdirs();
                }
                String dist = filePath + "/" + filename;
                String url = baseConfig.getBaseUrl() + time + "/" + filename;
                File localFile = new File(dist);
                FileUtil.writIn(file.getInputStream(), dist);
                BaseResultUtil.setSuccess(result, url);
                return result;
            }
            return result;
        } catch (Exception e) {
            logger.error("上传图片异常： ", e);
            e.printStackTrace();
            return result;
        }
    }
*/

    @ApiOperation(value = "测试swagger", notes = "这是一条注意信息")
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getUser(int id) {
        User user = null;
        BaseResult result = BaseResultUtil.initResult();
        try {
            logger.debug("user:");
            user = userService.select(id);
            BaseResultUtil.setSuccess(result, user);
        } catch (Exception e) {
            logger.error("异常", e);
        }
        return result;
    }

    @ApiOperation(value = "插入一条数据", notes = "")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult save(User user) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            logger.debug("user:");
            userService.insert(user);
            BaseResultUtil.setSuccess(result, user);
        } catch (Exception e) {
            logger.error("异常", e);
        }
        return result;
    }
}
