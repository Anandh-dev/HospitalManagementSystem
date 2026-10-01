package hospital_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "opd_sequence")
public class OPDSequence {
    @Id
    @Column(length = 50)
    private String id;

    @Column(name = "next_number", nullable = false)
    private Long nextNumber;

    public String getId() { return id; }
    public void setId(String value) { id = value; }
    public Long getNextNumber() { return nextNumber; }
    public void setNextNumber(Long value) { nextNumber = value; }
}