package com.sda.MyShoppingList.entities.command;

import com.sda.MyShoppingList.abstractclasses.AbstractController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/command")
public class OrderController extends AbstractController<Long, Order, OrderRepository, OrderService> {

    public OrderController(OrderService service) {
        super(service);
    }
}
