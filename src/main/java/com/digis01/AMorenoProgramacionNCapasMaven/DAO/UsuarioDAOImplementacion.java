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

                                usuario.getRol().setNombre(resultSet.getString("NombreRol"));

                                int idDireccion = resultSet.getInt("IdDireccion");

                                if (idDireccion != 0) {
                                    Direccion direccion = new Direccion();
                                    direccion.setIdDireccion(idDireccion);
                                    direccion.setCalle(resultSet.getString("Calle"));
                                    direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                                    direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                                    usuario.getDirecciones().add(direccion);
                                    
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


    public Result<Usuario> Add(Usuario usuario) {

        Result<Usuario> result = new Result<>();

        try {
            
            jdbcTemplate.execute(
                    "{CALL UsuarioDireccionAddSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                    (CallableStatementCallback<Boolean>) callableStatement -> {

                        callableStatement.setString(1, usuario.getNombre());
                        callableStatement.setString(2, usuario.getApellidoPaterno());
                        callableStatement.setString(3, usuario.getApellidoMaterno());
                        callableStatement.setString(4, usuario.getEmail());
                        callableStatement.setDate(5, java.sql.Date.valueOf(usuario.getFechaNacimiento()));
                        callableStatement.setString(6, usuario.getPassword());
                        callableStatement.setString(7, usuario.getSexo());
                        callableStatement.setString(8, usuario.getTelefono());
                        callableStatement.setString(9, usuario.getCelular());
                        callableStatement.setString(10, usuario.getCurp());
                        callableStatement.setString(11, usuario.getUserName());
                        callableStatement.setInt(12, usuario.getRol().getIdRol());

                        Direccion direccion = usuario.getDirecciones().get(0);

                        callableStatement.setString(13, direccion.getCalle());
                        callableStatement.setString(14, direccion.getNumeroInterior());
                        callableStatement.setString(15, direccion.getNumeroExterior());
                        callableStatement.setInt(16, direccion.getColonia().getIdColonia());

                        callableStatement.execute();
                        return true;
                    }
            );

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result ADDSP() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
