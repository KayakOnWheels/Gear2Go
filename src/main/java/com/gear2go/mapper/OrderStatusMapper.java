package com.gear2go.mapper;

import com.gear2go.domain.dto.request.order.UpdateOrderStatusRequest;
import com.gear2go.entity.enums.OrderStatus;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusMapper {

    public OrderStatus toOrderStatus(UpdateOrderStatusRequest updateOrderStatusRequest) throws Exception {

        switch (updateOrderStatusRequest.orderStatus()) {
                case "unpaid" -> { return OrderStatus.UNPAID;}
                case "paid" -> { return OrderStatus.PAID;}
                case "shipped" -> { return OrderStatus.SHIPPED;}
                case "delivered" -> { return OrderStatus.DELIVERED;
                }
            default -> throw new Exception();
        }
    }
}
