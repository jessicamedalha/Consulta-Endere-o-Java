package br.com.projeto.consultaendereco.controller;
import br.com.projeto.consultaendereco.dto.EnderecoDTO;
import br.com.projeto.consultaendereco.dto.EnderecoViaCepDTO;
import br.com.projeto.consultaendereco.dto.RequestCpfDTO;
import br.com.projeto.consultaendereco.utils.ConstsEndereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/consulta-endereco")
public class ConsultaCepController {

    @Autowired
    RestTemplate restTemplate;


    @PostMapping((""))
    public ResponseEntity<?> consultaViaCep (@RequestBody RequestCpfDTO cpfDTO){

        if (cpfDTO.getCpf().isEmpty() || cpfDTO.getCpf().isBlank()){
            return ResponseEntity.badRequest().body("CEP não deve ser vazio");
        }
        if (cpfDTO.getCpf().length() > 8) {
            return ResponseEntity.badRequest().body("CEP invalido");
        }
        if (cpfDTO.getCpf().length() < 8){
            return ResponseEntity.badRequest().body("CEP invalido");
        }


        ResponseEntity<EnderecoViaCepDTO> responseViaCep =
                restTemplate.getForEntity(
                        String.format("https://viacep.com.br/ws/%s/json", cpfDTO.getCpf()),
                        EnderecoViaCepDTO.class);
        ConstsEndereco constsEndereco = new ConstsEndereco();


        double valueFrete = constsEndereco.findCustFrete(responseViaCep.getBody().getUf());

        if (valueFrete == 0.0) {
            return ResponseEntity.badRequest().body("CEP não encontrado.");
        }
        EnderecoDTO response = EnderecoDTO.builder()
                .rua(responseViaCep.getBody().getLogradouro())
                .frete(valueFrete)
                .cidade(responseViaCep.getBody().getLocalidade())
                .complemento(responseViaCep.getBody().getComplemento())
                .bairro(responseViaCep.getBody().getBairro())
                .estado(responseViaCep.getBody().getUf())
                .cep(responseViaCep.getBody().getCep())
                .build();

        return ResponseEntity.ok(response);
    }
}

