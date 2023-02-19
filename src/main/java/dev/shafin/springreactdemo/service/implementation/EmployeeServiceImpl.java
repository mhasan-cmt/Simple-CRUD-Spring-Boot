package dev.shafin.springreactdemo.service.implementation;

import com.fasterxml.jackson.databind.util.BeanUtil;
import dev.shafin.springreactdemo.entity.EmployeeEntity;
import dev.shafin.springreactdemo.model.Employee;
import dev.shafin.springreactdemo.repository.EmployeeRepository;
import dev.shafin.springreactdemo.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee(Employee employeeModel) {
        EmployeeEntity employee = new EmployeeEntity();
        BeanUtils.copyProperties(employeeModel, employee);
        employeeRepository.save(employee);
        return employeeModel;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        List<Employee> employees = employeeEntities.stream()
                .map(emp -> new Employee(
                        emp.getId(),
                        emp.getFirstName(),
                        emp.getLastName(),
                        emp.getEmail()))
                .collect(Collectors.toList());
        return employees;
    }

    @Override
    public boolean deleteEmployee(Long id) {
        Optional<EmployeeEntity> employee = employeeRepository.findById(id);
        if (employee.isPresent()){
            employeeRepository.delete(employee.get());
            return true;
        }
        return false;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        Optional<EmployeeEntity>  employeeEntity = employeeRepository.findById(id);
        if (employeeEntity.isPresent()){
            Employee employee = new Employee();
            BeanUtils.copyProperties(employeeEntity.get(),employee);
            return employee;
        }
        throw new IllegalStateException("No Employee found by Id: "+id);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        if (employeeEntity.isPresent()){
            employeeEntity.get().setEmail(employee.getEmail());
            employeeEntity.get().setFirstName(employee.getFirstName());
            employeeEntity.get().setLastName(employee.getLastName());
            employeeRepository.save(employeeEntity.get());
            return employee;
        }
        return null;
    }
}
