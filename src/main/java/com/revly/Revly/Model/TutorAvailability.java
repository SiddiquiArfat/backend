package com.revly.Revly.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TutorAvailability extends Users{


    @Column(unique = true)
    Integer tutorId;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime ping;

    @JsonIgnore
    @OneToMany(mappedBy = "tutorAvailability",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Doubt> request = new ArrayList<>();
}
