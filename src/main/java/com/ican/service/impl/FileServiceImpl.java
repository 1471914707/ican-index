package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.domain.File;
import com.ican.exception.icanServiceException;
import com.ican.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public int insert(File file) throws icanServiceException {
        return Constant.DaoFacade.getFileDao().insert(file);
    }

    @Override
    public File select(int id) throws icanServiceException {
        return (File) Constant.DaoFacade.getFileDao().select(id);
    }

    @Override
    public int update(File file) throws icanServiceException {
        return Constant.DaoFacade.getFileDao().update(file);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getFileDao().delete(id);
    }

    @Override
    public int save(File file) throws icanServiceException {
        if (file == null) {
            return 0;
        }
        if (file.getId() > 0) {
            update(file);
        } else {
            if (StringUtils.isEmpty(file.getName())) {
                file.setName("");
            }
            if (StringUtils.isEmpty(file.getUrl())) {
                file.setUrl("");
            }
            insert(file);
        }
        return file.getId();
    }

    @Override
    public List<File> list(String ids, int userId, int targetId, int type, String order, int page, int size) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("userId", userId);
        param.put("targetId", targetId);
        param.put("type", type);
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getFileDao().list(param);
    }

    @Override
    public int count(String ids, int userId, int targetId, int type) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("userId", userId);
        param.put("targetId", targetId);
        param.put("type", type);
        return Constant.DaoFacade.getFileDao().count(param);
    }
}
