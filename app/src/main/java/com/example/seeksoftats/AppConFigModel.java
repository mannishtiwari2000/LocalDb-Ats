package com.example.seeksoftats;

public class AppConFigModel {
    String Id, ClientCode, CompanyCode, PlantCode;

    public AppConFigModel(String clientCode, String plantCode, String companyCode) {
        ClientCode = clientCode;
        PlantCode = plantCode;
        CompanyCode = companyCode;
    }

    public AppConFigModel() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getClientCode() {
        return ClientCode;
    }

    public void setClientCode(String clientCode) {
        ClientCode = clientCode;
    }

    public String getCompanyCode() {
        return CompanyCode;
    }

    public void setCompanyCode(String companyCode) {
        CompanyCode = companyCode;
    }

    public String getPlantCode() {
        return PlantCode;
    }

    public void setPlantCode(String plantCode) {
        PlantCode = plantCode;
    }
}
