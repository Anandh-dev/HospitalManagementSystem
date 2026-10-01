package hospital_backend.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import hospital_backend.entity.SystemSetting;
import hospital_backend.repository.SystemSettingRepository;

@Component
public class SystemSettingInitializer implements CommandLineRunner {

    private final SystemSettingRepository systemSettingRepository;

    public SystemSettingInitializer(SystemSettingRepository systemSettingRepository) {
        this.systemSettingRepository = systemSettingRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        List<SystemSetting> defaultSettings = List.of(
                new SystemSetting(
                        "HOSPITAL_NAME",
                        "HMS Hospital",
                        "STRING",
                        "Hospital name"
                ),
                new SystemSetting(
                        "HOSPITAL_REGISTRATION_NUMBER",
                        "",
                        "STRING",
                        "Hospital registration number"
                ),
                new SystemSetting(
                        "HOSPITAL_PHONE",
                        "",
                        "STRING",
                        "Hospital contact phone number"
                ),
                new SystemSetting(
                        "HOSPITAL_EMAIL",
                        "",
                        "STRING",
                        "Hospital contact email address"
                ),
                new SystemSetting(
                        "HOSPITAL_ADDRESS",
                        "",
                        "STRING",
                        "Hospital address"
                ),
                new SystemSetting(
                        "HOSPITAL_WEBSITE",
                        "",
                        "STRING",
                        "Hospital website"
                ),
                new SystemSetting(
                        "APPOINTMENT_DURATION",
                        "30",
                        "INTEGER",
                        "Default appointment duration in minutes"
                ),
                new SystemSetting(
                        "OPD_CONSULTATION_DURATION",
                        "30",
                        "INTEGER",
                        "Default OPD consultation duration in minutes"
                ),
                new SystemSetting(
                        "REMINDER_MINUTES",
                        "60",
                        "INTEGER",
                        "Appointment reminder time before appointment in minutes"
                ),
                new SystemSetting(
                        "TIME_ZONE",
                        "Asia/Kolkata",
                        "STRING",
                        "Hospital timezone"
                ),
                new SystemSetting(
                        "DATE_FORMAT",
                        "dd-MM-yyyy",
                        "STRING",
                        "Default date format"
                ),
                new SystemSetting(
                        "DEFAULT_LANGUAGE",
                        "English",
                        "STRING",
                        "Default application language"
                ),
                new SystemSetting(
                        "NOTIFICATION_APPOINTMENT",
                        "true",
                        "BOOLEAN",
                        "Enable appointment notifications"
                ),
                new SystemSetting(
                        "NOTIFICATION_PATIENT",
                        "true",
                        "BOOLEAN",
                        "Enable patient notifications"
                ),
                new SystemSetting(
                        "NOTIFICATION_OPD",
                        "true",
                        "BOOLEAN",
                        "Enable OPD notifications"
                ),
                new SystemSetting(
                        "NOTIFICATION_IPD",
                        "true",
                        "BOOLEAN",
                        "Enable IPD notifications"
                )
        );

        for (SystemSetting defaultSetting : defaultSettings) {
            if (!systemSettingRepository.existsBySettingKey(
                    defaultSetting.getSettingKey())) {

                systemSettingRepository.save(defaultSetting);
            }
        }
    }
}