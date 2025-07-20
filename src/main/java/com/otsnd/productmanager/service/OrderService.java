package com.otsnd.productmanager.service;

import com.otsnd.productmanager.controller.utils.DTOMapper;
import com.otsnd.productmanager.dto.requests.OrderItemRequestDTO;
import com.otsnd.productmanager.dto.requests.OrderRequestDTO;
import com.otsnd.productmanager.dto.response.OrderDTO;
import com.otsnd.productmanager.dto.response.ProductDTO;
import com.otsnd.productmanager.dto.response.UserDTO;
import com.otsnd.productmanager.entity.Order;
import com.otsnd.productmanager.entity.OrderItem;
import com.otsnd.productmanager.entity.Product;
import com.otsnd.productmanager.entity.User;
import com.otsnd.productmanager.exceptions.ExceededStockException;
import com.otsnd.productmanager.exceptions.ProductMissingException;
import com.otsnd.productmanager.exceptions.RepeatedProductInRequestException;
import com.otsnd.productmanager.exceptions.UserNotFoundException;
import com.otsnd.productmanager.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Transactional
    public OrderDTO placeOrder(OrderRequestDTO request) {
        UserDTO user = this.validateUser(request.getUserId());
        List<OrderItemRequestDTO> validatedProducts = this.validateProducts(request.getItems());
        Order entity = this.mapOrderEntity(user, validatedProducts);
        Order result = this.repository.save(entity);

        return DTOMapper.mapOrderDTO(result);
    }

    private Order mapOrderEntity(UserDTO userDTO, List<OrderItemRequestDTO> requestItems) {
        User user = this.userService.findEntityById(userDTO.getId())
                .orElseThrow(UserNotFoundException::new);

        List<Product> products = this.productsService.findAllEntities();
        if (products.isEmpty()) throw new ProductMissingException();

        Order order = new Order();

        List<OrderItem> orderItems = requestItems.stream()
                .map(orderItemRequestDTO -> mapOrderItem(orderItemRequestDTO, products, order))
                .collect(Collectors.toCollection(ArrayList::new));


        order.setUser(user);
        order.setItems(orderItems);
        order.setCreatedAt(LocalDateTime.now());

        return order;
    }

    private OrderItem mapOrderItem(OrderItemRequestDTO orderItemRequestDTO, List<Product> products, Order entity) {
        Product product = products.stream()
                .filter(prod -> Objects.equals(prod.getId(), orderItemRequestDTO.getProductId()))
                .findAny().orElseThrow(ProductMissingException::new);

        product.setStock(product.getStock() - orderItemRequestDTO.getQuantity());
        product = this.productsService.save(product);

        return OrderItem.builder()
                .id(null)
                .order(entity)
                .product(product)
                .quantity(orderItemRequestDTO.getQuantity())
                .build();
    }

    private UserDTO validateUser(long userId) {
        Optional<UserDTO> user = this.userService.findById(userId);
        if (user.isEmpty()) throw new UserNotFoundException();

        return user.get();
    }

    private List<OrderItemRequestDTO> validateProducts(List<OrderItemRequestDTO> items) {
        List<Long> itemIDs = items.stream()
                .map(OrderItemRequestDTO::getProductId)
                .toList();

        List<ProductDTO> products = productsService.findAll();

        boolean noneMatch = products.stream()
                .noneMatch(productDTO -> itemIDs.contains(productDTO.getId()));

        if (noneMatch) throw new ProductMissingException();

        boolean repeatedProducts = products.stream()
                .collect(Collectors.groupingBy(ProductDTO::getId, Collectors.counting()))
                .entrySet()
                .stream()
                .anyMatch(longLongEntry -> longLongEntry.getValue() > 1);

        if (repeatedProducts) throw new RepeatedProductInRequestException();

        List<OrderItemRequestDTO> mappedRequests = items.stream()
                .map(orderItemRequestDTO -> mapProductToOrder(orderItemRequestDTO, products))
                .collect(Collectors.toCollection(ArrayList::new));

        List<OrderItemRequestDTO> exceedStock = mappedRequests.stream()
                .filter(orderItemRequestDTO -> (orderItemRequestDTO.getQuantity() > orderItemRequestDTO.getProduct().getStock()))
                .collect(Collectors.toCollection(ArrayList::new));

        if (!exceedStock.isEmpty()) throw new ExceededStockException("Exceed stock", exceedStock);

        return items;
    }

    private static OrderItemRequestDTO mapProductToOrder(OrderItemRequestDTO orderItemRequestDTO, List<ProductDTO> products) {
        Optional<ProductDTO> product = products.stream()
                .filter(productDTO -> Objects.equals(productDTO.getId(), orderItemRequestDTO.getProductId()))
                .findAny();

        product.ifPresent(orderItemRequestDTO::setProduct);

        return orderItemRequestDTO;
    }
}


