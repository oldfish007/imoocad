package com.imooc.ad.entity.unit_condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;

/**
 * @author
 * @description关键词限制
 * @date 2019/4/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="ad_unit_keyword")
public class AdUnitKeyWords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long id;

    @Basic
    @Column(name="unit_id",nullable = false)
    private Long unitId;
    @Basic
    @Column(name="keyword",nullable = false)
    private String keyword;

    public AdUnitKeyWords(Long unitId, String keyword) {
        this.unitId = unitId;
        this.keyword = keyword;
    }
}
