package hospital_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ward")
public class Ward {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long wardId;
    @Column(name = "ward_name", nullable = false, unique = true, length = 100) private String wardName;
    @Column(name = "ward_type", nullable = false, length = 50) private String wardType;
    @Column(nullable = false, length = 30) private String status;
    public Long getWardId() { return wardId; } public void setWardId(Long value) { wardId = value; }
    public String getWardName() { return wardName; } public void setWardName(String value) { wardName = value; }
    public String getWardType() { return wardType; } public void setWardType(String value) { wardType = value; }
    public String getStatus() { return status; } public void setStatus(String value) { status = value; }
}