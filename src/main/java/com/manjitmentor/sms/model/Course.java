package com.manjitmentor.sms.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "TBL_COURSES")
public class Course extends Auditable<ApplicationUser> {

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

    @Column(name = "isActive",
            columnDefinition = "CHAR default 'Y'",
            length = 1,
            nullable = false)
    private Character isActive;

    public Course(Long id){
        super(id);
    }

}
