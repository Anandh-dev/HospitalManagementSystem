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

@Entity
@Table(name = "room")
public class Room {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long roomId;
    @Column(name = "room_number", nullable = false, length = 30) private String roomNumber;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "ward_id", nullable = false) private Ward ward;
    @Column(nullable = false, length = 30) private String status;
    public Long getRoomId() { return roomId; } public void setRoomId(Long value) { roomId = value; }
    public String getRoomNumber() { return roomNumber; } public void setRoomNumber(String value) { roomNumber = value; }
    public Ward getWard() { return ward; } public void setWard(Ward value) { ward = value; }
    public String getStatus() { return status; } public void setStatus(String value) { status = value; }
}