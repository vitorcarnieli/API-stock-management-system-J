package br.gov.es.conceicaodocastelo.stock.dto;

import java.util.List;


public class OrderCreateRequest {
    private Long institutionId;
    private String nameOrder;
    private String observationsOrder;
    private List<Long> itemsId;
    private List<Integer> amounts;
    public Long getInstitutionId() {
        return institutionId;
    }
    public void setInstitutionId(Long institutionId) {
        this.institutionId = institutionId;
    }
    public List<Long> getItemsId() {
        return itemsId;
    }
    public void setItemsId(List<Long> itemsId) {
        this.itemsId = itemsId;
    }
    public List<Integer> getAmounts() {
        return amounts;
    }
    public void setAmounts(List<Integer> amounts) {
        this.amounts = amounts;
    }
    public String getNameOorder() {
        return nameOrder;
    }
    public void setNameOorder(String nameOorder) {
        this.nameOrder = nameOorder;
    }
    public String getObservationsOrder() {
        return observationsOrder;
    }
    public void setObservationsOrder(String observationsOrder) {
        this.observationsOrder = observationsOrder;
    }


    
}
