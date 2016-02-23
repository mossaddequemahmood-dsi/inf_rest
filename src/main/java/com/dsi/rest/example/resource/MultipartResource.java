package com.dsi.rest.example.resource;

import com.dsi.rest.annotation.Path;
import com.dsi.rest.annotation.Path.HttpMethod;
import com.dsi.rest.annotation.Path.MediaType;
import com.dsi.rest.annotation.RestResource;
import com.dsi.rest.requestresponse.Request;

@RestResource
public class MultipartResource {

	/*
	 * curl -v -XPOST http://localhost:8080/multipartRequest --form
	 * "fileupload=@/tmp/agile.jpg;filename=a.txt" --form param1=value1 --form
	 * param2=value2
	 */
	@Path(mapping = "multipartRequest", requestMethod = HttpMethod.POST, responseContentType = MediaType.APPLICATION_JSON)
	public void multipartPost(Request request) {
		System.out.println(request.getMultipart());
	}

}