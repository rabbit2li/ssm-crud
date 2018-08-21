package com.hg.crud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hg.crud.bean.Employee;
import com.hg.crud.bean.Msg;
import com.hg.crud.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	// 更新
	@ResponseBody
	@RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
	public Msg saveEmp(Employee employee) {
		employeeService.updateEmp(employee);
		return Msg.success();
	}

	// 删除
	@RequestMapping(value = "/emp/{ids}")
	@ResponseBody
	public Msg deleteEmpById(@PathVariable("ids") String ids) {
		//批量删除
		List<Integer> del_ids = new ArrayList<Integer>();
		if (ids.contains("-")) {
			String[] split = ids.split("-");
			for (String string : split) {
				del_ids.add(Integer.parseInt(string));
			}
			employeeService.deleBatch(del_ids);
		} else {
			Integer id = Integer.parseInt(ids);
			employeeService.deleteEmp(id);
		}
		return Msg.success();
	}

	@ResponseBody
	@RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
	public Msg getEmp(@PathVariable("id") Integer id) {
		Employee employee = employeeService.getEmp(id);
		return Msg.success().add("emp", employee);
	}

	// 检查用户名是否可用
	@ResponseBody
	@RequestMapping("/checkuser")
	public Msg checkuser(@RequestParam("empName") String empName) {
		// 先判断用户名是否为合法表达式
		String regex = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
		if (!empName.matches(regex)) {
			return Msg.fail().add("va_msg", "用户名必须是2-5位中文或6-16位英文和数字的组合");
		}

		// 数据库用户名重复校验
		boolean b = employeeService.checkUser(empName);
		if (b) {
			return Msg.success();
		} else {
			return Msg.fail().add("va_msg", "用户名不可用");
		}
	}

	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		// 引入pagehelper分页插件
		PageHelper.startPage(pn, 5);
		// startPage后紧跟的一个查询就是分页查询
		List<Employee> emps = employeeService.getAll();
		// 使用pageInfo包装查询后的结果
		PageInfo page = new PageInfo(emps, 5);
		return Msg.success().add("pageInfo", page);
	}

	// 员工保存
	@RequestMapping(value = "/emp", method = RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee, BindingResult result) {
		if (result.hasErrors()) {
			// 校验失败,返回错误信息
			Map<String, Object> map = new HashMap<String, Object>();
			List<FieldError> fieldErrors = result.getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				System.out.println("错误的字段名" + fieldError.getField());
				System.out.println("错误信息" + fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", map);
		} else {
			employeeService.saveEmp(employee);
			return Msg.success();
		}

	}

	// @RequestMapping("/emps")
	public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
		// 引入pagehelper分页插件
		PageHelper.startPage(pn, 5);
		// startPage后紧跟的一个查询就是分页查询
		List<Employee> emps = employeeService.getAll();
		// 使用pageInfo包装查询后的结果
		PageInfo page = new PageInfo(emps, 5);
		model.addAttribute("pageInfo", page);
		return "list";
	}
}
