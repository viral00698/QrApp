package com.QrApplication.Service;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

import com.QrApplication.Interface.TokenGeneratorSubject;

@Service
public class GeneratorToken implements TokenGeneratorSubject {

	private String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private int TOKEN_BATCH_SIZE = 3;
	
	@Override
	public String generatorToken(String prifix) {
		
		String tmp ="";
		if(prifix.length()>=3) {
			tmp = prifix.substring(0,3).toUpperCase();
		}
		
		String r1 = this.generator(TOKEN_BATCH_SIZE);
		String r2 = this.generator(TOKEN_BATCH_SIZE);
		String result = tmp+"-"+r1+"-"+r2;

		return result;
	}

	@Override
	public String generator(int length) {
		SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }

        return code.toString();
	}
	
	

}
