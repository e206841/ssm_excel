package com.ln.web.db2.dao;

import java.util.List;

import com.ln.web.db2.model.GoodTable;

public interface GoodTableMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodTable record);

    int insertSelective(GoodTable record);

    GoodTable selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodTable record);

    int updateByPrimaryKey(GoodTable record);

	List<GoodTable> getGoods();
}