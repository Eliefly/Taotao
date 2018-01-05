package com.taotao.service.impl;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.taotao.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    private Mapper<T> mapper;

    private Class<T> clazz;

    public BaseServiceImpl() {

        // 获取父类的 Type
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();

        // 把获取到的Type强制类型转换为字节码
        clazz = (Class<T>) type.getActualTypeArguments()[0];

    }

    @Override
    public T queryById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> queryAll() {
        return mapper.select(null);
    }

    @Override
    public Integer queryCountByWhere(T t) {
        return mapper.selectCount(t);
    }

    @Override
    public List<T> queryListByWhere(T t) {
        return mapper.select(t);
    }

    @Override
    public List<T> queryByPage(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        return mapper.select(null);
    }

    @Override
    public T queryOne(T t) {
        return mapper.selectOne(t);
    }

    @Override
    public void save(T t) {
        mapper.insert(t);
    }

    @Override
    public void saveSelective(T t) {
        mapper.insertSelective(t);
    }

    @Override
    public void updateById(T t) {
        mapper.updateByPrimaryKey(t);
    }

    @Override
    public void updateByIdSelective(T t) {
        mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public void deleteById(Long id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(List<Object> ids) {

        Example example = new Example(clazz);

        example.createCriteria().andIn("ids", ids);

        mapper.deleteByExample(example);

    }
}
