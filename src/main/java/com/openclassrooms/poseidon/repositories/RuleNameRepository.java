package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.models.RuleModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RuleNameRepository extends JpaRepository<RuleModel, Integer> {

    RuleModel findById(int id);

}
