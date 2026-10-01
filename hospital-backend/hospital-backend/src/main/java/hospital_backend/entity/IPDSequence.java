package hospital_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ipd_sequence")
public class IPDSequence {
    @Id @Column(length = 30) private Long id;
    @Column(name = "next_number", nullable = false) private Long nextNumber;
    public Long getId() { return id; } public void setId(Long value) { id = value; }
    public Long getNextNumber() { return nextNumber; } public void setNextNumber(Long value) { nextNumber = value; }
}