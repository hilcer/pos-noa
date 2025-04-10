package com.noa.pos.service.imp;

import com.noa.pos.dto.DomainDto;
import com.noa.pos.dto.OrderSalesDetailDto;
import com.noa.pos.dto.OrderSalesDto;
import com.noa.pos.imp.constant.DomainConstant;
import com.noa.pos.imp.constant.OrderState;
import com.noa.pos.model.dto.*;
import com.noa.pos.model.entity.OrderSalesDetailEntity;
import com.noa.pos.model.entity.OrderSalesEntity;
import com.noa.pos.model.repository.OrderSalesDetailRepository;
import com.noa.pos.model.repository.OrderSalesRepository;
import com.noa.pos.service.DomainService;
import com.noa.pos.service.OrderService;
import com.noa.pos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServicesImp implements OrderService {

    private final OrderSalesRepository orderSalesRepository;
    private final OrderSalesDetailRepository orderSalesDetailRepository;
    private final DomainService domainService;
    private final UserService userService;

    @Autowired
    public OrderServicesImp(final OrderSalesRepository orderSalesRepository, final OrderSalesDetailRepository orderSalesDetailRepository,
                            final DomainService domainService, final UserService userService) {
        this.orderSalesDetailRepository = orderSalesDetailRepository;
        this.orderSalesRepository = orderSalesRepository;
        this.domainService = domainService;
        this.userService = userService;
    }

    public OrderSalesDto saveOrders(OrderSalesDto orderSalesDto) throws Exception {

        // Obtener informacion del usuario
        var userName = userService.getUserByUser(orderSalesDto.getLastUser());
        orderSalesDto.setCompanyId(userName.getCompanyId());
        orderSalesDto.setSucursalId(userName.getSucursalId());

        Integer nroTicket = getNextTicket();
        orderSalesDto.setTicketNumber(nroTicket);
        var domGuardarPeticion = domainService.getDomainGroupAndName(DomainConstant.CONFIGURATION, DomainConstant.ENABLED_PETITION);
        if (domGuardarPeticion != null && domGuardarPeticion.getValue().equals("SI")) {
            orderSalesDto.setState(OrderState.PREPARING.name());
        } else {
            orderSalesDto.setState(OrderState.PROCESSED.name());
        }
        var entity = dtoToEntity(orderSalesDto);

        orderSalesRepository.save(entity);

        var listDetail = new ArrayList<OrderSalesDetailDto>();
        orderSalesDto.getDetails().stream().parallel().forEach(
                dto -> {
                    var detail = orderSalesDetailRepository.save(dtoDetailToEntity(dto, entity.getOrderSalesId(), entity.getLastUser()));
                    listDetail.add(entityDetailToDto(detail));
                }
        );

        orderSalesDto = entityToDto(entity);
        orderSalesDto.setDetails(listDetail);
        return orderSalesDto;
    }

    @Override
    public OrderSalesDto updatePendingToProcessed(OrderSalesDto orderSalesDto) throws Exception {
        var order = orderSalesRepository.findById(orderSalesDto.getOrderSalesId()).orElse(null);
        if (order == null) {
            return null;
        }
        order.setState(OrderState.PROCESSED.name());
        order.setLastTime(LocalDateTime.now());
        order.setLastUser(orderSalesDto.getLastUser());
        orderSalesRepository.save(order);
        return entityToDto(order);
    }

    private Integer getNextTicket() {
        Optional<DomainDto> date = Optional.ofNullable(domainService.getDomainGroupAndName(DomainConstant.CONFIGURATION, DomainConstant.SEQUENCE_DATE));
        Optional<DomainDto> value = Optional.ofNullable(domainService.getDomainGroupAndName(DomainConstant.CONFIGURATION, DomainConstant.SEQUENCE_TICKET));
        if (date.isPresent() && LocalDate.now().equals(LocalDate.parse(date.get().getValue()))) {
            // siguiente ticket
            if (value.isPresent()) {
                var nroTicket = Integer.parseInt(value.get().getValue()) + 1;
                value.get().setValue(String.valueOf(nroTicket));
                value.get().setLastTime(LocalDateTime.now());
                domainService.mergeDomain(value.get());
                return nroTicket;
            } else {
//                var domValue = new DomainDto();
//                value.get().setValue("1");
//                value.get().setLastTime(LocalDateTime.now());
//                domainService.mergeDomain(value.get());
                System.out.println("ERROR VALOR DE SECUENCIA NO CONFIGURADO");
                return 1;
            }
        } else {
            if (date.isPresent()) {
                date.get().setValue(LocalDate.now().toString());
                date.get().setLastTime(LocalDateTime.now());
                domainService.mergeDomain(date.get());
                value.get().setValue("1");
                value.get().setLastTime(LocalDateTime.now());
                domainService.mergeDomain(value.get());
                return 1;
            }
        }
        return 0;
    }

    public List<OrderSalesDto> findAll() {
        return orderSalesRepository.findAll().stream().parallel().map(this::entityToDto).toList();
    }

    public OrderSalesDto findById(Long orderId) {
        return entityToDto(orderSalesRepository.findById(orderId).get());
    }

    public List<ReportOrderSalesDetailDto> findOrderSalesDetailByOrderId(Long orderId) {
        return orderSalesDetailRepository.findByOrderSalesDetail(orderId).stream().parallel().toList();
    }

    public List<Dashboard7DayDto> findByOrderLas7Day(String user) {
        var companyId = userService.getUserByUser(user).getCompanyId();
        return orderSalesDetailRepository.findByOrderLas7Day(companyId);
    }

    public List<DashboardTopProductDto> findTopProductDay(String user) {
        var companyId = userService.getUserByUser(user).getCompanyId();
        return orderSalesDetailRepository.findTopProductDay(companyId);
    }

    private OrderSalesEntity dtoToEntity(OrderSalesDto salesDto) {
        var entity = new OrderSalesEntity();
        entity.setTicketNumber(salesDto.getTicketNumber());
        entity.setTotalAmount(salesDto.getTotalAmount());
        entity.setState(salesDto.getState());
        entity.setDateRegister(LocalDate.now());
        entity.setCompanyId(salesDto.getCompanyId());
        entity.setSucursalId(salesDto.getSucursalId());
        entity.setTypePayment(salesDto.getTypePayment());
        entity.setLastUser(salesDto.getLastUser());
        entity.setLastTime(LocalDateTime.now());
        return entity;
    }

    private OrderSalesDto entityToDto(OrderSalesEntity salesEntity) {
        var orderSales = new OrderSalesDto();
        orderSales.setOrderSalesId(salesEntity.getOrderSalesId());
        orderSales.setTicketNumber(salesEntity.getTicketNumber());
        orderSales.setTotalAmount(salesEntity.getTotalAmount());
        orderSales.setState(salesEntity.getState());
        orderSales.setDateRegister(salesEntity.getDateRegister().toString());
        orderSales.setCompanyId(salesEntity.getCompanyId());
        orderSales.setSucursalId(salesEntity.getSucursalId());
        orderSales.setLastUser(salesEntity.getLastUser());
        orderSales.setLastTime(salesEntity.getLastTime());
        return orderSales;
    }

    private OrderSalesDetailEntity dtoDetailToEntity(OrderSalesDetailDto dto, Long orderSalesId, String user) {
        OrderSalesDetailEntity entity = new OrderSalesDetailEntity();
        entity.setOrderSalesId(orderSalesId);
        entity.setProductId(dto.getProductId());
        entity.setPrice(dto.getPrice());
        entity.setQuantity(dto.getQuantity());
        entity.setLastUser(user);
        entity.setLastTime(LocalDateTime.now());
        return entity;
    }

    private OrderSalesDetailDto entityDetailToDto(OrderSalesDetailEntity entity) {
        OrderSalesDetailDto detailDto = new OrderSalesDetailDto();
        detailDto.setOrderSalesDetailId(entity.getOrderSalesDetailId());
        detailDto.setOrderSalesId(entity.getOrderSalesId());
        detailDto.setProductId(entity.getProductId());
        detailDto.setPrice(entity.getPrice());
        detailDto.setQuantity(entity.getQuantity());
        detailDto.setLastUser(entity.getLastUser());
        detailDto.setLastTime(entity.getLastTime());
        return detailDto;
    }

    @Override
    public Page<OrderSalesEntity> getAllOrderSalesPagination(Integer pageNo, Integer pageSize) {
        Sort sort = Sort.by(Sort.Order.desc("lastTime"));
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return orderSalesRepository.findAll(pageable);
    }

    @Override
    public Page<OrderSalesEntity> searchOrderSalesPagination(Integer pageNo, Integer pageSize, String dateFrom, String dateTo) {
        Sort sort = Sort.by(Sort.Order.desc("lastTime"));
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return orderSalesRepository.findByLastTimeBetweenPageable(LocalDate.parse(dateFrom, formatter).atStartOfDay(), LocalDate.parse(dateTo, formatter).atTime(23,59,59), pageable);
    }

    @Override
    public List<OrderSalesDto> searchOrderSales(String dateFrom, String dateTo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return orderSalesRepository.findByLastTimeBetween(LocalDate.parse(dateFrom, formatter).atStartOfDay(), LocalDate.parse(dateTo, formatter).atTime(23,59,59)).stream().parallel().map(this::entityToDto).toList();
    }

    @Override
    public List<OrderSalesDto> searchOrderSalesPending(String dateFrom, String dateTo, String user) {
        var userDto = userService.getUserByUser(user);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return orderSalesRepository.findByLastTimeBetweenPending(LocalDate.parse(dateFrom, formatter).atStartOfDay(),
                LocalDate.parse(dateTo, formatter).atTime(23,59,59), userDto.getCompanyId()).stream().parallel().map(this::entityToDto).toList();
    }

    @Override
    public List<ReportOrderSalesProdDto> searchOrderSalesByProd(String dateFrom, String dateTo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return orderSalesRepository.findByLastTimeBetweenProd(LocalDate.parse(dateFrom, formatter).atStartOfDay(), LocalDate.parse(dateTo, formatter).atTime(23,59,59));
    }
}
