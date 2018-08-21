package com.hg.crud.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hg.crud.bean.Department;
import com.hg.crud.bean.Employee;
import com.hg.crud.dao.DepartmentMapper;
import com.hg.crud.dao.EmployeeMapper;

/**
 * 测试dao层的工作
 * @author admin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {
	
	@Autowired
	DepartmentMapper departmentMapper;
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	@Autowired
	SqlSession sqlsession;
	
	
	/**
	 * 测试DepartmentMapper
	 */
	@Test
	public void testCRUD() {
		System.out.println(departmentMapper);
		
		//1、插入几个部门
//		departmentMapper.insertSelective(new Department(null,"开发部"));
//		departmentMapper.insertSelective(new Department(null,"测试部"));
		
		//2、生成员工数据，测试员工插入
//		employeeMapper.insertSelective(new Employee(null,"张三","M","zs@qq.com",1));
		
		EmployeeMapper mapper = sqlsession.getMapper(EmployeeMapper.class);
		for(int i=0; i<1000; i++) {
			String sub = UUID.randomUUID().toString().substring(0, 5)+i;
			mapper.insertSelective(new Employee(null,sub,"M",sub+"@qq.com",1));
		}
		System.out.println("批量完成！");
	}
}
