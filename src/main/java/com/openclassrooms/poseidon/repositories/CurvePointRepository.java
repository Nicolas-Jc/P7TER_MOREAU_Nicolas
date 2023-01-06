package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.models.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

    CurvePoint findById(int id);


}
