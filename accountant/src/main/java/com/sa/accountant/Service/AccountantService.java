package com.se.user.Accountant.Service;

import com.se.user.Accountant.Entity.Accountant;
import com.se.user.Accountant.Entity.Qualification;
import com.se.user.Accountant.Interface.IAccountant;
import com.se.user.Accountant.Repository.AccountantRepo;
import com.se.user.Accountant.Request.UpdateProfileReq;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountantService implements IAccountant {

    AccountantRepo accountantRepo;

    @Transactional
    public String updateProfile(String username, UpdateProfileReq request){
        Accountant accountant = accountantRepo.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("Accountant not found"));

        if(request.getName() != null) accountant.setName(request.getName());
        if(request.getExperience() != null) accountant.setExperience(request.getExperience());
        if(request.getDepartment() != null) accountant.setDepartment(request.getDepartment());
        if(request.getQualifications() != null){
            List<Qualification> newListQualification = request.getQualifications().stream()
                    .map(desc ->Qualification.builder()
                            .accountant(accountant)
                            .description(desc)
                            .build()
                        )
                    .toList();

            accountant.getQualifications().clear();
            accountant.getQualifications().addAll(newListQualification);
        }
        try{
            accountantRepo.save(accountant);
        }catch (Exception e){
            System.err.println("Error in updating profile of " + username);
            throw new RuntimeException("Error in updating");
        }
        return "Profile has been updated successfully";
    }
}
