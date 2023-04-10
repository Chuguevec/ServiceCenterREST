package ru.example.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.dto.EmployeeDto;
import ru.example.service.EmployeeService;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Transactional(readOnly = true)
    @Override
    public EmployeeDto findOne(int id) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<EmployeeDto> findAll() {
        return null;
    }

    @Transactional
    @Override
    public Integer save(EmployeeDto employeeDto) {
        return null;
    }

    @Transactional
    @Override
    public void update(EmployeeDto employeeDto) {

    }

    @Transactional
    @Override
    public void delete(int id) {

    }
}
