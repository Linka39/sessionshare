package com.linka39.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


/**
 * 职员
 *
 * @author xinyao.zhang
 */
@Entity
@Table(name = "huijin_employee")
@Data
public class Employee extends BaseEntity {

    @Id
    @Column(name = "id",columnDefinition = "int8 default nextval('huijin_employee_sequence'::regclass)")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "huijin_employee_seq")
    @SequenceGenerator(name = "huijin_employee_seq" ,allocationSize = 1
            ,sequenceName = "huijin_employee_sequence")
    private Long id;

    /**
     * 职员编码
     */
    @Column(name = "code",columnDefinition = "varchar",length = 50)
    private String code;

    /**
     * 账号
     */
    @Column(name = "account",columnDefinition = "varchar",length = 50)
    private String account;

    /**
     * 任职记录
     */
    @Column(name = "record_list")
    @OneToMany(cascade = {CascadeType.REFRESH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE}
            ,fetch = FetchType.EAGER
            ,targetEntity = EmployeeRecord.class)
    @JoinColumn(name = "employee_id")
    private List<EmployeeRecord> recordList;
}
