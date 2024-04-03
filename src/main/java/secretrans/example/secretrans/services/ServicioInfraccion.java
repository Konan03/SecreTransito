package secretrans.example.secretrans.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import secretrans.example.secretrans.model.Infraccion;

@Service
@RequiredArgsConstructor
public class ServicioInfraccion implements IServicioInfraccion{

    private Infraccion infraccion;
    @Override
    public Infraccion getInfraccion() {
        return infraccion;
    }

    @Override
    public Infraccion setInfraccion(Infraccion infraccion) {
        this.infraccion = infraccion;
        return this.infraccion;
    }
}
