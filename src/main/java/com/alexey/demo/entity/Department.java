package com.alexey.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Department {

    private Long id;

    @NonNull
    private String departName;

    private String averageSalary;

    private int amountEmployees;

    private List<Employee> employees;

}





