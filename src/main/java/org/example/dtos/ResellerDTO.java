package org.example.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entities.Reseller;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResellerDTO {
    private Long id;
    private String name;
    private String address;
    private String phone;

    public ResellerDTO(Reseller reseller) {
        this.id = reseller.getId();
        this.name = reseller.getName();
        this.address = reseller.getAddress();
        this.phone = reseller.getPhone();
    }

    @JsonIgnore
    public Reseller getAsEntity() {
        return Reseller.builder()
                .id(id)
                .name(name)
                .address(address)
                .phone(phone)
                .build();
    }
}
