package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.models.Rule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RuleNameRepository extends JpaRepository<Rule, Integer> {

    Rule findById(int id);
}
