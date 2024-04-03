package secretrans.example.secretrans.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import secretrans.example.secretrans.model.Infraccion;
import secretrans.example.secretrans.services.ErrorMessage;
import secretrans.example.secretrans.services.IServicioInfraccion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/infracciones")
public class InfraccionController {

    private static int valCodigo = 0;

    @Autowired
    private IServicioInfraccion servicioInfraccion;

    @RequestMapping(value = "/healthcheck")
    public String healthCheck(){
        return "Service status fine!";
    }

    /*
    @PostMapping(value = "/{codigo}")
    public void setCodigo(@PathVariable("codigo") int codigo){
        valCodigo = codigo;
    }
    */


    /*
    @GetMapping
    public int getCodigo(){
        return valCodigo;
    }
    S
     */

    @GetMapping
    public ResponseEntity<Infraccion> getInfraccion( ){
        //Infraccion infraccion = Infraccion.builder().numero(123).tipo("Velocity").build();
        Infraccion infraccion;
        infraccion = servicioInfraccion.getInfraccion();

        if(infraccion == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(infraccion);

    }

    @PostMapping
    public ResponseEntity<Infraccion> setInfraccion(@RequestBody Infraccion infraccion, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        servicioInfraccion.setInfraccion(infraccion);
        return ResponseEntity.ok(infraccion);
    }

    private String formatMessage(BindingResult result){
        List<Map<String,String>> errores = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String,String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());

        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .mensajes(errores)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }
}
