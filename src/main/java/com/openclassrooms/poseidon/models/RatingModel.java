package com.openclassrooms.poseidon.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
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

    @Positive(message = "Order Number must be greater than zero")
    @NotNull(message = "Order Number is mandatory")
    @Column(name = "order_number")
    private Integer orderNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMoodysRating() {
        return moodysRating;
    }

    public void setMoodysRating(String moodysRating) {
        this.moodysRating = moodysRating;
    }

    public String getSandPRating() {
        return sandPRating;
    }

    public void setSandPRating(String sandPRating) {
        this.sandPRating = sandPRating;
    }

    public String getFitchRating() {
        return fitchRating;
    }

    public void setFitchRating(String fitchRating) {
        this.fitchRating = fitchRating;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}
