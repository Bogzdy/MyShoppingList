package com.sda.MyShoppingList.entities.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sda.MyShoppingList.abstractclasses.AbstractModel;
import com.sda.MyShoppingList.entities.category.CategoryModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class ProductModel extends AbstractModel<Long> {
    @Column
    private Float price;

    @Column
    private String details;

    @Column
    private Float quantity;

    @Enumerated(EnumType.STRING)
    @Column
    private MeasurementUnits measurementUnits;

    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    @JsonIgnore
    private CategoryModel category;
}
