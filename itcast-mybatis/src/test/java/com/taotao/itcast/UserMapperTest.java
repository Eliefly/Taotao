package com.taotao.itcast;


import com.taotao.itcast.mapper.UserMapper;
import com.taotao.itcast.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;


public class UserMapperTest {
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void setUp() throws Exception {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        this.sqlSessionFactory = builder.build(inputStream);
    }

    @Test
    public void testQueryUserById() {
        SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User user = userMapper.queryUserById(2l);

        System.out.println(user);

        sqlSession.close();
    }

    @Test
    public void testSaveUser() {
        SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User user = new User();
        user.setId(null);
        user.setName("韩梅梅");
        user.setSex(2);
        user.setUserName("hanmeimei3");

        userMapper.saveUser(user);

        System.out.println(user);

        sqlSession.close();
    }

    @Test
    public void testUpdateUserById() {
        SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User user = new User();
        user.setId(7l);
        user.setName("李传文");
        user.setSex(3);
        user.setUserName("lichuanwen");

        userMapper.updateUserById(user);

        sqlSession.close();
    }

    @Test
    public void testDeleteUserById() {
        SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        userMapper.deleteUserById(7l);

        sqlSession.close();
    }


}
