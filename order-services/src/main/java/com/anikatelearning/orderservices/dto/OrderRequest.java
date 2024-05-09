package com.anikatelearning.orderservices.dto;

import com.anikatelearning.orderservices.model.OrderContainer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private List<OrderContainerDto> orderContainerDtoList;
}
