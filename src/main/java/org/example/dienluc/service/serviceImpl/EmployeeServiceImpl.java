package org.example.dienluc.service.serviceImpl;

import org.example.dienluc.entity.Account;
import org.example.dienluc.entity.Employee;
import org.example.dienluc.entity.Role;
import org.example.dienluc.repository.AccountRepository;
import org.example.dienluc.repository.EmployeeRepository;
import org.example.dienluc.service.EmployeeService;
import org.example.dienluc.service.dto.employee.EmployeeChooseDto;
import org.example.dienluc.service.dto.employee.EmployeeDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository1, ModelMapper modelMapper, AccountRepository accountRepository) {
        this.employeeRepository = employeeRepository1;
        this.modelMapper = modelMapper;
        this.accountRepository = accountRepository;
    }
    @Override
    public List<EmployeeDto> getAllEmployee() {
        return employeeRepository.findAll().stream()
                .map(employee -> modelMapper.map(employee, EmployeeDto.class))
                .collect(Collectors.toList());
    }

    /**
     * nếu thêm nhân viên thất bại thì rollback: không thêm tài khoản vào database
     * @param employeeDto
     * @return
     */
    @Transactional
    @Override
    public Employee addEmployee(EmployeeDto employeeDto) {
        Account account = accountRepository.save(Account.builder()
                .username(employeeDto.getUsername())
                .password(employeeDto.getPassword())
                .role(Role.builder().id(2).build())
                .build());
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        employee.setAccount(account);
        return employeeRepository.save(employee);
    }

    @Override
    public List<EmployeeChooseDto> getRecordableEmployees() {

        return employeeRepository.findByRoleId(2).stream()
                .map(employee -> {
                    EmployeeChooseDto employeeChooseDto = modelMapper.map(employee, EmployeeChooseDto.class);
                    employeeChooseDto.setIdAndFullName(employee.getIdAndFullName());
                    return employeeChooseDto;
                })
                .collect(Collectors.toList());
    }
}
