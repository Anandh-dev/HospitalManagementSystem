package hospital_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "bed")
public class Bed {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long bedId;
    @Column(name = "bed_number", nullable = false, unique = true, length = 30) private String bedNumber;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "room_id", nullable = false) private Room room;
    @Column(nullable = false, length = 30) private String status;
    @Version private Long version;
    public Long getBedId() { return bedId; } public void setBedId(Long value) { bedId = value; }
    public String getBedNumber() { return bedNumber; } public void setBedNumber(String value) { bedNumber = value; }
    public Room getRoom() { return room; } public void setRoom(Room value) { room = value; }
    public String getStatus() { return status; } public void setStatus(String value) { status = value; }
    public Long getVersion() { return version; } public void setVersion(Long value) { version = value; }
}