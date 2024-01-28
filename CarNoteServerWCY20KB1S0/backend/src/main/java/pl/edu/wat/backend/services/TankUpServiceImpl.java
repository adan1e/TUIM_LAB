package pl.edu.wat.backend.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.backend.dtos.TankUpRequest;
import pl.edu.wat.backend.dtos.TankUpResponse;
import pl.edu.wat.backend.entities.TankUpEntity;
import pl.edu.wat.backend.repositories.TankUpRepository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Transactional
@Service
public class TankUpServiceImpl implements TankUpService {

    private final TankUpRepository tankUpRepository;

    @Autowired
    public TankUpServiceImpl(TankUpRepository tankUpRepository) {
        this.tankUpRepository = tankUpRepository;
    }

    @Override
    public void addTankUp(TankUpRequest tankUpRequest) {
        TankUpEntity tankUpEntity = new TankUpEntity();
        tankUpEntity.setTankUpDate(tankUpRequest.getTankUpDateString());
        tankUpEntity.setMileage(tankUpRequest.getMileage());
        tankUpEntity.setLiters(tankUpRequest.getLiters());
        tankUpEntity.setCost(tankUpRequest.getCost());
        tankUpRepository.save(tankUpEntity);
    }

    @Override
    public void deleteTankUp(Long id) {
        tankUpRepository.deleteById(id);
    }

    @Override
    public List<TankUpResponse> getAllTankUps() {

        return StreamSupport.stream(tankUpRepository.findAll().spliterator(), false)
                .map(entity -> new TankUpResponse(entity.getTankUpDate(), entity.getMileage(), entity.getLiters(),
                entity.getCost()))
                .collect(Collectors.toList());
    }

    @Override
    public TankUpEntity getTankUpById(Long id) {
        return tankUpRepository.findById(id).get();
    }

    @Override
    public void updateTankUp(Long id, TankUpEntity TankUpEntity)
    {
        TankUpEntity tankUp = tankUpRepository.findById(id).get();
        tankUp.setTankUpID(TankUpEntity.getTankUpID());
        tankUp.setTankUpDate(TankUpEntity.getTankUpDate());
        tankUp.setMileage(TankUpEntity.getMileage());
        tankUp.setLiters(TankUpEntity.getLiters());
        tankUp.setCost(TankUpEntity.getCost());
        tankUp.setFullTankup(TankUpEntity.isFullTankup());
        tankUpRepository.save(tankUp);
    }

}
