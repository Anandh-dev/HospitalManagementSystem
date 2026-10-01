package hospital_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hospital_backend.entity.SystemSetting;

public interface SystemSettingRepository extends JpaRepository<SystemSetting, Long> {

    Optional<SystemSetting> findBySettingKey(String settingKey);

    boolean existsBySettingKey(String settingKey);
}