package com.springlessons.testproject.mapper;

import com.springlessons.testproject.dto.OrderRequestDTO;
import com.springlessons.testproject.dto.OrderResponseDTO;
import com.springlessons.testproject.model.Order;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderMapper {
    public OrderResponseDTO mapToDTO(Order order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getOrder(),
                order.getPrice()
        );
    }

    public Order mapToEntity(@Valid OrderRequestDTO orderRequestDTO) {
        return Order.builder()
                .price(orderRequestDTO.price())
                .order(orderRequestDTO.name())
                .build();
    }

    public List<OrderResponseDTO> mapFormEnityListtoDTOList(List<Order> orderList) {
        List<OrderResponseDTO> dtoList = new ArrayList<>();
        for (Order order : orderList) {
            OrderResponseDTO responseDTO = new OrderResponseDTO(
                    order.getId(), order.getOrder(), order.getPrice()
            );
            dtoList.add(responseDTO);
        }
        return dtoList;
    }
}
