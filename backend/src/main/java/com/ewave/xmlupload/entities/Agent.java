package com.ewave.xmlupload.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="agents")
public class Agent extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @Column(name="cd_agent")
    private int code;
}
