package com.xWash.service.intf;

import com.xWash.model.dao.Machine;
import com.xWash.model.entity.QueryResult;

public interface IChecker {
    QueryResult check(Machine machine);
}
