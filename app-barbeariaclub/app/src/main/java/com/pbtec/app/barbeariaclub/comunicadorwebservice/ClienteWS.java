package com.pbtec.app.barbeariaclub.comunicadorwebservice;

import android.util.Log;

import com.pbtec.app.barbeariaclub.entidades.Cliente;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by root on 12/08/16.
 */
public class ClienteWS {
    private final String NAMESPACE = "http://com.ws/";
    private final String METODO_1 = "insertCliente";
    private final String METODO_2 = "verificarCliente";
    private final String METODO_3 = "atualizarSenha";
    private final String METODO_4 = "atualizarCliente";
    private final String URL = "http://node128040-server-pb.jelasticlw.com.br:8080/webservicebc/NovoWebService?wsdl";
    //"http://192.168.0.5:8080/WebApp/NovoWebService?wsdl";

    public String insertCliente(Cliente cliente){
        SoapObject soap = new SoapObject(NAMESPACE, METODO_1);
        PropertyInfo pi = new PropertyInfo();
        pi.name = "cliente";
        pi.setValue(cliente);
        pi.setType(cliente.getClass());
        soap.addProperty(pi);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);
        envelope.addMapping(NAMESPACE, Cliente.class.getName(), new Cliente().getClass());

        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call(NAMESPACE+METODO_1, envelope);
            Object msg = envelope.getResponse();
            return msg.toString();
        } catch (IOException | XmlPullParserException ex) {
            Logger.getLogger(ClienteWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Erro no sistema";
    }

    public Cliente verificarCliente(String email, String senha){
        SoapObject soap = new SoapObject(NAMESPACE,METODO_2);
        soap.addProperty("email", email);
        soap.addProperty("senha", senha);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);

        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call(NAMESPACE+METODO_2, envelope);
            Log.i("KSOAP","recebeu o objeto");
            SoapObject resposta = (SoapObject) envelope.getResponse();
            Cliente cliente = new Cliente();
            cliente.setNome(resposta.getProperty("nome").toString());
            cliente.setEmail(resposta.getPropertyAsString("email"));
            cliente.setDdd(Integer.parseInt(resposta.getProperty("ddd").toString()));
            cliente.setTelefone(resposta.getProperty("telefone").toString());
            cliente.setSenha(resposta.getProperty("senha").toString());
            if(!cliente.getNome().isEmpty()){
                return cliente;
            }
        } catch (IOException ex) {
            Logger.getLogger(ClienteWS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XmlPullParserException ex) {
            Logger.getLogger(ClienteWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String atualizarSenha(String email, String nova_senha){
        SoapObject soap = new SoapObject(NAMESPACE,METODO_3);
        soap.addProperty("email", email);
        soap.addProperty("nova_senha", nova_senha);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);

        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call(NAMESPACE+METODO_3, envelope);
            Object resposta = envelope.getResponse();
            return resposta.toString();
        } catch (IOException ex) {
            Logger.getLogger(ClienteWS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XmlPullParserException ex) {
            Logger.getLogger(ClienteWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Erro no sistema";
    }

    public String atualizarCliente(Cliente cliente, String email_antigo){
        SoapObject soap = new SoapObject(NAMESPACE,METODO_4);
        PropertyInfo pi = new PropertyInfo();
        pi.name = "cliente";
        pi.setValue(cliente);
        pi.setType(cliente.getClass());
        soap.addProperty(pi);
        soap.addProperty("email_antigo", email_antigo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);

        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call(NAMESPACE+METODO_4, envelope);
            Object resposta = envelope.getResponse();
            return resposta.toString();
        } catch (IOException ex) {
            Logger.getLogger(ClienteWS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XmlPullParserException ex) {
            Logger.getLogger(ClienteWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Erro no sistema";
    }
}
