package com.otsnd.productmanager.service;

import com.otsnd.productmanager.controller.utils.DTOMapper;
import com.otsnd.productmanager.dto.requests.OrderItemRequestDTO;
import com.otsnd.productmanager.dto.requests.OrderRequestDTO;
import com.otsnd.productmanager.dto.response.OrderDTO;
import com.otsnd.productmanager.dto.response.ProductDTO;
import com.otsnd.productmanager.dto.response.UserDTO;
import com.otsnd.productmanager.entity.Order;
import com.otsnd.productmanager.exceptions.ExceededStockException;
import com.otsnd.productmanager.exceptions.ProductMissingException;
import com.otsnd.productmanager.exceptions.RepeatedProductInRequestException;
import com.otsnd.productmanager.exceptions.UserNotFoundException;
import com.otsnd.productmanager.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final UserService userService;
    private final ProductsService productsService;

    public OrderService(OrderRepository repository,
                        UserService userService,
                        ProductsService productsService) {
        this.repository = repository;
        this.userService = userService;
        this.productsService = productsService;
    }

    public List<OrderDTO> findOrdersByUserId(long id) {
        return repository.findAllByUserId(id)
                .stream()
                .map(DTOMapper::mapOrderDTO)
                .toList();
    }

    public OrderDTO placeOrder(OrderRequestDTO request) {
        UserDTO user = this.validateUser(request.getUserId());
        List<OrderItemRequestDTO> validatedProducts = this.validateProducts(request.getItems());

        //Order order = this.repository.save(new Order());
        //return DTOMapper.mapOrderDTO(order);

        return new OrderDTO();
    }

    private UserDTO validateUser(long userId) {
        Optional<UserDTO> user = this.userService.findById(userId);
        if (user.isEmpty()) throw new UserNotFoundException("User not found");

        return user.get();
    }

    private List<OrderItemRequestDTO> validateProducts(List<OrderItemRequestDTO> items) {

        List<Optional<ProductDTO>> products = items.stream()
                .map(item -> this.productsService.findById(item.getProductId()))
                .toList();

        if (products.stream().anyMatch(Optional::isEmpty)) throw new ProductMissingException();

        boolean repeatedProducts = products.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.groupingBy(ProductDTO::getId, Collectors.counting()))
                .entrySet()
                .stream()
                .anyMatch(longLongEntry -> longLongEntry.getValue() > 1);

        if (repeatedProducts) throw new RepeatedProductInRequestException();

        List<OrderItemRequestDTO> mappedRequests = items.stream()
                .map(orderItemRequestDTO -> mapProductToOrder(orderItemRequestDTO, products))
                .toList();

        List<OrderItemRequestDTO> exceedStock = mappedRequests.stream()
                .filter(orderItemRequestDTO -> (orderItemRequestDTO.getQuantity() > orderItemRequestDTO.getProduct().getStock()))
                .toList();

        if (!exceedStock.isEmpty()) throw new ExceededStockException("Exceed stock", exceedStock);

        return items;
    }

    private static OrderItemRequestDTO mapProductToOrder(OrderItemRequestDTO orderItemRequestDTO, List<Optional<ProductDTO>> products) {
        Optional<ProductDTO> product = products.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(productDTO -> Objects.equals(productDTO.getId(), orderItemRequestDTO.getProductId()))
                .findAny();

        product.ifPresent(orderItemRequestDTO::setProduct);

        return orderItemRequestDTO;
    }
}


