package com.noa.pos.service.imp;

import com.noa.pos.dto.DomainDto;
import com.noa.pos.dto.OrderSalesDetailDto;
import com.noa.pos.dto.OrderSalesDto;
import com.noa.pos.imp.constant.DomainConstant;
import com.noa.pos.imp.constant.OrderState;
import com.noa.pos.model.dto.ReportOrderSalesDto;
import com.noa.pos.model.entity.OrderSalesDetailEntity;
import com.noa.pos.model.entity.OrderSalesEntity;
import com.noa.pos.model.repository.OrderSalesDetailRepository;
import com.noa.pos.model.repository.OrderSalesRepository;
import com.noa.pos.service.DomainService;
import com.noa.pos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class OrderServicesImp implements OrderService {

    private final OrderSalesRepository orderSalesRepository;
    private final OrderSalesDetailRepository orderSalesDetailRepository;
    private final DomainService domainService;

    @Autowired
    public OrderServicesImp(final OrderSalesRepository orderSalesRepository, final OrderSalesDetailRepository orderSalesDetailRepository,
                            final DomainService domainService) {
        this.orderSalesDetailRepository = orderSalesDetailRepository;
        this.orderSalesRepository = orderSalesRepository;
        this.domainService = domainService;
    }

    @Override
    public OrderSalesDto saveOrders(OrderSalesDto orderSalesDto) throws Exception {

        Integer nroTicket = getNextTicket();
        orderSalesDto.setTicketNumber(nroTicket);
        orderSalesDto.setState(OrderState.PROCESSED.name());
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

    private OrderSalesEntity dtoToEntity(OrderSalesDto salesDto) {
        var entity = new OrderSalesEntity();
        entity.setTicketNumber(salesDto.getTicketNumber());
        entity.setTotalAmount(salesDto.getTotalAmount());
        entity.setState(salesDto.getState());
        entity.setDateRegister(LocalDate.now().toString());
        entity.setCompanyId(salesDto.getCompanyId());
        entity.setSucursalId(salesDto.getSucursalId());
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
        orderSales.setDateRegister(salesEntity.getDateRegister());
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

    public static void main(String[] args) {
        ReportOrderSalesDto dto = new ReportOrderSalesDto();
    }
}
