package com.digis01.AMorenoProgramacionNCapasMaven.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

@Service
public class ValidationService {
    
    @Autowired
    private Validator validator;
    
    public BindingResult ValidateObject(Object object){
        DataBinder dataBinder = new DataBinder(object);
        dataBinder.setValidator(validator);
        dataBinder.validate();
        
        return dataBinder.getBindingResult();
    }
}
