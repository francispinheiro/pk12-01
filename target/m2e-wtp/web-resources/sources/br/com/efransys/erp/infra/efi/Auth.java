package br.com.efransys.erp.infra.efi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Base64;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.fileupload.RequestContext;
import org.json.JSONObject;

public class Auth {
	
//	public final String client_id_h = "Client_Id_1aa3fb7f7e106e00aa73f65fab8b309e5bd189fd";
//	public final String client_secret_h = "Client_Secret_28d820b044fd200786308b14ff3c59303cf6633f";

	// Producao
	// Chave Client ID - Client_Id_c3dcf98c94b780200c68ad682ad0eb0f9f50c2ea
	// Client_Secret_c8862b332d6ad13081f2f031688ea8852cb322a8
	
	public final String client_id = "Client_Id_1aa3fb7f7e106e00aa73f65fab8b309e5bd189fd";
	public final String client_secret = "Client_Secret_28d820b044fd200786308b14ff3c59303cf6633f";	             	
	public final String basicAuth = Base64.getEncoder().encodeToString(((client_id+':'+client_secret).getBytes()));

	public String geraToken() {
		String access_token = "";

		try {
			// Diretório em que seu certificado em formato .p12 deve ser
			// inserido
			
			
			// "CASA_DIARIA_H.p12"
			
			// Identifica que há um arquivo em br.com.efransys.erp.infra.efi com o nome indicado
			InputStream inputT = getClass().getResourceAsStream("CASA_DIARIA_H.p12");
			getClass().getResourceAsStream("CASA_DIARIA_H.p12");
			getClass().getName();
			
			// prepara o arquivo e grava na pasta indicada mais não é o que vamos usar
			byte[] buff = new byte[inputT.available()];
			inputT.read(buff);
			File file11 = File.createTempFile("CASA_DIARIA_H.p12", "tmp");
			OutputStream outStream = new FileOutputStream(file11);
			outStream.write(buff);
			
			// Verifica a existencia do arquivo
			if (inputT != null) {
			    System.out.println("existe o arquivo");
			} else {
			    System.out.println("não existe o arquivo");
			}			
			
//			String t1 = getClass().getResource("/outhers/CASA_DIARIA_H.p12").getPath(); 
//			
//			String nm = t1;
			
			System.setProperty("javax.net.ssl.keyStore", "CASA_DIARIA_H.p12");
			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

			URL url = new URL("https://api-pix-h.gerencianet.com.br/oauth/token"); // Homologação
			
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "Basic " + basicAuth);
			conn.setSSLSocketFactory(sslsocketfactory);
			String input = "{\"grant_type\": \"client_credentials\"}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			InputStreamReader reader = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(reader);

			String response;
			
			StringBuilder responseBuilder = new StringBuilder();
						
			while ((response = br.readLine()) != null) {
				System.out.println(response);
				responseBuilder.append(response);
			}
			
			try {
				JSONObject jsonObject = new JSONObject(responseBuilder.toString());
				access_token = jsonObject.getString("access_token");
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();			
		}
		
		return access_token;

	}


}
