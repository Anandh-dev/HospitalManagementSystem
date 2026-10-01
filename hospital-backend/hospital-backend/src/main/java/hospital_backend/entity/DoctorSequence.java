package hospital_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "doctor_sequence")
public class DoctorSequence {
    @Id private Long id;
    @Column(name = "next_number", nullable = false) private Long nextNumber;
    public Long getId() { return id; } public void setId(Long value) { id = value; }
    public Long getNextNumber() { return nextNumber; } public void setNextNumber(Long value) { nextNumber = value; }
}
