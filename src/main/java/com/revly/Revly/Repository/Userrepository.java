package com.revly.Revly.Repository;

import com.revly.Revly.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Userrepository extends JpaRepository<Users, Integer> {

    public Optional<Users> findByUsername(String email);

}
