package mhl.service;

import mhl.dao.EmployeeDAO;
import mhl.domain.Employee;

//完成对employee表的各种操作（通过调用employeedao完成）
public class EmployeeService {

//    定义一个EmployeeDAO属性
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    public Employee getEmployeeByIdAndPwd(String empID,String pwd){
        return employeeDAO.querySingle("select * from employee where empID=? and pwd=md5(?)", Employee.class,empID,pwd);
    }




}
























































































