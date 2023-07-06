package br.com.efransys.erp.infra.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

import br.com.efransys.erp.infra.converter.HexStringConverter;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;



public class AppUtils {

	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();


    private static final String ALGORITHM = "AES";
    private static final byte[] keyValue =
            new byte[]{'G', 'l', 'o', 'b', 'a', 'l', 'C', 'i', 'n', 'c', 'o', 'S', 'e', 'n', 'h', 'a'};


    private static Properties prop = new Properties();
    private static InputStream input = null;

    private static String ambiente;
    // load a properties file

	public static final String dirImagens = "/var/www/fotos/";
	
	public static final String dirXML = "\\XML\\";

    public static String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";


	public static String gerarNovaSenha() {
		String[] carct = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
				"m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
				"y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z", "!", "@", "#", "$", "%", "ˆ", "&", "*" };

		String senha = "";

		for (int x = 0; x < 10; x++) {
			int j = (int) (Math.random() * carct.length);
			senha += carct[j];

		}

		return senha;
	}

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for ( int j = 0; j < bytes.length; j++ ) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
	
	public static byte[] HexToBytes(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}
	
	public static Map<String,String> NamedParams(String... params) {
	    Map<String, String> map = new HashMap<String, String>();
        for(int i=0;i<params.length;i++){
            map.put(params[i],params[++i]);
        }
	    return map;
    }

	public static String toMd5(String valor) {
        MessageDigest mDigest;
        try {
            //Instanciamos o nosso HASH MD5, poder�amos usar outro como
            //SHA, por exemplo, mas optamos por MD5.
            mDigest = MessageDigest.getInstance("MD5");

            //Convert a String valor para um array de bytes em MD5
            byte[] valorMD5 = mDigest.digest(valor.getBytes("UTF-8"));

            //Convertemos os bytes para hexadecimal, assim podemos salvar
            //no banco para posterior compara��o se senhas
            StringBuffer sb = new StringBuffer();
            for (byte b : valorMD5){
                sb.append(Integer.toHexString((b & 0xFF) |
                        0x100).substring(1,3));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
	
	/**
	 * Formata competência dd/mm/AAAA em AAAA/MM
	 * @param date
	 * @return
	 */
	public static String formataCompetencia(Date date) {


		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");

		return sdf.format( date );
	}

	
	public static String saveArqXML(String path, UploadedFile upload, int id, String tipo) {
		if (upload == null) {
			return null;
		}
		String extesion = FilenameUtils.getExtension(upload.getFileName());
		try {
			InputStream is = upload.getInputstream();
			byte[] bytes = IOUtils.toByteArray(is);
			FileOutputStream fileOuputStream = new FileOutputStream(path + tipo + " - " + id + "." + extesion);
			fileOuputStream.write(bytes);
			fileOuputStream.close();

			return tipo + " - " +  id + "." + extesion;

		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Documentos  " + id
							+ " não terá seu arquivo enviado por um erro não tratado.", null));

		}
		return null;
	}
	
	public static String imageName(String filename, boolean encode) {
        try {
            if (encode) {
                return HexStringConverter.getHexStringConverterInstance().stringToHex(encrypt(filename));
            } else {
                return decrypt(HexStringConverter.getHexStringConverterInstance().hexToString(filename));
            }
        } catch (Exception e) {

        }
        return null;
    }

    public static String encrypt(String valueToEnc) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encValue = c.doFinal(valueToEnc.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encValue);
        return encryptedValue;
    }

    public static String decrypt(String encryptedValue) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedValue);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }


    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGORITHM);
        return key;
    }
  
    public static String foto(int id, String urlfoto) {
        String result = "";

        try {

        	if( urlfoto == null ) {
				Path dir = FileSystems.getDefault().getPath( AppUtils.dirImagens + "/");
				DirectoryStream<Path> stream = Files.newDirectoryStream( dir, id + ".{jpg,jpeg,png,gif,bmp,JPG,JPEG,PNG,GIF,BMP}" );
				for (Path path : stream) {
					result = AppUtils.imageName(AppUtils.dirImagens + "/" + path.getFileName().toString(), true);
					break;
				}
                stream.close();
				return result;
			} else {
				if (urlfoto.length() == 0) {
					result = AppUtils.imageName("sem_imagem.png", true);
				} else {
					result = AppUtils.imageName(urlfoto, true);
				}
			}


		} catch (Exception e ) {
			result = AppUtils.imageName("sem_imagem.png", true);
		}
		return result;
		
    }
    
    public static final String SERVIDOR_SMTP = "smtp.gmail.com" ; //"smtp.gmail.com";
    public static final String PORTA_SERVIDOR_SMTP = "587";    
    public static final String SMTP_USERNAME = "efransys@gmail.com";
    private static final String CONTA_EMAIL = "efransys@gmail.com";
    private static final String SENHA_EMAIL = "Felcar@3025&&"; 
    
	private static String de;
	private static String para;
	private static String assunto;
	private static String mensagem;
    
    
    public static Properties configuracaoEmail(){
    	
    	Properties config = new Properties();
    	
    	// Descomentar somente se utilizar servidor com proxy
    	/*
		config.setProperty("proxySet", "true");
    	config.setProperty("socksProxyHost", "127.0.0.1");
    	config.setProperty("socksProxyHost", "8080");    	 
    	 */
    	/* Envio via Tipo Seguranca StartTls */
    	
    	
    	config.put("mail.smtp.host", SERVIDOR_SMTP);		// smtp.gmail.com
    	config.put("mail.smtp.port", PORTA_SERVIDOR_SMTP);	// 587
    	config.put("mail.smtp.username", SMTP_USERNAME);    // efransy@gmail.com  	
    	config.put("mail.smtp.auth", "true");
    	config.put("mail.smtp.connectiontimeout", 5000);
    	config.put("mail.smtp.timeout", 5000);
    	config.put("mail.smtp.writetimeout", 5000);
    	
    	//config.put("mail.smtp.starttls.enable", "true"); 	//TLS
    	
    	
    	// SSL, Post 465
    	config.put("mail.smtp.socketFactory.port", 465); //SSL Port
    	config.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
    	
    	
    	/* Envio via Tipo Seguranca SSL/TLS
    	config.put("mail.transport.protocol", "smtp");
    	config.put("mail.smtp.starttls.enable", "true");
    	config.put("mail.smtp.host", SERVIDOR_SMTP);
    	config.put("mail.smtp.auth", "true");
    	config.put("mail.smtp.user", CONTA_EMAIL);
    	config.put("mail.debug", "true");
    	config.put("mail.smtp.port", PORTA_SERVIDOR_SMTP);
    	config.put("mail.smtp.socketFactory.port", PORTA_SERVIDOR_SMTP);
    	config.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");    	
    	config.put("mail.smtp.socketFactory.fallback", "false");
    	*/
    	return config;
    }
    
    /**
     * @author francis
     * @param reseiveDate
     * @return
     * Retorna lista com os dias da semana do mês corrente (dom-a-sab)
     */
    public static List mounthDaysWeek(LocalDate reseiveDate){
    	
    	// Definição do Locale para Brasil
    	Locale locale = new Locale("pt","BR");
    	
    	// Instancia receiveDate em ld
    	LocalDate ld = reseiveDate;    		
    		
    		// tamanho do mês
    		ld.lengthOfMonth();
    		
    		// mês
    		ld.getMonthValue();
    		
    		// 1 dia do mês com dia da semana
    		LocalDate di = ld.withDayOfMonth(1);

    		List<String> lstDayWeek = new ArrayList<String>(); 
    		
    		//lstDayWeek.add(di.getDayOfWeek().getDisplayName(TextStyle.SHORT, locale));
    		// Capturar dias da semana para o mês
    		for(int i=0; i < di.lengthOfMonth(); i++){
    			lstDayWeek.add(di.plusDays(i).getDayOfWeek().getDisplayName(TextStyle.SHORT, locale));
    		}
    	
    	return lstDayWeek;
    	    	
    }
    
    /**
     * @author francis
     * @param reseiveDate
     * @return
     * Retorna lista com os dias do mês corrente (1-31 | 1-30 | 1-29 | 1-28)
     */
    public static List mounthDays(LocalDate reseiveDate){
    	
    	// Definição do Locale para Brasil
    	Locale locale = new Locale("pt","BR");
    	
    	// Instancia receiveDate em ld
    	LocalDate ld = reseiveDate;    		
    		
    		// tamanho do mês
    		ld.lengthOfMonth();
    		
    		// mês
    		ld.getMonthValue();
    		
    		// 1 dia do mês com dia da semana
    		LocalDate di = ld.withDayOfMonth(1);

    		List<String> lstMounthDay = new ArrayList<String>(); 
    		
    		//lstDayWeek.add(di.getDayOfWeek().getDisplayName(TextStyle.SHORT, locale));
    		// Capturar dias da semana para o mês
    		for(int i=0; i < di.lengthOfMonth(); i++){
    			lstMounthDay.add(String.valueOf(di.plusDays(i).getDayOfMonth()));
    		}
    	
    	return lstMounthDay;
    	    	
    }
    

}
