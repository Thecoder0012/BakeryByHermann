package com.bakerybyhermann.Service;


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

}
