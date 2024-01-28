package pl.edu.wat.backend.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.backend.dtos.CarRequest;
import pl.edu.wat.backend.dtos.CarResponse;
import pl.edu.wat.backend.entities.CarEntity;
import pl.edu.wat.backend.repositories.CarRepository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Transactional
@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, CarRepository carRepository1) {
        this.carRepository = carRepository;
    }

    @Override
    public void addCar(CarRequest carRequest) {
        CarEntity carEntity = new CarEntity();
//        carEntity.setCarID(carRequest.getCarID());
        carEntity.setModel(carRequest.getModel());
        carEntity.setMake(carRequest.getMake());
        carEntity.setColor(carRequest.getColor());
//        carEntity.setBestSpeed(carRequest.getBestSpeed());
//        carEntity.setFirstMileage(carRequest.getFirstMileage());


        carRepository.save(carEntity);
    }

    @Override
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<CarResponse> getAllCategories() {

//        return StreamSupport.stream(carRepository.findAll().spliterator(), false)
//                .map(entity -> new CarResponse(entity.getCarID(), entity.getModel(), entity.getMake(), entity.getColor(),
//                        entity.getBestSpeed(),
//                        entity.getFirstMileage(), entity.getCollisionEntities(), entity.getRepairEntities(), entity.getTankUpEntities()))
//                .collect(Collectors.toList());

        return StreamSupport.stream(carRepository.findAll().spliterator(), false)
                .map(entity -> new CarResponse(entity.getModel(), entity.getMake(), entity.getColor()))
                .collect(Collectors.toList());
    }

    @Override
    public CarEntity getCarById(Long id) {
        return carRepository.findById(id).get();
    }

    @Override
    public void updateCar(Long id, CarEntity carEntity)
    {
        CarEntity car = carRepository.findById(id).get();
//        car.setCarID(carEntity.getCarID());
        car.setModel(carEntity.getModel());
        car.setMake(carEntity.getMake());
        car.setColor(carEntity.getColor());
//        car.setBestSpeed(carEntity.getBestSpeed());
//        car.setFirstMileage(carEntity.getFirstMileage());
        carRepository.save(car);
    }




}
