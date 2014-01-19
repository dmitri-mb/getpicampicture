package getpicampicture;

/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
//package org.apache.http.examples.entity.mime;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
//import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Example how to use multipart/form encoded POST request.
 */
public class FileSenderHttp extends FileSender{

	private String desturi = "http://theinternetofthings.ru" + "/getpicampicture/uploadimage.php";
	
	public void setFileName(String filename){
		super.setFileName(filename);
	}
	
	public void setURI(String uri) {
		desturi = uri;
	}
	
	public FileSenderHttp(String filename, String uri){
		
		super(filename);
		desturi = uri;
	}
	
	private FileBody getFile(){
		
		return new FileBody(new File(super.getFileName()));
		
	}
	
	@Override
    public void execute() {

    	CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(desturi);

            FileBody bin = getFile();
            //StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("file_name", bin)
                    //.addPart("comment", comment)
                    .build();


            httppost.setEntity(reqEntity);

            System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("Response content length: " + resEntity.getContentLength());
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
            
        } catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
