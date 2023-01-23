package com.ewave.xmlupload.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="records")
public class Record extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @Column(name="sg_region")
    private String region;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name="dt_record")
    private Date recordDate;

    @Column(name="ds_value")
    private Float value;

    @Column(name="ic_generation")
    private boolean isGeneration;

    @Column(name="ic_purchase")
    private boolean isPurchase;

    @ManyToOne
    private Agent agent;

}
