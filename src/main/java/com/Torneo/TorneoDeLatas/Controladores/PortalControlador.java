package com.Torneo.TorneoDeLatas.Controladores;

import com.Torneo.TorneoDeLatas.Excepciones.Miexception;

import com.Torneo.TorneoDeLatas.Servicios.usuarioServicio;
import com.Torneo.TorneoDeLatas.entidades.Usuario;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private usuarioServicio usuarioservicio;

    @GetMapping("/")
    public String index() {

        return "index.html";
    }

    @GetMapping("/Iniciosesion")
    public String Iniciosesion(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Correo o contraseña incorrectos");
        }
        return "Iniciosesion.html";
    }

    @GetMapping("/registro")
    public String registro() {

        return "usuario-form.html";
    }

    @PostMapping("/registrar")

    public String registrar(String id, @RequestParam String email, @RequestParam(required = false) Integer dni, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String password, @RequestParam String password2, ModelMap modelo, MultipartFile archivo) {

        try {
            usuarioservicio.crearUsuario(archivo, email, dni, nombre, apellido, password, password2);
            modelo.put("exito", "Usuario cargado exitosamente!!!");
        } catch (Miexception ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("email", email);
            modelo.put("dni", dni);
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("password", password);
            modelo.put("password2", password2);
            return "usuario-form.html";
        }

        return "index.html";

    }

    @GetMapping("/Basesycondiciones")
    public String Basesycondicones() {

        return "Basesycondiciones.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/Central")
    public String Central(HttpSession Session) {
        Usuario logueado = (Usuario) Session.getAttribute("usuariosession");
        return "Central.html";
    }

    @GetMapping("/Listartodos")
    public String Listartodos(
            ModelMap modelo) {
        List<Usuario> usuarios = usuarioservicio.listarUsuario();

        modelo.addAttribute("usuarios", usuarios);
        return "Listartodos.html";

    }

    @GetMapping("/Modificar")
    public String Modificar(ModelMap modelo) {
      //  modelo.put("usuario", usuarioservicio.getOne(id));
        return "usuario_modificar.html";
    }

    @PostMapping("/Modifica")
    public String Modifica( @RequestParam String email2, @RequestParam(required = false) Integer dni2, @RequestParam String nombre2, @RequestParam String apellido2, @RequestParam String password3, @RequestParam String password4, ModelMap modelo, MultipartFile archivo) {
        List<Usuario> usuarios = usuarioservicio.listarUsuario();

        modelo.addAttribute("usuarios", usuarios);

        for (Usuario usuario : usuarios) {

            if (email2.equals(usuario.getEmail())) {
                try {
                    System.out.println(email2+" "+" "+usuario.getEmail());
                    usuarioservicio.modificarUsuario(archivo, email2, dni2, nombre2, apellido2, password3, password4);
                } catch (Miexception ex) {
                    modelo.put("error", ex.getMessage());
                    return "usuario_modificar.html";
                }
            }

        }
        return "Central.html";
    }

    @GetMapping("/paneles")
    public String paneles() {

        return "panel.html";
    }

    @GetMapping("/Consulta")
    public String Consulta() {

        return "Consulta.html";

    }

    @PostMapping("/Consultar")
    public String Consultar(@RequestParam(required = false) String email2, @RequestParam(required = false) String nombre2, @RequestParam(required = false) String apellido2, ModelMap modelo) {

        List<Usuario> usuarios = usuarioservicio.listarUsuario();

        modelo.addAttribute("usuarios", usuarios);
        System.out.println(email2 + nombre2 + apellido2);
        for (Usuario usuario : usuarios) {

            if (email2.equals(usuario.getEmail())) {

                email2 = usuario.getEmail();
                Integer dni2 = usuario.getDni();
                nombre2 = usuario.getNombre();
                apellido2 = usuario.getApellido();
                double ecopuntos2 = usuario.getEcopuntos();

                modelo.put("email2", email2);
                modelo.put("dni2", dni2);
                modelo.put("nombre2", nombre2);
                modelo.put("apellido2", apellido2);
                modelo.put("ecopuntos", ecopuntos2);
            } else {
                modelo.put("error", "Usuario no encontrado");

            }

        }
        return "Consulta.html";
    }

    @GetMapping("/Ingresarpuntos")
    public String Ingresarpuntos() {

        return "Ingresarpuntos.html";

    }

    @PostMapping("/Sumar")
    public String Sumar(@RequestParam String email2, @RequestParam double ecop, ModelMap modelo) {

        List<Usuario> usuarios = usuarioservicio.listarUsuario();

        modelo.addAttribute("usuarios", usuarios);

        for (Usuario usuario : usuarios) {

            if (email2.equals(usuario.getEmail())) {
                usuarioservicio.modificarUsuariopuntos(email2, ecop);

            }

        }
        return "Consulta.html";
    }

    @PostMapping("/SumarU")
    public String Sumar(@RequestParam String email2, @RequestParam double ecop2, @RequestParam double env, ModelMap modelo) {

        List<Usuario> usuarios = usuarioservicio.listarUsuario();

        modelo.addAttribute("usuarios", usuarios);

        for (Usuario usuario : usuarios) {

            if (email2.equals(usuario.getEmail())) {
                usuarioservicio.modificarUsuariopuntosU(email2, ecop2, env);

            }

        }
        return "Consulta.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/Perfil")
    public String Perfil(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);

        return "usuario_modificar.html";
    }
     @GetMapping("/ecoideas")
    public String ecoideas() {

        return "ecoideas.html";
    }
    
    
    
    
}
 
    

