package org.example.dienluc.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import org.example.dienluc.entity.Account;
import org.example.dienluc.entity.Client;
import org.example.dienluc.entity.Employee;
import org.example.dienluc.entity.Role;
import org.example.dienluc.repository.AccountRepository;
import org.example.dienluc.repository.EmployeeRepository;
import org.example.dienluc.service.EmployeeService;
import org.example.dienluc.service.dto.ResponseCheck;
import org.example.dienluc.service.dto.employee.EmployeeChooseDto;
import org.example.dienluc.service.dto.employee.EmployeeDto;
import org.example.dienluc.service.dto.employee.EmployeeGetDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
    public List<EmployeeGetDto> getAllEmployee() {
        return employeeRepository.findAll().stream()
                .map(employee -> modelMapper.map(employee, EmployeeGetDto.class))
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
                .role(Role.builder().id(employeeDto.getRoleId()).build())
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

    @Override
    public Employee updateEmployee(Integer employeeId, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + employeeId));
        Employee employeeUpdate = modelMapper.map(employeeDto, Employee.class);
        employeeUpdate.setAccount(employee.getAccount());
        return employeeRepository.save(employeeUpdate);
    }

    @Override
    public ResponseCheck checkEmailOfEmployeeExists(String email) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
        if (optionalEmployee.isPresent()){
            return new ResponseCheck(true);
        }
        return new ResponseCheck(false);
    }

    @Override
    public ResponseCheck checkPhoneOfEmployeeExists(String phone) {
        Optional<Employee> optionalEmployee = employeeRepository.findByPhone(phone);
        if (optionalEmployee.isPresent()){
            return new ResponseCheck(true);
        }
        return new ResponseCheck(false);
    }

    @Override
    public ResponseCheck checkIdentityCardOfEmployeeExists(String identityCard) {
        Optional<Employee> optionalEmployee = employeeRepository.findByIdentityCard(identityCard);
        if (optionalEmployee.isPresent()){
            return new ResponseCheck(true);
        }
        return new ResponseCheck(false);
    }
}
