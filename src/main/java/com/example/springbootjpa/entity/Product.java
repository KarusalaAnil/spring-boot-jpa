package com.example.springbootjpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCT")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class Product  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;
    //@Column(name = "DESC")
    private String description;
    private String productType;

//    @CreatedDate
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createAt;
//    @CreatedBy
//    private String createdBy;
//    @LastModifiedDate
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModifiedAt;
//    @LastModifiedBy
//    private String lastModifiedBy;
}