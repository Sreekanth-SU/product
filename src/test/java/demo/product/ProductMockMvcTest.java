package demo.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.product.domain.Data;
import demo.product.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ProductApplication.class)
@AutoConfigureMockMvc
public class ProductMockMvcTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllProducts() throws Exception {
        MvcResult result =
                mockMvc
                        .perform(
                                get("/product"))
                        .andExpect(status().isOk())
                        .andReturn();

        List<Product> products =
                mapper.readValue(
                        result.getResponse().getContentAsString(), Data.class).getData();
        assertThat(products.size()).isEqualTo(100);
    }

    @Test
    public void testGetAllProductsByType() throws Exception {
        MvcResult phoneResult =
                mockMvc
                        .perform(
                                get("/product").queryParam("type", "phone"))
                        .andExpect(status().isOk())
                        .andReturn();
        List<Product> phones =
                mapper.readValue(
                        phoneResult.getResponse().getContentAsString(), Data.class).getData();
        List<Product> nonPhones = phones.stream().filter(product ->
                !product.getType().equalsIgnoreCase("phone")).collect(Collectors.toList());

        MvcResult subscriptionResult =
                mockMvc
                        .perform(
                                get("/product").queryParam("type", "subscription"))
                        .andExpect(status().isOk())
                        .andReturn();
        List<Product> subscriptions =
                mapper.readValue(
                        subscriptionResult.getResponse().getContentAsString(), Data.class).getData();
        List<Product> nonSubscriptions = subscriptions.stream().filter(product ->
                !product.getType().equalsIgnoreCase("subscription")).collect(Collectors.toList());

        assertTrue(!phones.isEmpty() && nonPhones.isEmpty());
        assertTrue(!subscriptions.isEmpty() && nonSubscriptions.isEmpty());
    }

    @Test
    public void testGetAllProductsByColor() throws Exception {
        MvcResult phoneResult =
                mockMvc
                        .perform(
                                get("/product").queryParam("property:color", "silver"))
                        .andExpect(status().isOk())
                        .andReturn();
        List<Product> products =
                mapper.readValue(
                        phoneResult.getResponse().getContentAsString(), Data.class).getData();

        assertTrue(products.get(1).getProperties().contains("color:silver"));
    }

    @Test
    public void testGetAllProductsByTypeAndColor() throws Exception {
        MvcResult phoneResult =
                mockMvc
                        .perform(
                                get("/product")
                                        .queryParam("type", "phone")
                                        .queryParam("property:color", "silver"))
                        .andExpect(status().isOk())
                        .andReturn();
        List<Product> products =
                mapper.readValue(
                        phoneResult.getResponse().getContentAsString(), Data.class).getData();

        assertTrue(products.get(1).getProperties().contains("color:silver")
                && products.get(0).getType().equalsIgnoreCase("phone"));
    }

    @Test
    public void testGetAllProductsByTypeAndColorAndMinPrice() throws Exception {
        MvcResult phoneResult =
                mockMvc
                        .perform(
                                get("/product")
                                        .queryParam("type", "phone")
                                        .queryParam("property:color", "silver")
                                        .queryParam("min_price", "500"))
                        .andExpect(status().isOk())
                        .andReturn();
        List<Product> products =
                mapper.readValue(
                        phoneResult.getResponse().getContentAsString(), Data.class).getData();

        assertTrue(products.get(0).getProperties().contains("color:silver")
                && products.get(0).getType().equalsIgnoreCase("phone")
                && products.get(0).getPrice().intValue() >= 500);
    }

    @Test
    public void testGetAllProductsByTypeAndColorAndMinPriceAndMaxPrice() throws Exception {
        MvcResult phoneResult =
                mockMvc
                        .perform(
                                get("/product")
                                        .queryParam("type", "phone")
                                        .queryParam("property:color", "silver")
                                        .queryParam("min_price", "500")
                                        .queryParam("max_price", "700"))
                        .andExpect(status().isOk())
                        .andReturn();
        List<Product> products =
                mapper.readValue(
                        phoneResult.getResponse().getContentAsString(), Data.class).getData();

        assertTrue(products.get(0).getProperties().contains("color:silver")
                && products.get(0).getType().equalsIgnoreCase("phone")
                && products.get(0).getPrice().intValue() >= 500 && products.get(0).getPrice().intValue() <= 600);
    }

    @Test
    public void testGetAllProductsByTypeAndColorAndMinPriceAndMaxPriceAndCity() throws Exception {
        MvcResult phoneResult =
                mockMvc
                        .perform(
                                get("/product")
                                        .queryParam("type", "phone")
                                        .queryParam("property:color", "vit")
                                        .queryParam("min_price", "500")
                                        .queryParam("max_price", "700")
                                        .queryParam("city", "Stockholm"))
                        .andExpect(status().isOk())
                        .andReturn();
        List<Product> products =
                mapper.readValue(
                        phoneResult.getResponse().getContentAsString(), Data.class).getData();

        assertTrue(products.get(0).getProperties().contains("color:vit"));
        assertTrue(products.get(0).getType().equalsIgnoreCase("phone"));
        assertTrue(products.get(0).getPrice().intValue() >= 500
                && products.get(0).getPrice().intValue() <= 700);
        assertTrue(products.get(0).getStore_address().contains("Stockholm"));
    }

    @Test
    public void testGetAllProductsByTypeAndMinGbLimitAndMaxGbLimit() throws Exception {
        MvcResult phoneResult =
                mockMvc
                        .perform(
                                get("/product")
                                        .queryParam("type", "subscription")
                                        .queryParam("property:gb_limit_min", "15")
                                        .queryParam("property:gb_limit_max", "100")
                                        .queryParam("city", "Stockholm"))
                        .andExpect(status().isOk())
                        .andReturn();
        List<Product> products =
                mapper.readValue(
                        phoneResult.getResponse().getContentAsString(), Data.class).getData();

        assertTrue(products.get(0).getType().equalsIgnoreCase("subscription"));
        assertTrue(Integer.parseInt(products.get(0).getProperties().split("gb_limit:")[1]) >= 15
                && Integer.parseInt(products.get(0).getProperties().split("gb_limit:")[1]) <= 100);
        assertTrue(products.get(0).getStore_address().contains("Stockholm"));
    }
}
