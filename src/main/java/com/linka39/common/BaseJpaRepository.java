package com.linka39.common;

import com.linka39.entity.Employee;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseJpaRepository<T,ID> extends JpaRepository<T,Integer> {
}
