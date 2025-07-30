package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // MD5 加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus().equals(StatusConstant.DISABLE)) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    @Override
    public void save(EmployeeDTO employeeDTO) {
        // 创建一个新的 Employee 实体对象，用于存储从 DTO 转换后的数据
        Employee employee = new Employee();
        // 使用 BeanUtils 的 copyProperties 方法将 EmployeeDTO 对象中的属性值复制到 Employee 实体对象中
        BeanUtils.copyProperties(employeeDTO, employee);
        // 设置员工的状态为启用状态，StatusConstant.ENABLE 代表员工状态为启用
        employee.setStatus(StatusConstant.ENABLE);
        // 将默认密码进行 MD5 加密后，设置为员工的密码
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        // 设置员工记录的创建时间为当前时间
        employee.setCreateTime(LocalDateTime.now());
        // 设置员工记录的更新时间为当前时间
        employee.setUpdateTime(LocalDateTime.now());
        // 临时设置员工记录的创建用户 ID 为 10L，后续需要修改为当前登录用户的 ID
        employee.setCreateUser(BaseContext.getCurrentId());
        // 临时设置员工记录的更新用户 ID 为 10L，后续需要修改为当前登录用户的 ID
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.insert(employee);
    }

    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {

        // 使用 PageHelper 插件开启分页功能，根据传入的页码和每页记录数进行分页
        // employeePageQueryDTO.getPage() 获取当前请求的页码
        // employeePageQueryDTO.getPageSize() 获取当前请求每页显示的记录数
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        // 调用 employeeMapper 的 pageQuery 方法执行分页查询，返回一个 Page 对象
        // Page 对象包含了分页查询的结果信息，如总记录数、当前页数据等
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);
        // 从 Page 对象中获取查询结果的总记录数
        long total = page.getTotal();
        // 从 Page 对象中获取当前页的员工数据列表
        List<Employee> result = page.getResult();
        // 创建一个 PageResult 对象，将总记录数和当前页数据列表封装到该对象中并返回
        // PageResult 用于统一封装分页查询结果，方便前端处理
        return new PageResult(total, result);
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        // update employee set status = ? where id = ?
        Employee employee = Employee.builder()
                .status(status)
                .id(id)
                .build();
        employeeMapper.update(employee);
    }

    @Override
    public Employee getById(Long id) {
        Employee employee = employeeMapper.getById(id);
        employee.setPassword("******");
        return employee;
    }

    @Override
    public void update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(employee);
    }
}

