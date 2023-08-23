
package com.Torneo.TorneoDeLatas.Controladores;

import com.Torneo.TorneoDeLatas.Servicios.usuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")
public class Controladorimagen {
    @Autowired
    usuarioServicio usuarioservicio;
    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenusuario(@PathVariable String id){
        usuarioservicio.
        
        return null;
    }
}
