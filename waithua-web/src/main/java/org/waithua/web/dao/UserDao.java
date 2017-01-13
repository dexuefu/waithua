package org.waithua.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.waithua.web.base.MyBatisDao;
import org.waithua.web.entity.User;

/**
 * Created by jch on 17/1/12.
 */
@Component
public class UserDao {
    private final SqlSession sqlSession;

    public UserDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public User getById(long id) {
        return this.sqlSession.selectOne("getById", id);
    }
}
