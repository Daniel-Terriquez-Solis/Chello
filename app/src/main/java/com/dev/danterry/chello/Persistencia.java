package com.dev.danterry.chello;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class Persistencia {
    /*
        String NAMESPACE = "http://webservices/";
        String URL = "http://localhost:8080/ProyectoFinalWS/UsuarioWS?WSDL";
        Boolean resultado = false;
        */
    boolean dotNet = false;

    //---------Usuario----------------------------------------------------------------------------------------------------------
    public int agregarUsuario(Usuario usuario) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/UsuarioWS?wsdl";
        boolean dotNet = false;
        int resultado = 0;

        String METHOD_NAME = "AgregarUsuario";
        String ACTION_SOAP = "";
        try {
            //Configuracion del soap
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parametros a soap
            request.addProperty("nombre", usuario.getNombre());
            request.addProperty("email", usuario.getEmail());
            request.addProperty("contrasena", usuario.getContrasena());

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Integer.parseInt(res.toString());

            return resultado;

        } catch(Exception ex){
            return resultado;
        }
    }

    public boolean editarUsuario(Usuario usuario) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/UsuarioWS?wsdl";
        boolean dotNet = false;
        Boolean resultado = false;

        String METHOD_NAME = "EditarUsuario";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", usuario.getId());
            Request.addProperty("nombre", usuario.getNombre());
            Request.addProperty("email", usuario.getEmail());
            Request.addProperty("contrasena", usuario.getContrasena());

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;

        } catch (Exception ex) {
            return resultado;
        }
    }



    public Usuario seleccionarUsuario(int userId){
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/UsuarioWS?wsdl";
        Boolean resultado = false;

        String METHOD_NAME = "SeleccionarUsuario";
        String ACTION_SOAP = "";
        Usuario usuario = new Usuario();
        try {
            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            Request.addProperty("id", userId);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){

                    int id = 0;
                    String nombre = null;
                    String email = null;
                    String contrasena = null;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    nombre = body.getProperty("titulo").toString();
                    email = body.getProperty("descripcion").toString();
                    contrasena = body.getProperty("contrasena").toString();

                    usuario.setId(id);
                    usuario.setNombre(nombre);
                    usuario.setEmail(email);
                    usuario.setContrasena(contrasena);
                }
            }
            return usuario;
        } catch(Exception ex){
            return null;
        }
    }


    public Usuario login(String email, String contrasena) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/UsuarioWS?wsdl";
        Boolean resultado = false;

        String METHOD_NAME = "Login";
        String ACTION_SOAP = NAMESPACE+METHOD_NAME;
        Usuario usuario = new Usuario();
        try {
            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("email", email);
            Request.addProperty("contra", contrasena);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP,soapEnvelop);

            //Recibir datos
            //SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;
            Log.d("OBJ RESPONSE",obj.toString());
            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                Log.d("tamanio",tamanio+"");

                for(int i=0; i<tamanio; i++){

                    int id = 0;
                    String nombre = null;
                    email = null;
                    contrasena = null;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    nombre = body.getProperty("nombre").toString();
                    email = body.getProperty("email").toString();
                    contrasena = body.getProperty("contrasena").toString();
                    Log.d("Usuario nomre:",nombre);
                    usuario.setId(id);
                    usuario.setNombre(nombre);
                    usuario.setEmail(email);
                    usuario.setContrasena(contrasena);
                }
            }

            //resultado = Boolean.valueOf(res.toString());

            return usuario;

        } catch (Exception ex) {
            return null;
        }

    }


    //-----------Tablero-------------------------------------------------------------------------------------------------------

    public int agregarTablero(Tablero tablero) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/TableroWS?WSDL";
        int resultado = 0;

        String METHOD_NAME = "AgregarTablero";
        String ACTION_SOAP = "";
        try {


            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parametros a soap
            Request.addProperty("titulo", tablero.getTitulo());

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Integer.parseInt(res.toString());

            return resultado;

        } catch(Exception ex){
            return resultado;
        }
    }

    public boolean eliminarTablero(int id) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/TableroWS?WSDL";

        Boolean resultado = false;

        String METHOD_NAME = "EliminarTablero";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", id);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;


        } catch (Exception ex) {
            return resultado;
        }
    }

    public boolean editarTablero(Tablero tablero) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/TableroWS?WSDL";
        Boolean resultado = false;

        String METHOD_NAME = "EditarTablero";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", tablero.getId());
            Request.addProperty("nombre", tablero.getTitulo());

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;

        } catch (Exception ex) {
            return resultado;
        }
    }

    public Tablero seleccionarTablero(int id) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/TableroWS?WSDL";

        String METHOD_NAME = "SeleccionarTablero";
        String ACTION_SOAP = "";
        Tablero tablero = new Tablero();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", id);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){

                    id = 0;
                    String titulo = null;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    titulo = body.getProperty("titulo").toString();

                    tablero.setId(id);
                    tablero.setTitulo(titulo);
                }
            }

            return tablero;

        } catch (Exception ex) {
            return null;
        }
    }


    public boolean crearTableroEquipo(int idTablero, int idEquipo) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/tableroEquipoWS?WSDL";
        Boolean resultado = false;

        String METHOD_NAME = "CrearTableroEquipo";
        String ACTION_SOAP = "";

        try {
            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("idTablero", idTablero);
            Request.addProperty("idEquipo", idEquipo);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;

        } catch(Exception ex){
            return resultado;
        }
    }


    public boolean eliminarTableroEquipo(int id) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/tableroEquipoWS?WSDL";

        Boolean resultado = false;
        String METHOD_NAME = "EliminarTableroEquipo";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", id);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;


        } catch (Exception ex) {
            return resultado;
        }
    }

    public boolean eliminarTableroEquipos(int idTablero) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/tableroEquipoWS?WSDL";

        Boolean resultado = false;

        String METHOD_NAME = "EliminarTableroEquipo";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", idTablero);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;


        } catch (Exception ex) {
            return resultado;
        }
    }

    public TableroEquipo seleccionarTableroEquipo(int id) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/tableroEquipoWS?WSDL";

        String METHOD_NAME = "SeleccionarTableroEquipo";
        String ACTION_SOAP = "";
        TableroEquipo tableroEquipo = new TableroEquipo();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", id);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){

                    id = 0;
                    int idTablero = 0;
                    int idEquipo = 0;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    idTablero = Integer.valueOf(body.getProperty("idTablero").toString());
                    idEquipo = Integer.valueOf(body.getProperty("idEquipo").toString());

                    tableroEquipo.setId(id);
                    tableroEquipo.setIdTablero(idTablero);
                    tableroEquipo.setIdEquipo(idEquipo);
                }
            }

            return tableroEquipo;

        } catch (Exception ex) {
            return null;
        }
    }


    public List<Tablero> obtenerTablerosDeEquipo(int idEquipo) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/tableroEquipoWS?WSDL";

        String METHOD_NAME = "ObtenerTablerosDeEquipo";
        String ACTION_SOAP = "";
        List<Tablero> tableros = new ArrayList<>();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("idEquipo", idEquipo);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){
                    int id = 0;
                    String titulo = null;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    titulo = body.getProperty("titulo").toString();

                    Tablero tablero = new Tablero(id, titulo);
                    tableros.add(tablero);
                }
            }
            return tableros;

        } catch (Exception ex) {
            return null;
        }
    }


    public List<Equipo> obtenerEquiposDeTablero(int idTablero) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/tableroEquipoWS?WSDL";

        String METHOD_NAME = "ObtenerEquiposDeTablero";
        String ACTION_SOAP = "";
        List<Equipo> equipos = new ArrayList<>();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("idTablero", idTablero);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){
                    int id = 0;
                    String nombre = null;
                    String descripcion = null;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    nombre = body.getProperty("nombre").toString();
                    descripcion = body.getProperty("descripcion").toString();

                    Equipo equipo = new Equipo(id, nombre, descripcion);
                    equipos.add(equipo);
                }
            }
            return equipos;

        } catch (Exception ex) {
            return null;
        }
    }


    public boolean crearTableroUsuario(int idTablero, int idUsuario) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/tableroUsuarioWS?WSDL";

        Boolean resultado = false;

        String METHOD_NAME = "CrearTableroUsuario";
        String ACTION_SOAP = "";
        try {
            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("idTablero", idTablero);
            Request.addProperty("idUsuario", idUsuario);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;

        } catch(Exception ex){
            return resultado;
        }
    }

    public boolean eliminarTableroUsuario(int id) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/tableroUsuarioWS?WSDL";

        Boolean resultado = false;

        String METHOD_NAME = "EliminarTableroUsuario";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", id);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;


        } catch (Exception ex) {
            return resultado;
        }
    }

    public boolean eliminarTableroUsuarios(int idTablero) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/tableroUsuarioWS?WSDL";

        Boolean resultado = false;

        String METHOD_NAME = "EliminarTableroUsuarios";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("idTablero", idTablero);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;

        } catch (Exception ex) {
            return resultado;
        }
    }

    public TableroUsuario seleccionarTableroUsuario(int id) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/tableroUsuarioWS?WSDL";

        String METHOD_NAME = "SeleccionarTableroUsuario";
        String ACTION_SOAP = "";
        TableroUsuario tableroUsuario = new TableroUsuario();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", id);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){

                    id = 0;
                    int idTablero = 0;
                    int idUsuario = 0;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    idTablero = Integer.valueOf(body.getProperty("idTablero").toString());
                    idUsuario = Integer.valueOf(body.getProperty("idUsuario").toString());

                    tableroUsuario.setId(id);
                    tableroUsuario.setIdTablero(idTablero);
                    tableroUsuario.setIdUsuario(idUsuario);
                }
            }

            return tableroUsuario;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<Tablero> obtenerTablerosDeUsuario(int idUsuario) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/tableroUsuarioWS?WSDL";

        String METHOD_NAME = "ObtenerTablerosDeUsuario";
        String ACTION_SOAP = "";
        List<Tablero> tableros = new ArrayList<>();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("idUsuario", idUsuario);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){
                    int id = 0;
                    String titulo = null;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    titulo = body.getProperty("titulo").toString();

                    Tablero tablero = new Tablero(id, titulo);
                    tableros.add(tablero);
                }
            }
            return tableros;

        } catch (Exception ex) {
            return null;
        }
    }


    public List<Usuario> obtenerUsuariosDeTablero(int idTablero) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/tableroUsuarioWS?WSDL";

        String METHOD_NAME = "ObtenerUsuariosDeTablero";
        String ACTION_SOAP = "";
        List<Usuario> usuarios = new ArrayList<>();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("idTablero", idTablero);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){
                    int id = 0;
                    String nombre = null;
                    String email = null;
                    String contrasena = null;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    nombre = body.getProperty("nombre").toString();
                    email = body.getProperty("email").toString();
                    contrasena = body.getProperty("contrasena").toString();

                    Usuario usuario = new Usuario(id, nombre, email, contrasena);
                    usuarios.add(usuario);
                }
            }
            return usuarios;

        } catch (Exception ex) {
            return null;
        }
    }


    //-------Equipo-------------------------------------------------------------------------------------------------------------

    public int agregarEquipo(Equipo equipo) {
        int resultado = 0;

        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/EquipoWS?WSDL";

        String METHOD_NAME = "AgregarEquipo";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("nombre", equipo.getNombre());
            Request.addProperty("descripcion", equipo.getDescripcion());

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Integer.parseInt(res.toString());

            return resultado;

        } catch (Exception ex) {
            return resultado;
        }
    }

    public boolean eliminarEquipo(int id) {
        Boolean resultado = false;

        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/EquipoWS?WSDL";

        String METHOD_NAME = "EliminarEquipo";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", id);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;

        } catch (Exception ex) {
            return resultado;
        }
    }

    public boolean editarEquipo(Equipo equipo) {
        Boolean resultado = false;

        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/EquipoWS?WSDL";

        String METHOD_NAME = "EditarEquipo";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", equipo.getId());
            Request.addProperty("nombre", equipo.getNombre());
            Request.addProperty("descripcion", equipo.getDescripcion());

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;

        } catch (Exception ex) {
            return resultado;
        }
    }

    public Equipo seleccionarEquipo(int id) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/EquipoWS?WSDL";

        String METHOD_NAME = "SeleccionarEquipo";
        String ACTION_SOAP = "";
        Equipo equipo = new Equipo();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", id);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){

                    id = 0;
                    String nombre = null;
                    String descripcion = null;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    nombre = body.getProperty("nombre").toString();
                    descripcion = body.getProperty("descripcion").toString();

                    equipo.setId(id);
                    equipo.setNombre(nombre);
                    equipo.setDescripcion(descripcion);
                }
            }

            return equipo;

        } catch (Exception ex) {
            return null;
        }
    }

    public boolean agregarEquipoUsuario(int idEquipo, int idUsuario) {
        Boolean resultado = false;

        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/EquipoUsuarioWS?WSDL";

        String METHOD_NAME = "AgregarEquipoUsuario";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("idEquipo", idEquipo);
            Request.addProperty("idUsuario", idUsuario);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;

        } catch (Exception ex) {
            return resultado;
        }
    }

    public boolean eliminarEquipoUsuario(int id) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/EquipoUsuarioWS?WSDL";

        Boolean resultado = false;

        String METHOD_NAME = "EliminarEquipoUsuario";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", id);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;

        } catch (Exception ex) {
            return resultado;
        }
    }

    public EquipoUsuario seleccionarEquipoUsuario(int id) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/EquipoUsuarioWS?WSDL";

        String METHOD_NAME = "SeleccionarEquipoUsuario";
        String ACTION_SOAP = "";
        EquipoUsuario equipoUsuario = new EquipoUsuario();
        try {
            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", id);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){

                    id = 0;
                    int idEquipo = 0;
                    int idUsuario = 0;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    idEquipo = Integer.valueOf(body.getProperty("idEquipo").toString());
                    idUsuario = Integer.valueOf(body.getProperty("idUsuario").toString());

                    equipoUsuario.setId(id);
                    equipoUsuario.setIdEquipo(idEquipo);
                    equipoUsuario.setIdUsuario(idUsuario);
                }
            }

            return equipoUsuario;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<Equipo> obtenerEquiposDeUsuario(int idUsuario) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/EquipoUsuarioWS?WSDL";

        String METHOD_NAME = "ObtenerEquiposDeUsuario";
        String ACTION_SOAP = "";
        List<Equipo> equipos = new ArrayList<>();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("idUsuario", idUsuario);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){
                    int id = 0;
                    String nombre = null;
                    String descripcion = null;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    nombre = body.getProperty("nombre").toString();
                    descripcion = body.getProperty("descripcion").toString();

                    Equipo equipo = new Equipo(id, nombre, descripcion);
                    equipos.add(equipo);
                }
            }
            return equipos;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<Usuario> obtenerUsuariosDeEquipo(int idEquipo) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/EquipoUsuarioWS?WSDL";

        String METHOD_NAME = "ObtenerUsuariosDeEquipo";
        String ACTION_SOAP = "";
        List<Usuario> usuarios = new ArrayList<>();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("idEquipo", idEquipo);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){
                    int id = 0;
                    String nombre = null;
                    String email = null;
                    String contrasena = null;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    nombre = body.getProperty("nombre").toString();
                    email = body.getProperty("email").toString();
                    contrasena = body.getProperty("contrasena").toString();

                    Usuario usuario = new Usuario(id, nombre, email, contrasena);
                    usuarios.add(usuario);
                }
            }
            return usuarios;

        } catch (Exception ex) {
            return null;
        }
    }


    //------------------------------------------------------------------------------------------------------------------
    public boolean agregarModulo(Modulo modulo, int idTablero) {
        Boolean resultado = false;

        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ModuloWS?WSDL";

        String METHOD_NAME = "AgregarModulo";
        String ACTION_SOAP = "";
        try {
            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parametros a soap
            Request.addProperty("nombre", modulo.getNombre());
            Request.addProperty("idTablero", idTablero);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;

        } catch(Exception ex){
            return resultado;
        }
    }


    public boolean eliminarModulo(int id) {
        Boolean resultado = false;

        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ModuloWS?WSDL";

        String METHOD_NAME = "EliminarModulo";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", id);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;

        } catch (Exception ex) {
            return resultado;
        }
    }

