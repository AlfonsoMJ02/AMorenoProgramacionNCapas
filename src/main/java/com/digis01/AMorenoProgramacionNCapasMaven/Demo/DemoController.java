package com.digis01.AMorenoProgramacionNCapasMaven;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("demo")
public class DemoController {
    
    @GetMapping("saludo")
    public String Test(@RequestParam String nombre , Model model){
        model.addAttribute("nombre", nombre);
        return "HolaMundo";
    }
    
    @GetMapping("saludo/{nombre}")
    public String Test2(@PathVariable("nombre") String nombre , Model model){
        model.addAttribute("nombre", nombre);
        return "HolaMundo";
    }
    
    @GetMapping("Calculadora{numero1}/{numero2}")
    public String Calculo(@RequestParam String operacion ,@PathVariable double numero1 ,@PathVariable double numero2 , Model model){
        double resultado;
        String calcula = "";
        switch (operacion) {
            case "Suma":
                resultado = numero1 + numero2;
                break;
            case "Resta":
                resultado = numero1 - numero2;
                break;
            case "Multiplicacion":
                resultado = numero1 * numero2;
                break;
            case "Division":
                resultado = numero1 / numero2;
                break;
            default:
                throw new AssertionError();
        }
        model.addAttribute("operacion", operacion);
        model.addAttribute("numero1", numero1);
        model.addAttribute("numero2", numero2);
        model.addAttribute("resultado", resultado);
        return "Calculadora";
    }
    
    @GetMapping("Usuarios")
    public String Usuario(){
        return "Usuario";
    }
    
    @GetMapping("Usuario/Form")
    public String Formulario(){
        return "Formulario";
    }
}
