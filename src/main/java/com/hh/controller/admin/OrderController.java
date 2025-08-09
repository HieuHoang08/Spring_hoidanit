package com.hh.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.hh.domain.Order;
import com.hh.service.OrderService;


@Controller
public class OrderController {
    OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/admin/order")
    public String getOrderPage(Model model) {
        List<Order> orders = this.orderService.fetchOrders();
        model.addAttribute("order", orders);
        return "admin/order/show";
    }

    @GetMapping("/admin/order/{id}")
    public String getViewOderPage(Model model, @PathVariable long id) {
        Optional<Order> order = this.orderService.fetchOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("id", id);
        model.addAttribute(("orderDetails"), order.get().getOrderDetail());

        return "admin/order/detail";
    }

    
    @GetMapping("/admin/order/update/{id}")
    public String getUpdatePage(Model model, @PathVariable long id ){
        Optional<Order> order  = this.orderService.fetchOrderById(id);
        model.addAttribute("singleOrder", order.get());
        return "admin/order/update";
    }

    @PostMapping("/admin/order/update")
    public String handleUpdateOrderPage(@ModelAttribute("singleOrder") Order currentOrder) {

        this.orderService.updateOrder(currentOrder);
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/delete/{id}")
    public String handleDeleteOrderPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newOrder", new Order());
        return "admin/order/delete";
    }

    @PostMapping("/admin/order/delete")
    public String postMethodName( @ModelAttribute("newOrder") Order currentOrder) {
        this.orderService.deleteOrderById(currentOrder.getId());
        
        return "redirect:/admin/order";
    }
    
}
