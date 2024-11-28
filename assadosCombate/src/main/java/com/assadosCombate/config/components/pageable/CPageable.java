package com.assadosCombate.config.components.pageable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CPageable {

    private int page = 1;
    private int size = 1;
    private String search = null;
    List<CSort> sort = null;
    List<CFilter> filter = new ArrayList<>();

    public int getPage(){
        return page == 0 ? 1 : page - 1;
    }

    public void addSort(CSort cSort) {
        if(this.sort == null){
            this.sort = new ArrayList<>();
        }

        this.sort.add(cSort);
    }

    @JsonIgnore
    public Pageable getPageable(){

        List<Order> orders = new ArrayList<>();

        if(sort != null) {
            for (CSort sort : sort) {
                if (sort.getDirection().equals("desc")) {
                    orders.add(Order.desc(sort.getColumn()));
                } else {
                    orders.add(Order.asc(sort.getColumn()));
                }
            }
        }

        if(!orders.isEmpty()) {
            return PageRequest.of(getPage(), getSize(), Sort.by(orders));
        }

        return PageRequest.of(getPage(), getSize());
    }

}

