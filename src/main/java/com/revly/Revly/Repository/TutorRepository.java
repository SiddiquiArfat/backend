package com.revly.Revly.Repository;

import com.revly.Revly.Model.TutorAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<TutorAvailability, Integer> {
    Optional<TutorAvailability> findByUsernameAndUserType(String username, String userType);
    @Query("select t from TutorAvailability t where t.ping >= :ldt and t.userType = 'TUTOR' ")
    List<TutorAvailability> findAllActiveTutors(LocalDateTime ldt);
}
