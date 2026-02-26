package com.digis01.AMorenoProgramacionNCapasMaven.Demo;

import com.digis01.AMorenoProgramacionNCapasMaven.DAO.ColoniaDAOImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.EstadoDAOImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.MunicipioDAOImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.PaisDAOImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.RolDAOImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Result;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Usuario;
import com.digis01.AMorenoProgramacionNCapasMaven.DAO.UsuarioDAOImplementacion;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Colonia;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Direccion;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.ErroresArchivo;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Pais;
import com.digis01.AMorenoProgramacionNCapasMaven.ML.Rol;
import com.digis01.AMorenoProgramacionNCapasMaven.Services.ValidationService;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String ADD(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult, @RequestParam("imagenFile") MultipartFile imagenFile,RedirectAttributes redirectAttributes, Model model){

        if (bindingResult.hasErrors()) {

            Result resultRol = rol.GetAll();
            Result resultPais = pais.GetAll();

            model.addAttribute("roles", resultRol.objects);
            model.addAttribute("paises", resultPais.objects);

            return "Formulario";
        }

        try {
            if (imagenFile != null && !imagenFile.isEmpty()) {

                String nombreArchivo = imagenFile.getOriginalFilename();

                String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1).toLowerCase();

                if (extension.equals("jpg") || extension.equals("png")) {

                    byte[] arregloBytes = imagenFile.getBytes();
                    String base64Img = Base64.getEncoder().encodeToString(arregloBytes);

                    usuario.setImagen(base64Img);

                } else {
                    System.out.println("Formato no válido");
                    usuario.setImagen(null);
                }

            } else {
               
                usuario.setImagen(null);
            }

          
            Result result = dao.Add(usuario);

            if (result.correct) {
                redirectAttributes.addFlashAttribute("success", true);
                return "redirect:/Usuario";
            } else {
                redirectAttributes.addFlashAttribute("error", true);
                return "redirect:/Usuario";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Formulario";
        }
    }
    
    @GetMapping("/Search")
    public String Search(@RequestParam(required = false) String nombre, @RequestParam(required = false) String apellidoPaterno, @RequestParam(required = false) String apellidoMaterno, @RequestParam(required = false) Integer idRol, Model model) {

        Result result = dao.Search(nombre, apellidoPaterno, apellidoMaterno, idRol);

        model.addAttribute("usuarios", result.objects);

        Result resultRol = rol.GetAll();
        model.addAttribute("roles", resultRol.objects);

        return "Usuario";
    }
    
    @Autowired
    private UsuarioDAOImplementacion dao;

    @GetMapping()
    public String GetAll(Model model) {

        Result<Usuario> result = dao.GetAll();
        Result resultRol = rol.GetAll();
        Result resultPais = pais.GetAll(); 

        if (result.correct) {
            model.addAttribute("usuarios", result.objects);
        } else {
            model.addAttribute("usuarios", new ArrayList<Usuario>());
        }

        if (resultRol.correct) {
            model.addAttribute("roles", resultRol.objects);
        }

        if (resultPais.correct) {
            model.addAttribute("paises", resultPais.objects);
        }

        return "Usuario";
    }
    
    @GetMapping("/getById")
    @ResponseBody
    public Result GetById(@RequestParam int idUsuario){
        return dao.GetById(idUsuario);
    }
    
    @GetMapping("/EditarUsuario")
    public String EditarUsuario(Model model){
        return "EditarUsuario";
    }
    
    @PostMapping("/Update")
    @ResponseBody
    public Result Update(@RequestBody Usuario usuario) {
        return dao.Update(usuario);
    }
    
    @PostMapping("/Direccion/Add")
    @ResponseBody
    public Result AddDireccion(@RequestBody Direccion direccion) {
        return dao.AddDireccion(direccion);
    }
    
    @PostMapping("/Update/Direccion")
    @ResponseBody
    public Result UpdateDireccion(@RequestBody Direccion direccion) {
        return dao.UpdateDireccion(direccion);
    }
    
    @PostMapping("/Delete/{idUsuario}")
    @ResponseBody
    public Result Delete(@PathVariable int idUsuario){
        return dao.Delete(idUsuario);
    }
    
    @GetMapping("/Direccion/GetById")
    @ResponseBody
    public Result GetByIdDireccion(@RequestParam int idDireccion) {
        return dao.GetByIdDireccion(idDireccion);
    }
    
    @GetMapping("/Direccion/Delete/{idDireccion}")
    public String DeleteDireccion(@PathVariable int idDireccion){
        dao.DeleteDireccion(idDireccion);
        return "redirect:/Usuario";
    }
    
    @PostMapping("/Imagen/Update")
    @ResponseBody
    public Result UpdateImagen(@RequestParam("idUsuario") int idUsuario, @RequestParam("imagenFile") MultipartFile imagenFile){
        Result result = new Result();
        
        try {
            if (imagenFile != null && !imagenFile.isEmpty()) {
                if (imagenFile.getContentType().startsWith("image/")) {
                    byte[] arregloBytes = imagenFile.getBytes();
                    String base64Img = Base64.getEncoder().encodeToString(arregloBytes);
                    
                    result = dao.UpdateImagen(idUsuario, base64Img);
                }else{
                    result.correct = false;
                    result.errorMessage = "Formato no valido";
                }
            }else {
                result.correct = false;
                result.errorMessage = "No se selecciono ninguna imagen";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;     
    }
    
    @GetMapping("/CargaMasiva")
    public String cargaMasiva(){
        return "CargaMasiva";
    }
    
    @PostMapping("/CargaMasiva")
    public String CargaMasiva(@RequestParam("archivo") MultipartFile archivo, Model model) {
        
        try {
            if (archivo != null) {

                String rutaBase = System.getProperty("user.dir");
                String rutaCarpeta = "src/main/resources/archivosCM";
                String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
                String nombreArchivo = fecha + archivo.getOriginalFilename();
                String rutaArchivo = rutaBase + "/" + rutaCarpeta + "/" + nombreArchivo;
                String extension = archivo.getOriginalFilename().split("\\.")[1];
                List<Usuario> usuarios = null;
                if (extension.equals("txt")) {
                    archivo.transferTo(new File(rutaArchivo));
                    usuarios = LecturaArchivoTxt(new File(rutaArchivo));
                } else if (extension.equals("xlsx")) {
                    archivo.transferTo(new File(rutaArchivo));
                    usuarios = LecturaArchivoXLSX(new File(rutaArchivo));
                } else {
                    model.addAttribute("mensajeError", "Extension erronea");
                    return "CargaMasiva";
                }

                List<ErroresArchivo> errores = ValidarDatos(usuarios);

                if (errores.isEmpty()) {
                    model.addAttribute("archivoValido", true);
    
                } else {
//                    retorno lista errores, y la renderizo.
                    model.addAttribute("listaErrores", errores);
                    model.addAttribute("archivoValido", false);
                }
                /*
                    - insertarlos
                    - renderizar la lista de errores
                 */
            }
        } catch (Exception ex) {
            // notificación de error

            model.addAttribute("mensajeError", ex.getMessage());
        }
        return "CargaMasiva";
    }
    
    public List<Usuario> LecturaArchivoTxt(File archivo) {
        List<Usuario> usuarios = null;
        //try with reouces - Garbage collector
        try(InputStream inputStream = new FileInputStream(archivo);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))){
                   
            usuarios = new ArrayList<>();
            String cadena = "";
            int fila = 1;
            while ((cadena = bufferedReader.readLine()) != null) {                
                String[] datosUsuario = cadena.split("\\|");
                Usuario usuario = new Usuario();
                usuario.setRol(new Rol());
                usuario.setdireccion(new Direccion());
                usuario.setColonia(new Colonia());
                usuario.setNombre(datosUsuario[0]);
                usuario.setApellidoPaterno(datosUsuario[1]);
                usuario.setApellidoMaterno(datosUsuario[2]);
                usuario.setEmail(datosUsuario[3]);
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate fecha = LocalDate.parse(datosUsuario[4], formatter);
                usuario.setFechaNacimiento(fecha);
                
                usuario.setPassword(datosUsuario[5]);
                usuario.setSexo(datosUsuario[6]);
                usuario.setTelefono(datosUsuario[7]);
                usuario.setCelular(datosUsuario[8]);
                usuario.setCurp(datosUsuario[9]);
                usuario.setUserName(datosUsuario[10]);
                usuario.getRol().setIdRol(Integer.parseInt(datosUsuario[11]));
                usuario.getdireccion().setCalle(datosUsuario[12]);
                usuario.getdireccion().setNumeroInterior(datosUsuario[13]);
                usuario.getdireccion().setNumeroExterior(datosUsuario[14]);
                usuario.getColonia().setIdColonia(Integer.parseInt(datosUsuario[15]));
                
                usuarios.add(usuario);
                fila++;
            }         
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return usuarios;
    }
    
    public List<Usuario> LecturaArchivoXLSX(File archivo) {

        List<Usuario> usuarios = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(archivo);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();

            int fila = 1;

            for (Row row : sheet) {          
                Usuario usuario = new Usuario();
                usuario.setRol(new Rol());
                usuario.setdireccion(new Direccion());
                usuario.setColonia(new Colonia());

                usuario.setNombre(row.getCell(0).toString());
                usuario.setApellidoPaterno(row.getCell(1).toString());
                usuario.setApellidoMaterno(row.getCell(2).toString());
                usuario.setEmail(row.getCell(3).toString());

                Cell celdaFecha = row.getCell(4);
                LocalDate fecha;

                if (celdaFecha.getCellType() == CellType.NUMERIC) {
                    fecha = celdaFecha.getLocalDateTimeCellValue().toLocalDate();

                } else {
                    String fechaTexto = celdaFecha.getStringCellValue();

                    try {
                        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        fecha = LocalDate.parse(fechaTexto, formatter1);
                    } catch (Exception ex1) {
                        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("M/d/yy");
                        fecha = LocalDate.parse(fechaTexto, formatter2);
                    }
                }

                usuario.setFechaNacimiento(fecha);
                usuario.setPassword(row.getCell(5).toString());
                usuario.setSexo(row.getCell(6).toString());
                usuario.setTelefono(dataFormatter.formatCellValue(row.getCell(7)));
                usuario.setCelular(dataFormatter.formatCellValue(row.getCell(8)));
                usuario.setCurp(row.getCell(9).toString());
                usuario.setUserName(row.getCell(10).toString());

                usuario.getRol().setIdRol(Integer.parseInt(dataFormatter.formatCellValue(row.getCell(11))));

                usuario.getdireccion().setCalle(dataFormatter.formatCellValue(row.getCell(12)));
                usuario.getdireccion().setNumeroInterior(dataFormatter.formatCellValue(row.getCell(13)));
                usuario.getdireccion().setNumeroExterior(dataFormatter.formatCellValue(row.getCell(14)));

                usuario.getColonia().setIdColonia(Integer.parseInt(dataFormatter.formatCellValue(row.getCell(15))));

                usuarios.add(usuario);
                fila++;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return usuarios;
    }
    
    @Autowired
    private ValidationService validationService;
    
    public List<ErroresArchivo> ValidarDatos(List<Usuario> usuarios){

    List<ErroresArchivo> errores = new ArrayList<>();

    int fila = 1;

    for (Usuario usuario : usuarios) {

        BindingResult bindingResult = validationService.ValidateObject(usuario);

        System.out.println("Errores en fila " + fila + ": " + bindingResult.getErrorCount());

        if (bindingResult.hasErrors()) {

            for (FieldError fieldError : bindingResult.getFieldErrors()) {

                ErroresArchivo error = new ErroresArchivo();
                error.fila = fila;
                error.dato = fieldError.getField();
                error.descripcion = fieldError.getDefaultMessage();

                errores.add(error);
            }
        }

        fila++;
    }

    return errores;
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
