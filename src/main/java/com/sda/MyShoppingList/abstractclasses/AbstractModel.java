package com.sda.MyShoppingList.abstractclasses;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractModel<ID_TYPE> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID_TYPE id;


    @Column(nullable = false, unique = true)
    private String name;
}
