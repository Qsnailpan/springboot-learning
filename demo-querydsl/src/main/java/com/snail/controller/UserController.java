package com.snail.controller;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.snail.jpa.UserRepository;
import com.snail.model.QUser;
import com.snail.model.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;



/**
 *
 * @author snail
 * @version 1.0.0
 *
 */
@Api(value = "/users", description = "用户接口")
@RestController
@RequestMapping(value="/users")   
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EntityManager em;
	private QUser qUser = QUser.user;  // 相当于得到user表
	/**  
     * querydsl-data-jap 
     */
    @ApiOperation(value="获取用户列表", notes="获取所有用户列表")
    @RequestMapping(value="/list", method=RequestMethod.GET)
    public List<User> getUserList() {
    	JPAQuery<User> query = new JPAQuery<User>(em);
		return query.select(qUser).from(qUser).fetch();
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public User getUser(@ApiParam(value = "用户ID", required = true)@PathVariable Long id) {
    	JPAQuery<User> query = new JPAQuery<User>(em);
		return query.select(qUser).from(qUser).where(qUser.id.eq(id)).fetchOne();
    }
 
    @ApiOperation(value="获取用户列表", notes="queryDSL 动态查询")
    @RequestMapping(value="/list1", method=RequestMethod.GET)
    public List<User> getAll1(
    		@ApiParam(value = "年龄")@RequestParam(value = "age", required = false)Integer age,
    		@ApiParam(value = "姓名")@RequestParam(value = "name", required = false)String name) {
    	JPAQuery<User> query = new JPAQuery<User>(em);
    	if(!Objects.isNull(age)){
    		query.where(qUser.age.eq(age));
    	}
    	if(StringUtils.isNotBlank(name)){
    		query.where(qUser.name.contains(name));
    	}
    	
    /*	query.select(qUser.name).from(qUser).fetch();
    	query.select(qUser.id.count()).from(qUser).fetch();
    	query.select(qUser.age.countDistinct()).from(qUser).fetch();
    */
    	
		return query.select(qUser).from(qUser).fetch();
    }
  /**  
   *    spring-data-jpa 整合 querydsl-data-jpa
   *     
   * @param age
   * @param name
   * @return
   */
    @ApiOperation(value="获取用户列表", notes="queryDSL 动态查询")
    @RequestMapping(value="/list2", method=RequestMethod.GET)
    public List<User> getAll2(
    		@ApiParam(value = "年龄")@RequestParam(value = "age", required = false)Integer age,
    		@ApiParam(value = "姓名")@RequestParam(value = "name", required = false)String name) {
    	
    	BooleanBuilder builder = new BooleanBuilder();
    	if(!Objects.isNull(age)){
    		builder.and(qUser.age.eq(age));
    	}
    	if(StringUtils.isNotBlank(name)){
    		builder.and(qUser.name.eq(name));
    	}
		return 	(List<User>) userRepository.findAll(builder);
    }   

}