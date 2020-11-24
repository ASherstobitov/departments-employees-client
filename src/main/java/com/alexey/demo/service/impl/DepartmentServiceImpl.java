package com.alexey.demo.service.impl;

import com.alexey.demo.config.AppConfig;
import com.alexey.demo.entity.Department;
import com.alexey.demo.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final RestTemplate restTemplate;
    private final AppConfig appConfig;

    @Override
    public void saveOrUpdate(Department department) {
        if (department.getId() == null) {
            restTemplate.postForEntity(appConfig.getDepartmentUrl(), department,
                    Department.class);
        } else {
            restTemplate.put(appConfig.getDepartmentUrl(), department);
        }
    }

    @Override
    public List<Department> getDepartments() {

        ResponseEntity<List<Department>> responseEntity =
                restTemplate.exchange(appConfig.getDepartmentUrl(),
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Department>>() {
                        });
        List<Department> departments = responseEntity.getBody();
        return departments;
    }

    @Override
    public Department getDepartment(Long id) {
        Department department =
                restTemplate.getForObject(appConfig.getDepartmentUrl() + "/" + id,
                        Department.class);
        return department;
    }

    @Override
    public void delete(Long id) {
        restTemplate.delete(appConfig.getDepartmentUrl() + "/" + id);
    }
}
