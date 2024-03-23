package secretrans.example.secretrans.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/infracciones")
public class InfraccionController {

    private static int valCodigo = 0;

    @RequestMapping(value = "/healthcheck")
    public String healthCheck(){
        return "Service status fine!";
    }

    @PostMapping(value = "/{codigo}")
    public void setCodigo(@PathVariable("codigo") int codigo){
        valCodigo = codigo;
    }

    @GetMapping
    public int getCodigo(){
        return valCodigo;
    }

}
