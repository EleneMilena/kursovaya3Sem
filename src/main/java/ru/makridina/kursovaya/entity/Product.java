package ru.makridina.kursovaya.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "magazin")
    private String magazin;

    @Column(name = "tovar")
    private String tovar;

    @Column(name = "price")
    private String price;
    @Column(name = "email")
    private String email;

}
