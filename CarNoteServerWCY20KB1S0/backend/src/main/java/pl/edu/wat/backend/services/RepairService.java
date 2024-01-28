package pl.edu.wat.backend.services;

import pl.edu.wat.backend.dtos.RepairRequest;
import pl.edu.wat.backend.dtos.RepairResponse;
import pl.edu.wat.backend.entities.RepairEntity;

import java.util.List;

public interface RepairService {
    List<RepairResponse> getAllRepairs();

    RepairEntity getRepairById(Long id);

    void addRepair(RepairRequest repairRequest);

    void deleteRepair(Long id);

    void updateRepair(Long id, RepairEntity repairEntity);
}
