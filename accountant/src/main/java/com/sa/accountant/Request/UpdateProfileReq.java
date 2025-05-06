package com.sa.accountant.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateProfileReq {
    String username;
    String name;
    String department;
    List<String> qualifications;
    Double experience;
}
