package com.psk.bank.configuration;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.psk.bank.model.User;

public class CustomHttpMessageConverter extends AbstractHttpMessageConverter<User> {

	
	public CustomHttpMessageConverter(){
		List<MediaType> supportedMediaTypes=new ArrayList<MediaType>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		this.setSupportedMediaTypes(supportedMediaTypes);
		
	}
	
	
	@Override
	protected boolean supports(Class<?> clazz) {
		
		return clazz.equals(com.psk.bank.model.User.class);
	}

	@Override
	protected User readInternal(Class<? extends User> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		
		return null;
	}
	
	
	
	
	

	@Override
	protected void writeInternal(User t, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		
        String body = t.getId()+"|"+ t.getName() + "|" +
                t.getDate();
		outputMessage.getBody().write(body.getBytes());
		outputMessage.getBody().flush();
		
	}

}
