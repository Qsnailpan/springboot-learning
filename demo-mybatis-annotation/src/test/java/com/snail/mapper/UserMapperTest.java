package com.snail.mapper;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.snail.entity.UserEntity;
import com.snail.enums.UserSexEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper UserMapper;

    Logger logger = LoggerFactory.getLogger (UserMapperTest.class);

    @Test
    public void testInsert() throws Exception {
        UserMapper.insert (new UserEntity ("aa", "a123456", UserSexEnum.MAN));
        UserMapper.insert (new UserEntity ("bb", "b123456", UserSexEnum.WOMAN));
        UserMapper.insert (new UserEntity ("cc", "b123456", UserSexEnum.WOMAN));

        Assert.assertEquals (3, UserMapper.getAll ().size ());
    }

    @Test
    public void testQuery() throws Exception {
        List<UserEntity> users = UserMapper.getAll ();
        logger.info (users.toString ());
    }


    @Test
    public void testUpdate() throws Exception {
        UserEntity user = UserMapper.getOne (3l);
        logger.info (user.toString ());
        user.setNickName ("snail");
        UserMapper.update (user);
        Assert.assertTrue (("snail".equals (UserMapper.getOne (3l).getNickName ())));
    }

}