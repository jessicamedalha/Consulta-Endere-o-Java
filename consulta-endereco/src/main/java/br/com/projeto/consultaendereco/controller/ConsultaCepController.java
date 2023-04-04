package br.com.projeto.consultaendereco.controller;
import br.com.projeto.consultaendereco.dto.EnderecoDTO;
import br.com.projeto.consultaendereco.dto.EnderecoViaCepDTO;
import br.com.projeto.consultaendereco.utils.ConstsEndereco;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("consulta-endereco")
public class ConsultaCepController {

    @GetMapping("{cep}")
    public ResponseEntity<?> consultaViaCep (@PathVariable("cep") String cep){

        if (cep.isEmpty() || cep.isBlank()){
            return ResponseEntity.badRequest().body("CEP não deve ser vazio");
        }
        if (cep.length() > 8) {
            return ResponseEntity.badRequest().body("CEP invalido");
        }
        if (cep.length() < 8){
            return ResponseEntity.badRequest().body("CEP invalido");
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<EnderecoViaCepDTO> responseViaCep =
                restTemplate.getForEntity(
                        String.format("https://viacep.com.br/ws/%s/json", cep),
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

