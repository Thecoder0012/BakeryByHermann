package com.bakerybyhermann.Service;

        import com.bakerybyhermann.Model.Address;
        import com.bakerybyhermann.Model.Baker;
        import com.bakerybyhermann.Repository.BakerRepo;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.List;

@Service
public class BakerService {

    @Autowired
    BakerRepo bakerRepo;

    public List<Baker> fetchAll(){
        return bakerRepo.fetchAll();
    }
    public void addNewBaker(Baker baker, Address address) {
        bakerRepo.addNewBaker(baker, address);
    }

    public Baker findById(int id){
        return bakerRepo.findById(id);
    }
    public void updateById(Baker baker){
        bakerRepo.updateById(baker);
    }

    public void deleteBaker(int bakerId){
        bakerRepo.deleteBaker(bakerId);
    }
}
