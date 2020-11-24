package com.alexey.demo.service.impl;

import com.alexey.demo.config.AppConfig;
import com.alexey.demo.entity.Employee;
import com.alexey.demo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final AppConfig appConfig;
    private final RestTemplate restTemplate;

    @Override
    public void saveOrUpdate(Employee employee) {
        if (employee.getId() == null) {
            restTemplate.postForEntity(appConfig.getEmployeeUrl(), employee,
                    Employee.class);
        } else {
            restTemplate.put(appConfig.getEmployeeUrl(), employee);
        }
    }

    @Override
    public Long deleteEmployee(Long id) {
        Long departId = getEmployee(id).getDepartment().getId();
        restTemplate.delete(appConfig.getEmployeeUrl() + "/" + id);
        return departId;
    }

    @Override
    public List<Employee> getAllEmployeesByDepartment(Long id) {
        List<Employee> employees = getAllEmployees().stream()
                .filter(em -> em.getDepartment().getId() == id)
                .collect(Collectors.toList());
        return employees;
    }

    @Override
    public Employee getEmployee(Long id) {
        Employee employee =
                restTemplate.getForObject(appConfig.getEmployeeUrl() + "/" + id,
                        Employee.class);
        return employee;
    }

    @Override
    public List<Employee> getAllEmployeesWithDateBirthBetween(LocalDate startDate, LocalDate endDate) {
        List<Employee> employees = getAllEmployees().stream()
                .filter(em -> em.getBirthday().isAfter(startDate.minusDays(1))
                        && em.getBirthday().isBefore(endDate.plusDays(1)))
                .collect(Collectors.toList());
        return employees;
    }

    @Override
    public List<Employee> getAllEmployees() {

        ResponseEntity<List<Employee>> responseEntity =
                restTemplate.exchange(appConfig.getEmployeeUrl(),
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Employee>>() {
                        });
        List<Employee> employees = responseEntity.getBody();
        return employees;
    }
}
