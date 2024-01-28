package pl.edu.wat.backend.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.backend.dtos.RepairRequest;
import pl.edu.wat.backend.dtos.RepairResponse;
import pl.edu.wat.backend.entities.RepairEntity;
import pl.edu.wat.backend.services.RepairService;

import java.util.List;

@RestController
public class RepairController {

    private final RepairService repairService;

    @Autowired
    public RepairController(RepairService repairService) {
        this.repairService = repairService;
    }

    @GetMapping("/api/Repair")
    public ResponseEntity<List<RepairResponse>> getRepairs() {
        List<RepairResponse> Repairs = repairService.getAllRepairs();

        return new ResponseEntity<>(Repairs, HttpStatus.OK);
    }


//    @PostMapping("/api/Repair")
//    public ResponseEntity addRepair(@RequestBody RepairRequest RepairRequest) {
//        repairService.addRepair(RepairRequest);
//
//        return new ResponseEntity(HttpStatus.CREATED);
//    }
@RequestMapping(value="/api/Repair", method = RequestMethod.POST)
@ResponseStatus(value=HttpStatus.OK)
public ResponseEntity addRepair(@RequestBody RepairRequest repairRequest){
    repairService.addRepair(repairRequest);
    return new ResponseEntity(HttpStatus.CREATED);
}

    @GetMapping({"/api/Repair/{ID}"})
    public ResponseEntity<RepairEntity> getRepair(@PathVariable Long RepairID) {
        return new ResponseEntity<>(repairService.getRepairById(RepairID), HttpStatus.OK);
    }


    @PutMapping({"/api/Repair/{ID}"})
    public ResponseEntity<RepairEntity> updateRepair(@PathVariable("ID") Long RepairID, @RequestBody RepairEntity Repair) {
        repairService.updateRepair(RepairID, Repair);
        return new ResponseEntity<>(repairService.getRepairById(RepairID), HttpStatus.OK);
    }

    @DeleteMapping({"/api/Repair/{ID}"})
    public ResponseEntity<RepairEntity> deleteRepair(@PathVariable("ID") Long RepairID) {
        repairService.deleteRepair(RepairID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
