package RSA;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;


public class RSACrypter {
	
	

	private static KeyPair self_keypair = genKeyPair();
	//暗号形式、今回は公開鍵暗号方式にRSAを用いるが他にどのようなものがあるか調べよう
	private static final String CRYPT_ALGORITHM = "RSA";
	
	
	private PublicKey partner_pubKey = null;
	
	/**
	 * 自身の公開鍵データをString型のデータとして取得する
	 * 
	 * @return　String 自身の公開鍵情報
	 */
	public static String getPublicKey(){
		return byte2String(self_keypair.getPublic().getEncoded());
	}
	
	/**
	 * 公開鍵をStringデータから作成する
	 * @param filename ファイル名
	 * @return 公開鍵．失敗時は null
	 */
	private static PublicKey loadPublicKey(String str_pubkey){
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(getCRYPT_ALGORITHM());
			byte[] b = str_pubkey.getBytes();
			EncodedKeySpec keySpec = new X509EncodedKeySpec(b);
			return keyFactory.generatePublic(keySpec);
		} catch (InvalidKeySpecException ex) {
			Logger.getLogger(RSACrypter.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(RSACrypter.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	
	/**
	 * Stringで取得した相手の鍵情報を公開鍵インスタンスとして保管する
	 * 
	 */
	public void setPartner_PublicKey(String str_publickey){
		partner_pubKey = loadPublicKey(str_publickey);
	}
	
	
	/**
	 * この暗号化モジュールがそのような公開鍵暗号化手法を用いるかを返す
	 * 今回はRSA
	 * @return the CRYPT_ALGORITHM
	 */
	public static String getCRYPT_ALGORITHM() {
		return CRYPT_ALGORITHM;
	}
	
	
	/**
	 * 公開鍵暗号のキーペアを生成する．
	 * 公開鍵ビット長は 1024
	 * getPrivate(), getPublic() で秘密鍵，公開鍵を参照できる．
	 * @return キーペア．失敗時は null
	 */
	private  static KeyPair genKeyPair(){
		KeyPairGenerator generator;
		try {
			generator = KeyPairGenerator.getInstance(getCRYPT_ALGORITHM());
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			generator.initialize(1024, random);
			return generator.generateKeyPair();
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(RSACrypter.class.getName()).log(Level.SEVERE, null, ex);
		}

		return null;
	}

	/**
	 * 
	 * 鍵情報はbyte型になっているので、String型に変換し返す
	 * @param b
	 * @return
	 */
    private static String byte2String(byte[] b){

        // ハッシュを16進数文字列に変換
        StringBuffer sb= new StringBuffer();
        int cnt= b.length;
        for(int i= 0; i< cnt; i++){
            sb.append(Integer.toHexString( (b[i]>> 4) & 0x0F ) );
            sb.append(Integer.toHexString( b[i] & 0x0F ) );
        }
        return sb.toString();
    }
    
	/**
	 * バイナリを公開鍵で複合
	 * @param source 複合したいバイト列
	 * @param privateKey
	 * @return 複合したバイト列．失敗時は null
	 */
	private static byte[] decrypt(byte[] source, PrivateKey privateKey){
		try {
			Cipher cipher = Cipher.getInstance(getCRYPT_ALGORITHM());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return cipher.doFinal(source);
		} catch (IllegalBlockSizeException ex) {
			Logger.getLogger(RSACrypter.class.getName()).log(Level.SEVERE, null, ex);
		} catch (BadPaddingException ex) {
			Logger.getLogger(RSACrypter.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InvalidKeyException ex) {
			Logger.getLogger(RSACrypter.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(RSACrypter.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NoSuchPaddingException ex) {
			Logger.getLogger(RSACrypter.class.getName()).log(Level.SEVERE, null, ex);
		}

		return null;
	}
	/**
	 * 自身の公開鍵を相手に渡し、その公開鍵で暗号化されたStringデータを
	 * 自身の秘密鍵で複合化するためのメソッド
	 * 
	 * @param String source
	 * @return byte[]
	 */
	public byte[] decrypt_PartnerData_by_selfPrivateKey(String source){
		return decrypt(DatatypeConverter.parseHexBinary(source),self_keypair.getPrivate());
	}

	/**
	 * バイナリを秘密鍵で暗号化
	 * @param source 暗号化したいバイト列
	 * @param publicKey
	 * @return 暗号化したバイト列．失敗時は null
	 */
	private  static byte[] encrypt(byte[] source, PublicKey publicKey){
		try {
			Cipher cipher = Cipher.getInstance(getCRYPT_ALGORITHM());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return cipher.doFinal(source);
		} catch (IllegalBlockSizeException ex) {
			Logger.getLogger(RSACrypter.class.getName()).log(Level.SEVERE, null, ex);
		} catch (BadPaddingException ex) {
			Logger.getLogger(RSACrypter.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InvalidKeyException ex) {
			Logger.getLogger(RSACrypter.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(RSACrypter.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NoSuchPaddingException ex) {
			Logger.getLogger(RSACrypter.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	
	
	/**
	 * 相手から渡された公開鍵を用いて与えられたStringテキストデータを暗号化する
	 * 
	 * 
	 * @param String source
	 * @return byte[]
	 */
	public  byte[] encrypt_MyData_by_PartnerPublishKey(String source){
		
		/**
		 * 相手の公開鍵情報がセットされていな場合正しく暗号化出来ないので
		 * nullを返す
		 */
		if (partner_pubKey == null){
			return null;
		}
		
		return encrypt(source.getBytes(), partner_pubKey);
	}
	
	
	/**
	 * 自身で作成した公開鍵情報を用いてStringデータを暗号化する
	 * 
	 * @param String source
	 * @return byte[]
	 * @throws UnsupportedEncodingException 
	 */
	public  byte[] encrypt_MyData_by_selfPublishKey(String source) throws UnsupportedEncodingException{
		
		return encrypt(source.getBytes("utf-8"), self_keypair.getPublic());
	}
	/**
	 * コンストラクタ
	 * RSA暗号化モジュールの自身のキーペアを作成する。
	 * また相手の鍵情報を取得できるようにする
	 */
	public RSACrypter() {
		// TODO 自動生成されたコンストラクター・スタブ
		
		// 使用自身のするキーペア
		System.out.println("このユーザの秘密鍵");
		System.out.println(byte2String(self_keypair.getPrivate().getEncoded()));
		System.out.println("このユーザの公開鍵");
		System.out.println(byte2String(self_keypair.getPublic().getEncoded()));
		System.out.println("\n");
		
		
		
		
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO 自動生成されたメソッド・スタブ
		
		RSACrypter crypter = new RSACrypter();
		//自分自身の公開鍵と秘密鍵を用いて暗号化、複合化を行うと
		
		
		// 暗号化したいデータをバイト列で用意
		String string = "send,sampleID,4000,このメッセージを暗号化します。";
		
		System.out.println("暗号化するデータ（String）");
		System.out.println(string);


		// 暗号化
		byte[] enc = crypter.encrypt_MyData_by_selfPublishKey(string);
		System.out.println("暗号化後データ");
		System.out.println(byte2String(enc));

		// 複合化
		byte[] dec = crypter.decrypt_PartnerData_by_selfPrivateKey(byte2String(enc));
		System.out.println("複合後データ");
		
		System.out.println(new String(dec,"utf-8"));

	}

}
