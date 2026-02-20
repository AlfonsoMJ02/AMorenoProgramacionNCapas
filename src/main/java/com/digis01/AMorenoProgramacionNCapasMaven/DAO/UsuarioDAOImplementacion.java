package com.digis01.AMorenoProgramacionNCapasMaven.DAO;

import com.digis01.AMorenoProgramacionNCapasMaven.ML.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDAOImplementacion implements IUsuario{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result<Usuario> GetAll() {

        Result<Usuario> result = new Result<>();

        try {

            jdbcTemplate.execute("{CALL UsuarioDireccionGetAllSP(?)}",
                    (CallableStatementCallback<Boolean>) callableStatement -> {

                        callableStatement.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
                        callableStatement.execute();

                        ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                        result.objects = new ArrayList<>();

                        while (resultSet.next()) {

                            int idUsuario = resultSet.getInt("IdUsuario");

                            if (!result.objects.isEmpty() && idUsuario == result.objects.get(result.objects.size() - 1).getIdUsuario()) {

                                Direccion direccion = new Direccion();
                                direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                                direccion.setCalle(resultSet.getString("Calle"));
                                direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                                direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                                Colonia colonia = new Colonia();
                                colonia.setIdColonia(resultSet.getInt("IdColonia"));
                                colonia.setNombre(resultSet.getString("NombreColonia"));
                                colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));

                                Municipio municipio = new Municipio();
                                municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                                municipio.setNombre(resultSet.getString("NombreMunicipio"));

                                Estado estado = new Estado();
                                estado.setIdEstado(resultSet.getInt("IdEstado"));
                                estado.setNombre(resultSet.getString("NombreEstado"));

                                Pais pais = new Pais();
                                pais.setIdPais(resultSet.getInt("IdPais"));
                                pais.setNombre(resultSet.getString("NombrePais"));

                                estado.setPais(pais);
                                municipio.setEstado(estado);
                                colonia.setMunicipio(municipio);
                                direccion.setColonia(colonia);

                                result.objects
                                        .get(result.objects.size() - 1)
                                        .getDirecciones()
                                        .add(direccion);

                            } else {

                                Usuario usuario = new Usuario();
                                usuario.setRol(new Rol());
                                usuario.setDirecciones(new ArrayList<>());

                                usuario.setIdUsuario(idUsuario);
                                usuario.setNombre(resultSet.getString("NombreUsuario"));
                                usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                                usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                                usuario.setEmail(resultSet.getString("Email"));
                                usuario.setPassword(resultSet.getString("Password"));
                                usuario.setFechaNacimiento(
                                        resultSet.getDate("FechaNacimiento") != null
                                        ? resultSet.getDate("FechaNacimiento").toLocalDate()
                                        : null
                                );
                                usuario.setSexo(resultSet.getString("Sexo"));
                                usuario.setTelefono(resultSet.getString("Telefono"));
                                usuario.setCelular(resultSet.getString("Celular"));
                                usuario.setCurp(resultSet.getString("Curp"));
                                usuario.setUserName(resultSet.getString("UserName"));
                                
                                usuario.setRol(new Rol());
                                usuario.getRol().setIdRol(resultSet.getInt("IdRol"));
                                usuario.getRol().setNombre(resultSet.getString("NombreRol"));

                                int idDireccion = resultSet.getInt("IdDireccion");

                                if (idDireccion != 0) {
                                    Direccion direccion = new Direccion();
                                    direccion.setIdDireccion(idDireccion);
                                    direccion.setCalle(resultSet.getString("Calle"));
                                    direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                                    direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                                    
                                    
                                    Colonia colonia = new Colonia();
                                    colonia.setIdColonia(resultSet.getInt("IdColonia"));
                                    colonia.setNombre(resultSet.getString("NombreColonia"));
                                    colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));

                                    Municipio municipio = new Municipio();
                                    municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                                    municipio.setNombre(resultSet.getString("NombreMunicipio"));

                                    Estado estado = new Estado();
                                    estado.setIdEstado(resultSet.getInt("IdEstado"));
                                    estado.setNombre(resultSet.getString("NombreEstado"));

                                    Pais pais = new Pais();
                                    pais.setIdPais(resultSet.getInt("IdPais"));
                                    pais.setNombre(resultSet.getString("NombrePais"));

                                    estado.setPais(pais);
                                    municipio.setEstado(estado);
                                    colonia.setMunicipio(municipio);
                                    direccion.setColonia(colonia);

                                    usuario.getDirecciones().add(direccion);
                                }

                                result.objects.add(usuario);
                            }
                        }

                        result.correct = true;
                        return true;
                    });

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }
    
    @Override
    public Result GetById(int idUsuario) {

        Result result = new Result();

        try {

            jdbcTemplate.execute("{CALL UsuarioGetByIdSP(?, ?)}",
                    (CallableStatementCallback<Void>) callableStatement -> {

                        callableStatement.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

                        callableStatement.setInt(2, idUsuario);

                        callableStatement.execute();

                        ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                        if (resultSet.next()) {

                            Usuario usuario = new Usuario();

                            usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                            usuario.setNombre(resultSet.getString("Nombre"));
                            usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                            usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                            usuario.setEmail(resultSet.getString("Email"));

                            if (resultSet.getDate("FechaNacimiento") != null) {
                                usuario.setFechaNacimiento(
                                        resultSet.getDate("FechaNacimiento").toLocalDate()
                                );
                            }

                            usuario.setPassword(resultSet.getString("Password"));
                            usuario.setSexo(resultSet.getString("Sexo"));
                            usuario.setTelefono(resultSet.getString("Telefono"));
                            usuario.setCelular(resultSet.getString("Celular"));
                            usuario.setCurp(resultSet.getString("Curp"));
                            usuario.setUserName(resultSet.getString("UserName"));

                            Rol rol = new Rol();
                            rol.setIdRol(resultSet.getInt("IdRol"));
                            rol.setNombre(resultSet.getString("NombreRol"));

                            usuario.setRol(rol);

                            result.object = usuario;
                            result.correct = true;

                        } else {
                            result.correct = false;
                            result.errorMessage = "Usuario no encontrado";
                        }

                        resultSet.close();
                        return null;
                    });

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }
    
    @Override
    public Result Add(Usuario usuario) {

        Result result = new Result();

        try {

            jdbcTemplate.execute("{CALL UsuarioAddSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                (CallableStatementCallback<Boolean>) callableStatement -> {

                    callableStatement.setString(1, usuario.getNombre());
                    callableStatement.setString(2, usuario.getApellidoPaterno());
                    callableStatement.setString(3, usuario.getApellidoMaterno());
                    callableStatement.setString(4, usuario.getEmail());
                    callableStatement.setString(5, usuario.getUserName());
                    callableStatement.setString(6, usuario.getPassword());
                    callableStatement.setString(7, usuario.getSexo());
                    callableStatement.setString(8, usuario.getTelefono());
                    callableStatement.setString(9, usuario.getCelular());
                    callableStatement.setString(10, usuario.getCurp());

                    callableStatement.setDate(11,
                        java.sql.Date.valueOf(usuario.getFechaNacimiento()));

                    callableStatement.setInt(12,
                        usuario.getRol().getIdRol());

                    // Dirección
                    callableStatement.setString(13,
                        usuario.getdireccion().getCalle());

                    callableStatement.setString(14,
                        usuario.getdireccion().getNumeroExterior());

                    callableStatement.setInt(15,
                        usuario.getColonia().getIdColonia());

                    callableStatement.execute();

                    result.correct = true;
                    return true;
                });

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    
    @Override
    public Result Update(Usuario usuario) {

        Result result = new Result();

        try {

            jdbcTemplate.update(
                "CALL UsuarioUpdateSP(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",

                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getApellidoPaterno(),
                usuario.getApellidoMaterno(),
                usuario.getEmail(),

                usuario.getFechaNacimiento() != null 
                    ? java.sql.Date.valueOf(usuario.getFechaNacimiento()) 
                    : null,

                usuario.getPassword(),
                usuario.getSexo(),
                usuario.getTelefono(),
                usuario.getCelular(),
                usuario.getCurp(),
                usuario.getUserName(),
                usuario.getRol().getIdRol()
            );

            result.correct = true;

        } catch (Exception ex) {

            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }
    
    public Result<Usuario> Delete(int idUsuario) {

        Result<Usuario> result = new Result<>();

        try {

            jdbcTemplate.execute("{CALL UsuarioDeleteSP(?)}",
                    (CallableStatementCallback<Boolean>) callableStatement -> {

                        callableStatement.setInt(1, idUsuario);
                        callableStatement.execute();

                        result.correct = true;
                        return true;
                    });

        } catch (Exception ex) {

            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }
    
    public Result<Usuario> DeleteDireccion(int idDireccion){
        Result<Usuario> result = new Result<>();
        
        try {
            jdbcTemplate.execute("{CALL DireccionDeleteSP(?)}", 
                    (CallableStatementCallback<Boolean>) callableStatement -> {
                        callableStatement.setInt(1, idDireccion);
                        callableStatement.execute();
                        
                        result.correct = true;
                        return true;
                    });
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }
        return result;
    }
 
    
    @Override
    public Result GetByIdDireccion(int idDireccion) {

        Result result = new Result();

        try {

            jdbcTemplate.execute("{CALL DireccionGetByIdSP(?, ?)}",
                (CallableStatementCallback<Void>) callableStatement -> {

                    callableStatement.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
                    callableStatement.setInt(2, idDireccion);
                    callableStatement.execute();

                    ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                    if (resultSet.next()) {

                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        Colonia colonia = new Colonia();
                        colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        colonia.setNombre(resultSet.getString("NombreColonia"));
                        colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));

                        Municipio municipio = new Municipio();
                        municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        municipio.setNombre(resultSet.getString("NombreMunicipio"));

                        Estado estado = new Estado();
                        estado.setIdEstado(resultSet.getInt("IdEstado"));
                        estado.setNombre(resultSet.getString("NombreEstado"));

                        Pais pais = new Pais();
                        pais.setIdPais(resultSet.getInt("IdPais"));
                        pais.setNombre(resultSet.getString("NombrePais"));

                        estado.setPais(pais);
                        municipio.setEstado(estado);
                        colonia.setMunicipio(municipio);
                        direccion.setColonia(colonia);

                        result.object = direccion;
                        result.correct = true;

                    } else {
                        result.correct = false;
                        result.errorMessage = "Direccion no encontrada";
                    }

                    resultSet.close();
                    return null;
                });

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }
    
    public Result UpdateDireccion(Direccion direccion){
        
        Result result = new Result();
        
        try {
            jdbcTemplate.update("{CALL DireccionUpdateSP(?,?,?,?,?)}",
            direccion.getIdDireccion(),
            direccion.getCalle(),
            direccion.getNumeroInterior(),
            direccion.getNumeroExterior(),
            direccion.getColonia()
            );
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
}
