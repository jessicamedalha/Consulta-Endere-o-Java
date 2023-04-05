package br.com.projeto.consultaendereco.utilsTests;

import br.com.projeto.consultaendereco.controller.ConsultaCepController;
import br.com.projeto.consultaendereco.dto.RequestCpfDTO;
import br.com.projeto.consultaendereco.utils.ConstsEndereco;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstsEnderecoTests {

    @Test
    public void ifRegiaoNordeste(){
        ConstsEndereco constsEndereco = new ConstsEndereco();

        var result = constsEndereco.findCustFrete("PI");
        var expected = 15.98;

        assertEquals(result, expected);
    }

    @Test
    public void ifRegiaoSudeste(){
        ConstsEndereco constsEndereco = new ConstsEndereco();

        var result = constsEndereco.findCustFrete("SP");
        var expected = 7.85;

        assertEquals(result, expected);
    }

    @Test
    public void ifRegiaoCentroOeste(){
        ConstsEndereco constsEndereco = new ConstsEndereco();

        var result = constsEndereco.findCustFrete("MS");
        var expected = 12.50;

        assertEquals(result, expected);
    }

    @Test
    public void ifRegiaoSul(){
        ConstsEndereco constsEndereco = new ConstsEndereco();

        var result = constsEndereco.findCustFrete("RS");
        var expected = 17.30;

        assertEquals(result, expected);
    }

    @Test
    public void ifRegiaoNorte(){
        ConstsEndereco constsEndereco = new ConstsEndereco();

        var result = constsEndereco.findCustFrete("AM");
        var expected = 20.83;

        assertEquals(result, expected);
    }

    @Test
    public void ifNaoEncontraRegiao(){
        ConstsEndereco constsEndereco = new ConstsEndereco();

        var result = constsEndereco.findCustFrete("ZZ");
        var expected = 0.0;

        assertEquals(result, expected);
    }
}
