package com.ican.service;

import com.ican.domain.Words;
import com.ican.exception.icanServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WordsService {
    void insert(Words words) throws icanServiceException;

    void delete(int id) throws icanServiceException;

    Words select(int id) throws icanServiceException;

    void update(Words words) throws icanServiceException;

    List<Words> list(String ids, String order, int page, int size) throws icanServiceException;

    int count(String ids) throws icanServiceException;

}
