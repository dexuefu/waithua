package org.waithua.web.base;

import org.apache.ibatis.session.SqlSession;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Created by jch on 17/1/12.
 */
public class MyBatisDao <T extends Serializable> {
    public static final String SQL_SAVE = "save";
    public static final String SQL_UPDATE = "update";
    public static final String SQL_GETBYID = "getById";
    public static final String SQL_DELETEBYID = "deleteById";
    public static final String SQL_DELETEBYIDS = "deleteByIds";
    public static final String SQL_FINDPAGEBY = "findPageBy";
    public static final String SQL_FINDLISTBY = "findListBy";
    public static final String SQL_GETCOUNTBY = "getCountBy";

    private final SqlSession sqlSession;

    public MyBatisDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public T getById(long id) {
        return this.sqlSession.selectOne(SQL_GETBYID, id);
    }
}
