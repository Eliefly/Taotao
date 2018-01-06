package com.taotao.service.impl;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.taotao.common.pojo.BasePojo;
import com.taotao.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

public class BaseServiceImpl<T extends BasePojo> implements BaseService<T> {

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

        dealTime(t);
        mapper.insert(t);
    }

    @Override
    public void saveSelective(T t) {

        dealTime(t);
        mapper.insertSelective(t);
    }

    /**
     * 处理创建时间和更新时间
     *
     * @param t
     */
    private void dealTime(T t) {
        // 需要判断，如果调用者没有设置时间，则这里设置，如果设置了时间，则这里不设置了
        if (t.getCreated() == null) {
            t.setCreated(new Date());
            t.setUpdated(t.getCreated());
        } else if (t.getUpdated() == null) {
            t.setUpdated(t.getCreated());
        }
    }

    @Override
    public void updateById(T t) {
        // 更新方法直接设置时间
        t.setUpdated(new Date());
        mapper.updateByPrimaryKey(t);
    }

    @Override
    public void updateByIdSelective(T t) {
        // 更新方法直接设置时间
        t.setUpdated(new Date());
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
