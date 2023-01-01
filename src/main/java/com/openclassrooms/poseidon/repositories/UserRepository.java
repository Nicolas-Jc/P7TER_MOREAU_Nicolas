package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface UserRepository extends JpaRepository<UserModel, Integer>, JpaSpecificationExecutor<UserModel> {
    boolean existsByUsername(String username);

    UserModel findByUsername(String username);

    UserModel findById(int id);


}