/*
    public void eliminarModulos(int idTablero) {
        String METHOD_NAME = "EliminarModulos";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", idTablero);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(this.URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            if (resultado) {
                Log.d("RESPUESTA", "Se ha guardado correctamente.");
            } else {
                Log.d("RESPUESTA", "Ha ocurrido un error.");
            }

        } catch (Exception ex) {
            Log.d("Exception", ex.getMessage());
        }
    }
*/

    public boolean editarModulo(Modulo modulo) {
        Boolean resultado = false;

        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ModuloWS?WSDL";

        String METHOD_NAME = "EditarModulo";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", modulo.getId());
            Request.addProperty("nombre", modulo.getNombre().toString());

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;

        } catch (Exception ex) {
            return resultado;
        }
    }

    public Modulo seleccionarModulo(int id) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ModuloWS?WSDL";

        String METHOD_NAME = "SeleccionarModulo";
        String ACTION_SOAP = "";
        Modulo modulo = new Modulo();
        try {
            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", id);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){

                    id = 0;
                    String nombre = null;
                    int idTablero = 0;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    nombre = body.getProperty("nombre").toString();
                    idTablero = Integer.valueOf(body.getProperty("idTablero").toString());

                    modulo.setId(id);
                    modulo.setNombre(nombre);
                    modulo.setIdTablero(idTablero);
                }
            }

            return modulo;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<Modulo> obtenerModulosDeTablero(int idTablero) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ModuloWS?WSDL";

        String METHOD_NAME = "ObtenerModulosDeTablero";
        String ACTION_SOAP = "";
        List<Modulo> modulos = new ArrayList<>();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("idTablero", idTablero);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){
                    int id = 0;
                    String nombre = null;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    nombre = body.getProperty("nombre").toString();

                    Modulo modulo = new Modulo(id, nombre);
                    modulos.add(modulo);
                }
            }
            return modulos;

        } catch (Exception ex) {
            return null;
        }
    }


    //-----Actividad-------------------------------------------------------------------------------------------------------------

    public boolean agregarNota(Nota nota, int idModulo) {
        Boolean resultado = false;

        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ActividadWS?WSDL";

        String METHOD_NAME = "AgregarActividad";
        String ACTION_SOAP = "";
        try {
            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parametros a soap
            Request.addProperty("titulo", nota.getTitulo());
            Request.addProperty("descripcion", nota.getDescripcion());
            Request.addProperty("vencimiento", nota.getVencimiento());
            Request.addProperty("idModulo", idModulo);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;

        } catch(Exception ex){
            return resultado;
        }
    }


    public boolean eliminarNota(int id) {
        Boolean resultado = false;

        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ActividadWS?WSDL";

        String METHOD_NAME = "EliminarActividad";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", id);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;

        } catch (Exception ex) {
            return resultado;
        }
    }

    public boolean eliminarNotas(int idModulo) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ActividadWS?WSDL";

        Boolean resultado = false;

        String METHOD_NAME = "EliminarActividades";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", idModulo);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;

        } catch (Exception ex) {
            return resultado;
        }
    }

    public boolean editarNota(Nota nota) {
        Boolean resultado = false;

        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ActividadWS?WSDL";

        String METHOD_NAME = "EditarActividad";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", nota.getId());
            Request.addProperty("titulo", nota.getTitulo().toString());
            Request.addProperty("descripcion", nota.getDescripcion().toString());
            Request.addProperty("vencimiento", nota.getVencimiento().toString());

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;

        } catch (Exception ex) {
            return resultado;
        }
    }

    public Nota seleccionarNota(int id) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ActividadWS?WSDL";

        String METHOD_NAME = "SeleccionarActividad";
        String ACTION_SOAP = "";
        Nota nota = new Nota();
        try {
            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", id);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){

                    id = 0;
                    String titulo = null;
                    String descripcion = null;
                    String vencimiento = null;
                    int idModulo = 0;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    titulo = body.getProperty("titulo").toString();
                    descripcion = body.getProperty("descripcion").toString();
                    vencimiento = body.getProperty("vencimiento").toString();
                    idModulo = Integer.valueOf(body.getProperty("idModulo").toString());

                    nota.setId(id);
                    nota.setTitulo(titulo);
                    nota.setDescripcion(descripcion);
                    nota.setVencimiento(vencimiento);
                    nota.setIdModulo(idModulo);
                }
            }

            return nota;

        } catch (Exception ex) {
            return null;
        }
    }


    public List<Nota> seleccionarNotasDeModulo(int idModulo) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ActividadWS?WSDL";

        String METHOD_NAME = "ObtenerActividadesDeModulo";
        String ACTION_SOAP = "";
        List<Nota> notas = new ArrayList<>();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("idModulo", idModulo);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){
                    int id = 0;
                    String titulo = null;
                    String descripcion = null;
                    String vencimiento = null;
                    boolean completado = false;


                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    titulo = body.getProperty("titulo").toString();
                    descripcion = body.getProperty("descripcion").toString();
                    vencimiento = body.getProperty("vencimiento").toString();
                    completado = Boolean.valueOf(String.valueOf(body.getProperty("estado")));


                    Nota nota = new Nota(id, titulo, descripcion, vencimiento, completado);
                    notas.add(nota);
                }
            }
            return notas;

        } catch (Exception ex) {
            return null;
        }
    }

    public boolean agregarActividadUsuario(int idNota, int idUsuario) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ActividadUsuarioWS?WSDL";

        Boolean resultado = false;

        String METHOD_NAME = "AgregarActividadUsuario";
        String ACTION_SOAP = "";
        try {
            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parametros a soap
            Request.addProperty("idActividad", idNota);
            Request.addProperty("idUsuario", idUsuario);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;

        } catch(Exception ex){
            return resultado;
        }
    }


    public boolean eliminarActividadUsuario(int id) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ActividadUsuarioWS?WSDL";

        Boolean resultado = false;

        String METHOD_NAME = "EliminarActividadUsuario";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", id);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;

        } catch (Exception ex) {
            return resultado;
        }
    }


    public boolean eliminarActividadUsuarios(int idNota) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ActividadUsuarioWS?WSDL";

        Boolean resultado = false;

        String METHOD_NAME = "EliminarActividadUsuarios";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("idActividad", idNota);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;

        } catch (Exception ex) {
            return resultado;
        }
    }


    public ActividadUsuario seleccionarActividadUsuario(int id) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ActividadUsuarioWS?WSDL";

        String METHOD_NAME = "SeleccionarActividadUsuario";
        String ACTION_SOAP = "";
        ActividadUsuario actividadUsuario = new ActividadUsuario();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", id);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){
                    id = 0;
                    int idActividad = 0;
                    int idUsuario = 0;


                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    idActividad = Integer.valueOf(body.getProperty("id").toString());
                    idUsuario = Integer.valueOf(body.getProperty("id").toString());

                    actividadUsuario.setId(id);
                    actividadUsuario.setIdActividad(idActividad);
                    actividadUsuario.setIdUsuario(idUsuario);
                }
            }
            return actividadUsuario;

        } catch (Exception ex) {
            return null;
        }
    }


    public List<Nota> obtenerActividadesDeUsuario(int idUsuario) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ActividadUsuarioWS?WSDL";

        String METHOD_NAME = "ObtenerActividadesDeUsuario";
        String ACTION_SOAP = "";
        List<Nota> notas = new ArrayList<>();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("idUsuario", idUsuario);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){
                    int id = 0;
                    String titulo = null;
                    String descripcion = null;
                    String vencimiento = null;
                    boolean completado = false;


                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    titulo = body.getProperty("titulo").toString();
                    descripcion = body.getProperty("descripcion").toString();
                    vencimiento = body.getProperty("vencimiento").toString();
                    completado = Boolean.valueOf(String.valueOf(body.getProperty("estado")));


                    Nota nota = new Nota(id, titulo, descripcion, vencimiento, completado);
                    notas.add(nota);
                }
            }
            return notas;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<Usuario> ObtenerUsuariosDeActividad(int idNota) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ActividadUsuarioWS?WSDL";

        String METHOD_NAME = "ObtenerUsuariosDeActividad";
        String ACTION_SOAP = "";
        List<Usuario> usuarios = new ArrayList<>();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("idActividad", idNota);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){
                    int id = 0;
                    String nombre = null;
                    String email = null;
                    String contrasena = null;


                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    nombre = body.getProperty("nombre").toString();
                    email = body.getProperty("email").toString();
                    contrasena = body.getProperty("contrasena").toString();


                    Usuario usuario = new Usuario(id, nombre, email, contrasena);
                    usuarios.add(usuario);
                }
            }
            return usuarios;

        } catch (Exception ex) {
            return null;
        }
    }


    //-----------Comentario--------------------------------------------------------------------------------------------------

    public int crearComentario(Comentario comentario, int idUsuario, int idActividad) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ComentarioWS?WSDL";

        int resultado = 0;

        String METHOD_NAME = "CrearComentario";
        String ACTION_SOAP = "";

        try {
            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("descripcion", comentario.getDescripcion());
            Request.addProperty("fecha", comentario.getFecha());
            Request.addProperty("idUsuario", idUsuario);
            Request.addProperty("idActividad", idActividad);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Integer.parseInt(res.toString());

            return resultado;

        } catch(Exception ex){
            return resultado;
        }
    }

    public boolean eliminarComentario(int id) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ComentarioWS?WSDL";

        Boolean resultado = false;

        String METHOD_NAME = "EliminarComentario";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", id);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;


        } catch (Exception ex) {
            return resultado;
        }
    }

    public Comentario seleccionarComentario(int id) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ComentarioWS?WSDL";

        String METHOD_NAME = "SeleccionarComentario";
        String ACTION_SOAP = "";
        Comentario comentario = new Comentario();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", id);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){

                    id = 0;
                    String descripcion = null;
                    String fecha = null;
                    int idUsuario = 0;
                    int idActividad = 0;


                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    descripcion = body.getProperty("descripcion").toString();
                    fecha = body.getProperty("fecha").toString();
                    idUsuario = Integer.valueOf(body.getProperty("idUsuario").toString());
                    idActividad = Integer.valueOf(body.getProperty("idActividad").toString());


                    comentario.setId(id);
                    comentario.setDescripcion(descripcion);
                    comentario.setFecha(fecha);
                    comentario.setUsuario(this.seleccionarUsuario(idUsuario));
                    comentario.setIdActividad(idActividad);
                }
            }

            return comentario;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<Comentario> ObtenerComentariosDeActividad(int idNota) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ComentarioWS?WSDL";

        String METHOD_NAME = "ObtenerComentariosDeActividad";
        String ACTION_SOAP = "";
        List<Comentario> comentarios = new ArrayList<>();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("idActividad", idNota);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){
                    int id = 0;
                    String descripcion = null;
                    String fecha = null;
                    int idUsuario = 0;
                    int idActividad = 0;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    descripcion = body.getProperty("descripcion").toString();
                    fecha = body.getProperty("fecha").toString();
                    idUsuario = Integer.valueOf(body.getProperty("idUsuario").toString());
                    idActividad = Integer.valueOf(body.getProperty("idActividad").toString());

                    Comentario comentario = new Comentario(id, idActividad, descripcion, fecha, this.seleccionarUsuario(idUsuario));
                    comentarios.add(comentario);
                }
            }
            return comentarios;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<Comentario> ObtenerComentariosDeUsuario(int idUsuario) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ComentarioWS?WSDL";

        String METHOD_NAME = "ObtenerComentariosDeUsuario";
        String ACTION_SOAP = "";
        List<Comentario> comentarios = new ArrayList<>();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("idUsuario", idUsuario);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){
                    int id = 0;
                    String descripcion = null;
                    String fecha = null;
                    int idUser = 0;
                    int idActividad = 0;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    descripcion = body.getProperty("descripcion").toString();
                    fecha = body.getProperty("fecha").toString();
                    idUser = Integer.valueOf(body.getProperty("idUsuario").toString());
                    idActividad = Integer.valueOf(body.getProperty("idActividad").toString());

                    Comentario comentario = new Comentario(id, idActividad, descripcion, fecha, this.seleccionarUsuario(idUsuario));
                    comentarios.add(comentario);
                }
            }
            return comentarios;

        } catch (Exception ex) {
            return null;
        }
    }


    //-------Tarea---------------------------------------------------------------------------------------------------
    public int agregarTarea(Tarea tarea, int idNota, int idUsuario) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/subActividadWS?WSDL";

        int resultado = 0;

        String METHOD_NAME = "AgregarSubActividad";
        String ACTION_SOAP = "";
        try {
            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("descripcion", tarea.getDescripcion());
            Request.addProperty("vencimiento", tarea.getVencimiento());
            Request.addProperty("estado", "false");
            Request.addProperty("idActividad", idNota);
            Request.addProperty("idUsuario", idUsuario);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Integer.parseInt(res.toString());

            return resultado;

        } catch(Exception ex){
            return resultado;
        }
    }



    public boolean eliminarTarea(int id) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/TareaWS?WSDL";

        Boolean resultado = false;

        String METHOD_NAME = "EliminarTarea";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", id);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;

        } catch (Exception ex) {
            return resultado;
        }
    }

    public boolean eliminarTareas(int idNota) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/TareaWS?WSDL";

        Boolean resultado = false;

        String METHOD_NAME = "EliminarTareaes";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("idActividad", idNota);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;


        } catch (Exception ex) {
            return resultado;
        }
    }

    public boolean editarTarea(Tarea tarea, int idNota, int idUsuario, int id) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/TareaWS?WSDL";

        Boolean resultado = false;

        String METHOD_NAME = "EditarTarea";
        String ACTION_SOAP = "";

        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("descripcion", tarea.getDescripcion());
            Request.addProperty("vencimiento", tarea.getVencimiento());
            Request.addProperty("estado", tarea.isEstado());
            Request.addProperty("id", id);
            Request.addProperty("idNota", idNota);
            Request.addProperty("idUsuario", idUsuario);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;

        } catch (Exception ex) {
            return resultado;
        }
    }




    public Tarea seleccionarTarea(int id, int idUsuario, int idActividad) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/TareaWS?WSDL";

        String METHOD_NAME = "SeleccionarTarea";
        String ACTION_SOAP = "";
        Tarea tarea = new Tarea();
        try {
            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", id);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){

                    id = 0;
                    String descripcion = null;
                    String vencimiento = null;


                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    descripcion = body.getProperty("descripcion").toString();
                    vencimiento = body.getProperty("fecha").toString();
                    idUsuario = Integer.valueOf(body.getProperty("idUsuario").toString());
                    idActividad = Integer.valueOf(body.getProperty("idActividad").toString());


                    tarea.setId(id);
                    tarea.setDescripcion(descripcion);
                    tarea.setVencimiento(vencimiento);
                    tarea.setIdUsuario(idUsuario);
                    tarea.setIdActividad(idActividad);
                }
            }

            return tarea;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<Tarea> obtenerTareasDeUsuario(int idUsuario) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/TareaWS?WSDL";

        String METHOD_NAME = "ObtenerSubActividadesDeUsuario";
        String ACTION_SOAP = "";
        List<Tarea> Tareaes = new ArrayList<>();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("idUsuario", idUsuario);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){
                    int id = 0;
                    String descripcion = null;
                    String vencimiento = null;
                    int idUser = 0;
                    int idActividad = 0;
                    boolean estado = false;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    descripcion = body.getProperty("descripcion").toString();
                    vencimiento = body.getProperty("fecha").toString();
                    estado = Boolean.valueOf(body.getProperty("estado").toString());
                    idUser = Integer.valueOf(body.getProperty("idUsuario").toString());
                    idActividad = Integer.valueOf(body.getProperty("idActividad").toString());

                    Tarea Tarea = new Tarea(id, idUser, idActividad, descripcion, vencimiento, estado);
                    Tareaes.add(Tarea);
                }
            }
            return Tareaes;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<Tarea> obtenerTareasDeActividad(int idNota) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/subActividadWS?WSDL";

        String METHOD_NAME = "ObtenerSubActividadesDeActividad";
        String ACTION_SOAP = "";
        List<Tarea> Tareaes = new ArrayList<>();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("idActividad", idNota);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){
                    int id = 0;
                    String descripcion = null;
                    String vencimiento = null;
                    int idUser = 0;
                    int idActividad = 0;
                    boolean estado = false;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    descripcion = body.getProperty("descripcion").toString();
                    vencimiento = body.getProperty("fecha").toString();
                    estado = Boolean.valueOf(body.getProperty("estado").toString());
                    idUser = Integer.valueOf(body.getProperty("idUsuario").toString());
                    idActividad = Integer.valueOf(body.getProperty("idActividad").toString());

                    Tarea Tarea = new Tarea(id, idUser, idActividad, descripcion, vencimiento, estado);
                    Tareaes.add(Tarea);
                }
            }
            return Tareaes;

        } catch (Exception ex) {
            return null;
        }
    }

    public boolean comprobarExistenciaEmail(String email) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/UsuarioWS?wsdl";
        boolean dotNet = false;
        Boolean resultado = false;

        String METHOD_NAME = "comprobarExistenciaEmail";
        String ACTION_SOAP = "";
        try {
            //Configuracion del soap
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parametros a soap
            request.addProperty("email", email);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;

        } catch(Exception ex){
            Throwable tr = ex.getCause();
            return resultado;
        }
    }


    public Usuario seleccionUsuarioPorEmail(String emailS){
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/UsuarioWS?wsdl";
        Boolean resultado = false;

        String METHOD_NAME = "SeleccionUsuarioPorEmail";
        String ACTION_SOAP = "";
        Usuario usuario = new Usuario();
        try {
            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            Request.addProperty("email", emailS);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){

                    int id = 0;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());

                    usuario.setId(id);
                }
            }
            return usuario;
        } catch(Exception ex){
            return null;
        }
    }


    public List<Usuario> seleccionUsuarioPorEmailCoincidencia(String emailS){
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/UsuarioWS?wsdl";
        Boolean resultado = false;

        String METHOD_NAME = "SeleccionUsuarioPorEmailCoincidencia";
        String ACTION_SOAP = "";
        List<Usuario> usuarios = new ArrayList<>();
        try {
            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            Request.addProperty("email", emailS);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){

                    int id = 0;
                    String nombre = null;
                    String email = null;
                    String contrasena = null;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    nombre = body.getProperty("nombre").toString();
                    email = body.getProperty("email").toString();
                    contrasena = body.getProperty("contrasena").toString();

                    Usuario usuario = new Usuario(id, nombre, email, contrasena);
                    usuarios.add(usuario);
                }
            }
            return usuarios;
        } catch(Exception ex){
            return null;
        }
    }

    public List<Usuario> obtenerTodosUsuarios() {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/tableroEquipoWS?WSDL";

        String METHOD_NAME = "obtenerTodosUsuarios";
        String ACTION_SOAP = "";
        List<Usuario> usuarios = new ArrayList<>();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){
                    int id = 0;
                    String nombre = null;
                    String email = null;
                    String contrasena = null;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    nombre = body.getProperty("nombre").toString();
                    email = body.getProperty("email").toString();
                    contrasena = body.getProperty("contrasena").toString();

                    Usuario usuario = new Usuario(id, nombre, email, contrasena);
                    usuarios.add(usuario);
                }
            }
            return usuarios;

        } catch (Exception ex) {
            return null;
        }
    }


    //-------ModificaciÃ³n----------------------------------------------------------------------------------------------------
    /*public boolean crearModificacion(Modificacion modificacion, int idTablero, int idUsuario) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ModificacionWS?WSDL";

        Boolean resultado = false;

        String METHOD_NAME = "AgregarTarea";
        String ACTION_SOAP = "";
        try {
            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("descripcion", modificacion.getDescripcion());
            Request.addProperty("fecha", modificacion.getFecha());
            Request.addProperty("idTablero", idTablero);
            Request.addProperty("idUsuario", idUsuario);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;

        } catch(Exception ex){
            return resultado;
        }
    }



    public boolean eliminarModificacion(int id) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ModificacionWS?WSDL";

        Boolean resultado = false;

        String METHOD_NAME = "EliminarModificacion";
        String ACTION_SOAP = "";
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", id);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapPrimitive res = (SoapPrimitive) soapEnvelop.getResponse();

            resultado = Boolean.valueOf(res.toString());

            return resultado;


        } catch (Exception ex) {
            return resultado;
        }
    }

    public Modificacion seleccionarModificacion(int id) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ModificacionWS?WSDL";

        String METHOD_NAME = "SeleccionarModificacion";
        String ACTION_SOAP = "";
        Modificacion modificacion = new Modificacion();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("id", id);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            //Pasar datos a un objeto
            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){

                    //Variables para almacenar los datos
                    id = 0;
                    String descripcion = null;
                    String fecha = null;
                    int idTablero = 0 ;
                    int idUsuario = 0;

                    //Sabe
                    SoapObject body = (SoapObject) obj.getProperty(i);

                    //Recibir los datos
                    id = Integer.valueOf(body.getProperty("id").toString());
                    descripcion = body.getProperty("descripcion").toString();
                    fecha = body.getProperty("fecha").toString();
                    idTablero = Integer.valueOf(body.getProperty("idTablero").toString());
                    idUsuario = Integer.valueOf(body.getProperty("idUsuario").toString());

                    //Dar los datos al objeto
                    modificacion.setId(id);
                    modificacion.setDescripcion(descripcion);
                    modificacion.setFecha(fecha);
                    modificacion.setIdTablero(idTablero);
                    modificacion.setIdUsuario(idUsuario);
                }
            }

            return modificacion;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<Modificacion> obtenerModificacionesDeUsuario(int idUsuario) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ModificacionWS?WSDL";

        String METHOD_NAME = "ObtenerModificacionesDeUsuario";
        String ACTION_SOAP = "";
        List<Modificacion> modificaciones = new ArrayList<>();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("idUsuario", idUsuario);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){
                    int id = 0;
                    String descripcion = null;
                    String fecha = null;
                    int idTablero = 0;
                    int idUser = 0;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    descripcion = body.getProperty("descripcion").toString();
                    fecha = body.getProperty("fecha").toString();
                    idUser = Integer.valueOf(body.getProperty("idUsuario").toString());
                    idTablero = Integer.valueOf(body.getProperty("idTablero").toString());

                    Modificacion modificacion = new Modificacion(id, descripcion, fecha, idTablero, idUser);
                    modificaciones.add(modificacion);
                }
            }
            return modificaciones;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<Modificacion> ObtenerModificacionesDeTablero(int idTablero) {
        String NAMESPACE = "http://webservices/";
        String URL = "http://jorgenorza.ga:8080/ProyectoFinalWS/ModificacionWS?WSDL";

        String METHOD_NAME = "ObtenerModificacionesDeTablero";
        String ACTION_SOAP = "";
        List<Modificacion> modificaciones = new ArrayList<>();
        try {

            //Configuracion del soap
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Mandar parÃ¡metros al SOAP
            Request.addProperty("idTablero", idTablero);

            SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet = this.dotNet;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(ACTION_SOAP, soapEnvelop);

            //Recibir datos
            SoapObject obj = (SoapObject) soapEnvelop.bodyIn;

            if(obj != null) {
                int tamanio = obj.getPropertyCount();

                for(int i=0; i<tamanio; i++){
                    int id = 0;
                    String descripcion = null;
                    String fecha = null;
                    int idTablerox = 0;
                    int idUsuario = 0;

                    SoapObject body = (SoapObject) obj.getProperty(i);

                    id = Integer.valueOf(body.getProperty("id").toString());
                    descripcion = body.getProperty("descripcion").toString();
                    fecha = body.getProperty("fecha").toString();
                    idUsuario = Integer.valueOf(body.getProperty("idUsuario").toString());
                    idTablerox = Integer.valueOf(body.getProperty("idTablero").toString());

                    Modificacion modificacion = new Modificacion(id, descripcion, fecha, idTablero, idUsuario);
                    modificaciones.add(modificacion);
                }
            }
            return modificaciones;

        } catch (Exception ex) {
            return null;
        }
    }
*/
}
