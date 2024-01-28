package pl.edu.wat.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.backend.dtos.RepairRequest;
import pl.edu.wat.backend.dtos.RepairResponse;
import pl.edu.wat.backend.entities.RepairEntity;
import pl.edu.wat.backend.repositories.RepairRepository;



import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Transactional
@Service
public class RepairServiceImpl implements RepairService {

    private final RepairRepository repairRepository;

    @Autowired
    public RepairServiceImpl(RepairRepository repairRepository, RepairRepository repairRepository1) {
        this.repairRepository = repairRepository;
    }


    @Override
    public List<RepairResponse> getAllRepairs()
    {
        return StreamSupport.stream(repairRepository.findAll().spliterator(), false)
                .map(entity -> new RepairResponse(entity.getRepairDate(), entity.getMileage(), entity.getParts(),
                        entity.getCost()))
                .collect(Collectors.toList());

    }

    @Override
    public void addRepair(RepairRequest repairRequest)
    {
        RepairEntity repairEntity = new RepairEntity();
        //repairEntity.setRepairID(repairRequest.getRepairID());
        repairEntity.setRepairDate(repairRequest.getRepairDateString());
        repairEntity.setMileage(repairRequest.getMileage());
        repairEntity.setParts(repairRequest.getParts());
        repairEntity.setCost(repairRequest.getCost());
        //repairEntity.setCar(repairRequest.getCar());
        repairRepository.save(repairEntity);
    }

    @Override
    public void deleteRepair(Long id) {
        repairRepository.deleteById(id);
    }

    @Override
    public RepairEntity getRepairById(Long id) {
        return repairRepository.findById(id).get();
    }

    @Override
    public void updateRepair(Long id, RepairEntity repairEntity) {
        RepairEntity repair = repairRepository.findById(id).get();
        repair.setRepairID(repairEntity.getRepairID());
        repair.setRepairDate(repairEntity.getRepairDate());
        repair.setMileage(repairEntity.getMileage());
        repair.setParts(repairEntity.getParts());
        repair.setCost(repairEntity.getCost());
        repairRepository.save(repair);
    }


}
