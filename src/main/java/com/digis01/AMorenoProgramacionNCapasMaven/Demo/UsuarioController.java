package com.digis01.AMorenoProgramacionNCapasMaven.Demo;

import com.digis01.AMorenoProgramacionNCapasMaven.DAO.ColoniaDAOImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.EstadoDAOImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.MunicipioDAOImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.PaisDAOImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.RolDAOImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Result;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Usuario;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.UsuarioDAOImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Pais;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Rol;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/Usuario")
class UsuarioController {

//    @GetMapping("GetAll")
//    public String getAll(Model model) {
//
//        List<Usuario> usuarios = new ArrayList<>();
//
//        Usuario u1 = new Usuario();
//        u1.setNombre("Alfonso");
//        u1.setApellidoPaterno("Moreno");
//        u1.setApellidoMaterno("Juarez");
//
//        Usuario u2 = new Usuario();
//        u2.setNombre("Juan");
//        u2.setApellidoPaterno("Perez");
//        u2.setApellidoMaterno("Lopez");
//
//        Usuario u3 = new Usuario();
//        u3.setNombre("Maria");
//        u3.setApellidoPaterno("Gomez");
//        u3.setApellidoMaterno("Diaz");
//
//        usuarios.add(u1);
//        usuarios.add(u2);
//        usuarios.add(u3);
//
//        model.addAttribute("usuarios", usuarios);
//
//        return "Usuario";
//    }
    @Autowired
    private PaisDAOImplementacion pais;

    @Autowired
    private RolDAOImplementacion rol;

    @GetMapping("Form")
    public String Formulario(Model model) {

        Usuario usuario = new Usuario();
        usuario.setPais(new Pais());
        usuario.setRol(new Rol());

        model.addAttribute("usuario", usuario);

        Result resultPais = pais.GetAll();
        Result resultRol = rol.GetAll();

        if (resultPais.correct) {
            model.addAttribute("paises", resultPais.objects);
        }

        if (resultRol.correct) {
            model.addAttribute("roles", resultRol.objects);
        }

        if (!resultPais.correct || !resultRol.correct) {
            model.addAttribute(
                    "error",
                    !resultPais.correct ? resultPais.errorMessage : resultRol.errorMessage
            );
        }
        return "Formulario";
    }
    
    @PostMapping("Form")
    public String ADD(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult,@RequestParam("imagenFile") MultipartFile imagenFile, Model model){
        
        if (bindingResult.hasErrors()) {

            Result resultRol = rol.GetAll();
            Result resultPais = pais.GetAll();
            Result resultEstado = estado.GetAll(usuario.getPais().getIdPais());
            Result resultMunicipio = municipio.GetAll(usuario.getEstado().getIdEstado());
            Result resultColonia = colonia.GetAll(usuario.getMunicipio().getIdMunicipio());

            model.addAttribute("roles", resultRol.objects);
            model.addAttribute("paises", resultPais.objects);
            model.addAttribute("estados", resultEstado.objects);
            model.addAttribute("municipios", resultMunicipio.objects);
            model.addAttribute("colonias", resultColonia.objects);

            return "Formulario";
        }
        
        if (imagenFile == null || imagenFile.isEmpty()) {

            bindingResult.rejectValue("Image", "error.usuario", "Debes seleccionar una imagen");

        } else {

            bindingResult.rejectValue("Image", "error.usuario", "Si hay imagen");
        }
        
        return "redirect:/Usuario";
    }

    @Autowired
    private UsuarioDAOImplementacion dao;

    @GetMapping()
    public String GetAll(Model model) {

        Result<Usuario> result = dao.GetAll();

        if (result.correct) {
            model.addAttribute("usuarios", result.objects);
        } else {
            model.addAttribute("error", result.errorMessage);
            model.addAttribute("usuarios", new ArrayList<Usuario>());
        }

        return "Usuario";
    }

    @Autowired
    private EstadoDAOImplementacion estado;

    @GetMapping("/estado/getByPais/{idPais}")
    @ResponseBody
    public Result getEstadosByPais(@PathVariable int idPais) {
        return estado.GetAll(idPais);
    }

    @Autowired
    private MunicipioDAOImplementacion municipio;
    
    @GetMapping("/municipio/getByEstado/{idEstado}")
    @ResponseBody
    public Result getMunicipiosByEstado(@PathVariable int idEstado){
        return municipio.GetAll(idEstado);
    }
    
    @Autowired
    private ColoniaDAOImplementacion colonia;
    
    @GetMapping("/colonia/getByMunicipio/{idMunicipio}")
    @ResponseBody
    public Result getColoniasByMunicipio(@PathVariable int idMunicipio){
        return colonia.GetAll(idMunicipio);
    }
}
