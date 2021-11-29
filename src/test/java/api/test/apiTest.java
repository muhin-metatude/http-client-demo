package api.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class apiTest
{
	@Test
	@Order(1)
	@DisplayName("Ensures that the use API call returns status code 200")
	void ensureThatUserAPICallReturnStatusCode200() throws Exception
	{
		final HttpClient client = HttpClient.newBuilder().build();
		final HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.github.com/users/vogella")).build();

		final HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

		assertThat(response.statusCode()).isEqualTo(200);
	}

	@Test
	@Order(2)
	@DisplayName("Ensures that the content type starts with application/json")
	void ensureThatJsonIsReturnedAsContentType() throws Exception
	{
		final HttpClient client = HttpClient.newBuilder().build();
		final HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.github.com/users/vogella")).build();

		final HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		final Optional<String> firstValue = response.headers().firstValue("Content-Type");

		final String string = firstValue.get();
		assertThat(string).startsWith("application/json");
	}

	@Test
	@Order(3)
	@DisplayName("Ensure that the JSON for the user vogella contains a reference to the Twitter user")
	void ensureJsonContainsTwitterHandler() throws Exception
	{
		final HttpClient client = HttpClient.newBuilder().build();
		final HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.github.com/users/vogella")).build();

		final HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		final String body = response.body();

		System.out.println(body);
		assertTrue(body.contains("twitter_username\":\"vogella\""));
	}
}
