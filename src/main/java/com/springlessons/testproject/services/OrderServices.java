package com.springlessons.testproject.services;

import com.springlessons.testproject.dto.DailyRevenueDto;
import com.springlessons.testproject.dto.OrderRequestDTO;
import com.springlessons.testproject.dto.OrderResponseDTO;
import com.springlessons.testproject.exception.OrderException;
import com.springlessons.testproject.exception.OrdersNotFoundException;
import com.springlessons.testproject.mapper.OrderMapper;
import com.springlessons.testproject.model.Order;
import com.springlessons.testproject.repositories.OrderRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServices {

    private final OrderRepository orderRepository;
    private final OrderMapper mapper;

    public OrderResponseDTO update(@Valid OrderRequestDTO orderRequestDTO) {
        Order order = mapper.mapToEntity(orderRequestDTO);
        orderRepository.save(order);
        return mapper.mapToDTO(order);
    }

    public OrderResponseDTO getById(@Valid Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(
                        () -> new OrderException("Order not found", HttpStatus.NOT_FOUND));
        return mapper.mapToDTO(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> findAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        if (orderList.isEmpty()) {throw new OrderException("Data is empty", HttpStatus.NOT_FOUND);}
        return mapper.mapFormEnityListtoDTOList(orderList);
    }

    public Long createdOrder(@Valid OrderRequestDTO orderRequestDTO) {
        Order order = mapper.mapToEntity(orderRequestDTO);
        orderRepository.save(order);
        return order.getId();
    }

    public void cancelOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Map<String, ? extends Serializable>> findByBookingTimeBetween(LocalDateTime startDate, LocalDateTime endDate){
        List<Order> orders = orderRepository.findAllByCreatedDateBetween(startDate, endDate);
        Map<LocalDateTime, Double> revenueByDate = orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getCreatedDate().toLocalDate().atStartOfDay(),
                        Collectors.summingDouble(Order::getPrice)
                ));

        // Преобразуем в список карт для ответа
        return revenueByDate.entrySet().stream()
                .map(entry -> Map.of(
                        "date", entry.getKey().toLocalDate().toString(),
                        "revenue", entry.getValue()
                ))
                .collect(Collectors.toList());

        //List<?> revenueData = orderRepository.getDailyRevenue(startDate, endDate);

//        return revenueData.stream()
//                .map(item ->
//                        new OrderResponseDTO(item.getId(), item.getOrder(), item.getPrice())
//                ).toList();

//        return revenueData.stream()
//                .map(item -> Map.of(
//                        "date", item.getDate().toString(),
//                        "revenue", item.getRevenue()
//                ))
//                .collect(Collectors.toList());
    }

    public List<OrderResponseDTO> getByTime(String orderCreate) {
        try {
            LocalDateTime time = LocalDateTime.parse(orderCreate);
            List<Order> orders = orderRepository.getByCreatedDate(time);
            if (orders.isEmpty()) {
                throw new OrdersNotFoundException("No bookings found for the specified time");
            }
           return mapper.mapFormEnityListtoDTOList(orders);
        } catch (OrdersNotFoundException e) {
            throw new OrderException(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
