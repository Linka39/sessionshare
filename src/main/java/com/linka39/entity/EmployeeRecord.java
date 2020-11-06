package com.linka39.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 任职记录
 *
 * @author xinyao.zhang
 */
@Entity
@Table(name = "huijin_employee_record")
@Data
public class EmployeeRecord extends BaseEntity {


    /**
     * 任职编码
     */
    @Id
    @Column(name = "record_code", columnDefinition = "varchar", length = 50)
    private String recordCode;

    /**
     * 所属职员
     */
    @Column(name = "employee_id")
    private Long employeeId;

    /**
     * 任职开始时间
     */
    private Date startTime;

    /**
     * 任职结束时间
     */
    private Date endTime;
}
