package com.openclassrooms.poseidon.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rating")
public class RatingModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Moodys Rating is mandatory")
    @Size(max = 125)
    @Column(name = "moodys_rating")
    private String moodysRating;

    @NotBlank(message = "Sand PRating is mandatory")
    @Size(max = 125)
    @Column(name = "sandprating")
    private String sandPRating;

    @NotBlank(message = "Fitch Rating is mandatory")
    @Size(max = 125)
    @Column(name = "fitch_rating")
    private String fitchRating;

    @NotNull(message = "Order Number is mandatory")
    @PositiveOrZero(message = "Order Number should be a decimal number and greater than zero")
    @Column(name = "order_number")
    private Integer orderNumber;

}
