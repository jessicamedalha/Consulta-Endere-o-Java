package br.com.projeto.consultaendereco.utils;

import java.util.Arrays;

public class ConstsEndereco {
    public String[] REGIAO_NORTE = {
        "AM", "RR", "AP", "PA", "TO", "RO", "AC"
    };

    public String[] REGIAO_NORDESTE = {
            "PI", "CE", "RN", "PE", "PB", "SE", "AL", "BA"
    };

    public String[] REGIAO_CENTRO_OESTE = {
            "MT", "MS", "GO"
    };

    public String[] REGIAO_SULDESTE= {
            "SP", "RJ", "ES", "MG"
    };

    public String[] REGIAO_SUL= {
            "PR", "RS", "SC"
    };

    public double SUDESTE = 7.85;
    public double CENTRO_OESTE = 12.50;
    public double NORDESTE = 15.98;
    public double SUL = 17.30;
    public double NORTE = 20.83;

    public double findCustFrete(String uf){
        double valueFrete =0.0;

        if(Arrays.asList(REGIAO_NORDESTE).contains(uf)){
            return NORDESTE;

        } else if(Arrays.asList(REGIAO_SULDESTE).contains(uf)){
            return SUDESTE;

        }else if(Arrays.asList(REGIAO_CENTRO_OESTE).contains(uf)){
            return CENTRO_OESTE;

        }else if(Arrays.asList(REGIAO_SUL).contains(uf)){
            return SUL;
        }else if(Arrays.asList(REGIAO_NORTE).contains(uf)){
            return NORTE;
        };
        return valueFrete;
    };


}

