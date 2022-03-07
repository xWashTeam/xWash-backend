package com.xWash.service.Impl;

import com.xWash.model.dao.Machine;
import com.xWash.model.entity.QueryResult;
import com.xWash.util.RedisUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("distributor")
public class Distributor {
    private final BeanFactory beanFactory;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    public Distributor(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public QueryResult check(Machine machine) {
        AbstractChecker checker = null;
        assert !machine.getBelong().equals("");
        checker = (AbstractChecker) beanFactory.getBean(machine.getBelong());
        QueryResult qr = checker.check(machine);
        qr.setDate(new Date());
        qr.setLocation(machine.getLocation());
        return qr;
    }
}
