package com.hg.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.crud.bean.Employee;
import com.hg.crud.bean.EmployeeExample;
import com.hg.crud.bean.EmployeeExample.Criteria;
import com.hg.crud.dao.EmployeeMapper;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeMapper employeeMapper;

	//查询所有员工
	public List<Employee> getAll() {
		// TODO Auto-generated method stub
		return employeeMapper.selectByExampleWithDept(null);
	}
	
	public void saveEmp(Employee employee) {
		// TODO Auto-generated method stub
		employeeMapper.insertSelective(employee);
	}

	//检验用户名是否可用
	//true可用     false不可用
	public boolean checkUser(String empName) {
		// TODO Auto-generated method stub
		EmployeeExample example = new EmployeeExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEmpNameEqualTo(empName);
		long count = employeeMapper.countByExample(example);
		return count == 0;
	}
	
	//获取员工名字
	public Employee getEmp(Integer id) {
		// TODO Auto-generated method stub
		Employee key = employeeMapper.selectByPrimaryKey(id);
		return key;
	}
	
	//更新员工
	public void updateEmp(Employee employee) {
		// TODO Auto-generated method stub
		employeeMapper.updateByPrimaryKeySelective(employee);
	}
	
	//删除员工
	public void deleteEmp(Integer id) {
		// TODO Auto-generated method stub
		employeeMapper.deleteByPrimaryKey(id);
	}
	
	//批量删除
	public void deleBatch(List<Integer> ids) {
		// TODO Auto-generated method stub
		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmpIdIn(ids);
		employeeMapper.deleteByExample(example);
	} 
}
