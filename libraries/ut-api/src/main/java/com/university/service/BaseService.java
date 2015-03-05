package com.university.service;

import com.university.repository.PersonRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by eshevchenko on 04.03.15.
 */
public abstract class BaseService {

    private Logger logger;
    @Autowired
    protected PersonRepository personRepository;

    public Logger getLogger() {
        if (logger == null) {
            logger = Logger.getLogger(getClass());
        }
        return logger;
    }

    protected static <T> T firstFrom(List<T> list) {
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }
}
