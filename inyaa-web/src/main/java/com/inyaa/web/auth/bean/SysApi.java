package com.inyaa.web.auth.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: yuxh
 * @date: 2021/3/12 23:16
 */
@Data
@Entity
@Table(name="sys_api")
public class SysApi {

    @Id
    @GeneratedValue
    private Integer id;

    private String url;

    private Boolean allowAccess;
}
