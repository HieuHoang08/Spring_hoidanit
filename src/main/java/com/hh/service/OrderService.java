package com.hh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hh.domain.Order;
import com.hh.domain.OrderDetail;
import com.hh.repository.OrderDetailRepository;
import com.hh.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

   public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;

    }

    public List<Order> fetchOrders() {
        return this.orderRepository.findAll();
    }

    public Optional<Order> fetchOrderById(long id){
        return this.orderRepository.findById(id);
    }

    public void deleteOrderById(long id) {
        Optional<Order> order = this.orderRepository.findById(id);

        if(order.isPresent()){
            List<OrderDetail> orderDetails = this.orderDetailRepository.findByOrder(order.get());

            for (OrderDetail orderDetail : orderDetails) {
                this.orderDetailRepository.delete(orderDetail);
            }

            this.orderRepository.delete(order.get());
        }

    }
    
    public void updateOrder(Order order) {
        Optional<Order> orderOptional = this.fetchOrderById(order.getId());
        if (orderOptional.isPresent()) {
            Order existingOrder = orderOptional.get();
            existingOrder.setStatus(order.getStatus());
            // Update other fields as necessary
            this.orderRepository.save(existingOrder);
        }
    }

    public List<Order> fetchOrdersByUser(Long userId) {
    return this.orderRepository.findByUserId(userId);
}

}
