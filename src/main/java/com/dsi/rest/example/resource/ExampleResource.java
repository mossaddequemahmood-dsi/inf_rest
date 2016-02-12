package com.dsi.rest.example.resource;

import com.dsi.rest.annotation.Path;
import com.dsi.rest.annotation.Path.HttpMethod;
import com.dsi.rest.annotation.Path.MediaType;
import com.dsi.rest.annotation.RestResource;
import com.dsi.rest.example.dto.Hello;
import com.dsi.rest.requestresponse.Request;
import com.dsi.rest.requestresponse.Response;

@RestResource
public class ExampleResource {

	public static final String SIMPLE_JSON = "{\"msg\" : \"Hellooooooo World!\"}";

	/* Sample command - 
	 * curl -v -XGET http://localhost:8080/plainTextGetRequest
	 */
	@Path(mapping = "plainTextGetRequest", requestMethod = HttpMethod.GET, responseContentType = MediaType.TEXT_PLAIN_TYPE)
	public String plainTextGetRequest() {
		return "plainTextGetRequest - response";
	}

	/* Sample command - 
	 * curl -v -XGET http://localhost:8080/jsonGetRequest
	 */
	@Path(mapping = "jsonGetRequest", requestMethod = HttpMethod.GET, responseContentType = MediaType.APPLICATION_JSON)
	public String jsonGetRequest() {
		return SIMPLE_JSON;
	}

	/* Sample command - 
	 * curl -v -XGET http://localhost:8080/getRequestWithPathParams/mahmood/28
	 * TODO: Can not handle param type other then String, yet!
	 */
	@Path(mapping = "getRequestWithPathParams/{name}/{age}", requestMethod = HttpMethod.GET, responseContentType = MediaType.APPLICATION_JSON)
	public String getRequestWithPathParams(String name, String age) {
		return "{\"name\":\"" + name + "\",\"age\":" + age + "\"}";
	}

	/* Sample command - 
	 * curl -v -XGET http://localhost:8080/getRequestWithQueryParam\?name=mahmood
	 */
	@Path(mapping = "getRequestWithQueryParam", requestMethod = HttpMethod.GET)
	public String getRequestWithQueryParam(Request request) {
		return "{\"name\":\"" + request.getQueryParam("name") + "\"}";
	}

	/* Sample command - 
	 * curl -v -XGET http://localhost:8080/getRequestWithResponseHeader
	 */
	@Path(mapping = "getRequestWithResponseHeader", requestMethod = HttpMethod.GET, responseContentType = MediaType.APPLICATION_JSON)
	public void getRequestWithResponseHeader(Response response) {
		response.setHeader("a", "b");
	}

	/* Sample command - 
	 * curl -v -XGET http://localhost:8080/jsonGetRequestWithPojoJsonResponse
	 */
	@Path(mapping = "jsonGetRequestWithPojoJsonResponse", requestMethod = HttpMethod.GET, responseContentType = MediaType.APPLICATION_JSON)
	public Hello jsonGetRequestWithPojoJsonResponse() {
		return new Hello("Hello", "World");
	}

	/* Sample command - 
	 * curl -XPOST http://localhost:8080/jsonPostRequestWithPojoJsonResponse -d '{"msg1":"Hello","msg2":"World"}'
	 */
	@Path(mapping = "jsonPostRequestWithPojoJsonResponse", requestMethod = HttpMethod.POST, responseContentType = MediaType.APPLICATION_JSON)
	public Hello jsonPostRequestWithPojoJsonResponse(Hello hello) {
		return hello;
	}

	/* Sample command - 
	 * curl -XPOST http://localhost:8080/complexRequest/mahmood -d '{"msg1":"Hello","msg2":"World"}'
	 */
	@Path(mapping = "complexRequest/{name}", requestMethod = HttpMethod.POST, responseContentType = MediaType.APPLICATION_JSON)
	public void getHello2Post(String name, Hello hello) {
		System.out.println("---->" + name);
		System.out.println("---->" + hello);
	}

}