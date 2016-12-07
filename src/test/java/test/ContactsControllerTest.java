package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ActiveProfiles("ci")
public class ContactsControllerTest extends AbstractControllerTest {

    @Before
    public void setUp() {
        super.setUp();
    }
    @Test
    public void invalidFilter() throws Exception {
        String uri = "/hello/contacts?nameFilter=^(54.*$";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status", 400, status);
    }
    @Test
    public void validData() throws Exception {
        String uri = "/hello/contacts?nameFilter=^[a-z].*$";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status", 200, status);
    }
}