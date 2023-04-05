package br.com.projeto.consultaendereco.controllerTests;

import br.com.projeto.consultaendereco.controller.ConsultaCepController;
import br.com.projeto.consultaendereco.dto.EnderecoDTO;
import br.com.projeto.consultaendereco.dto.EnderecoViaCepDTO;
import br.com.projeto.consultaendereco.dto.RequestCpfDTO;
import br.com.projeto.consultaendereco.utils.ConstsEndereco;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
@ExtendWith(SpringExtension.class)
public class ConsultaCepControllerTests {


    @InjectMocks
    private ConsultaCepController consultaCepController;

    @Mock
    private RestTemplate restTemplate;
    @Mock
    ConstsEndereco constsEndereco;

    @Test
    public void ifCepVazio(){
        ConsultaCepController consultaCepController = new ConsultaCepController();
        RequestCpfDTO requestCpfDTO = new RequestCpfDTO();
        requestCpfDTO.setCpf("");

        var result = consultaCepController.consultaViaCep(requestCpfDTO);
        var expected = ResponseEntity.badRequest().body("CEP não deve ser vazio");

        assertEquals(result, expected);
    }
    @Test
    public void ifCepMaior8(){
        ConsultaCepController consultaCepController = new ConsultaCepController();
        RequestCpfDTO requestCpfDTO = new RequestCpfDTO();
        requestCpfDTO.setCpf("000000000");

        var result = consultaCepController.consultaViaCep(requestCpfDTO);
        var expected = ResponseEntity.badRequest().body("CEP invalido");

        assertEquals(result, expected);
    }

    @Test
    public void ifCepMenor8(){
        ConsultaCepController consultaCepController = new ConsultaCepController();
        RequestCpfDTO requestCpfDTO = new RequestCpfDTO();
        requestCpfDTO.setCpf("000");

        var result = consultaCepController.consultaViaCep(requestCpfDTO);
        var expected = ResponseEntity.badRequest().body("CEP invalido");

        assertEquals(result, expected);
    }

    @Test
    public void ifCepNaoExiste(){
        RequestCpfDTO requestCpfDTO = new RequestCpfDTO();
        requestCpfDTO.setCpf("99999999");
        EnderecoViaCepDTO enderecoViaCepDTO = new EnderecoViaCepDTO();
        enderecoViaCepDTO.setLogradouro("zz");
        ResponseEntity<EnderecoViaCepDTO> mockEntity = Mockito.spy(new ResponseEntity(enderecoViaCepDTO, HttpStatus.OK));


        when(constsEndereco.findCustFrete(any())).thenReturn(0.0);
        doReturn(mockEntity).when(restTemplate).getForEntity(any(String.class),any(Class.class));

        var result = consultaCepController.consultaViaCep(requestCpfDTO);
        var expected = ResponseEntity.badRequest().body("CEP não encontrado.");

        assertEquals(result, expected);
    }

    @Test
    public void returnSucess(){
        RequestCpfDTO requestCpfDTO = new RequestCpfDTO();
        requestCpfDTO.setCpf("01001000");

        EnderecoViaCepDTO enderecoViaCepDTO = new EnderecoViaCepDTO();
        enderecoViaCepDTO.setLogradouro("ruaTeste");
        enderecoViaCepDTO.setLocalidade("cidadeTeste");
        enderecoViaCepDTO.setComplemento("complementoTeste");
        enderecoViaCepDTO.setBairro("bairroTeste");
        enderecoViaCepDTO.setUf("SP");
        enderecoViaCepDTO.setCep("cepTeste");

        ResponseEntity<EnderecoViaCepDTO> mockEntity = Mockito.spy(new ResponseEntity(enderecoViaCepDTO, HttpStatus.OK));

        EnderecoDTO response = EnderecoDTO.builder()
                .rua("ruaTeste")
                .frete(8.8)
                .cidade("cidadeTeste")
                .complemento("complementoTeste")
                .bairro("bairroTeste")
                .estado("SP")
                .cep("cepTeste")
                .build();

        when(constsEndereco.findCustFrete(any())).thenReturn(8.8);
        doReturn(mockEntity).when(restTemplate).getForEntity(any(String.class),any(Class.class));

        var result = consultaCepController.consultaViaCep(requestCpfDTO);
        var expected = ResponseEntity.ok(response);

        assertEquals(result.getStatusCode(), expected.getStatusCode());
    }
}
