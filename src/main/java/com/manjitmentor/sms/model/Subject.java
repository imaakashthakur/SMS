package com.manjitmentor.sms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_SUBJECTS")
public class Subject extends Auditable<ApplicationUser> {

    @Column(name = "NAME",
            length = 50,
            nullable = false)
    private String name;

    @Column(name = "DESCRIPTION",
            length = 500)
    private String description;

    @Column(name = "CODE",
            length = 20)
    private String code;

}
