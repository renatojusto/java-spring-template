package com.arch.stock.resource;

import com.arch.stock.domain.Product;
import com.arch.stock.repository.ProductRepository;
import com.arch.stock.util.tests.SpringIntegrationTests;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
public class ProductResourceIntegrationTests extends SpringIntegrationTests {

    private static final String PRODUCTS_RESOURCE = "/v1/products";
    private static final String CAIXA = "Caixa";
    private static final String UNIDADE = "Unidade";

    private static final BigDecimal ZERO = new BigDecimal("0.00000000");
    private static final BigDecimal DEZ = new BigDecimal("10.00000000");
    private static final BigDecimal VINTE = new BigDecimal("20.00000000");

    private static final String PAO = "Pao";
    private static final String LEITE = "Leite";
    private static final String OLEO = "Oleo";

    private static final long NOT_FOUNT_ID = 999999L;

    private static final int PADARIA_UNIAO = 1;
    private static final int TAUSTE = 2;
    private static final int CONFIANCA = 3;
    private static final int PADARIA_NAO_CADASTRADA = 4;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void shouldCreateProduct() throws Exception {
        String payloadProductA = getPostPayload(PAO, UNIDADE, DEZ, PADARIA_UNIAO);

        mockMvc.perform(post(PRODUCTS_RESOURCE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payloadProductA))
            .andExpect(status().isCreated());

        Optional<Product> productA = productRepository.findFirstByName(PAO);

        if (productA.isPresent()) {
            Product product = productA.get();
            Assertions.assertAll(() -> assertThat(product.getName()).isEqualTo(PAO),
                                 () -> assertThat(product.getUnitPrice()).isEqualTo(UNIDADE),
                                 () -> assertThat(product.getQuantityStock()).isEqualTo(DEZ));
        } else {
            Assertions.fail("Product not created");
        }
    }

    @Test
    public void shouldNotCreateProductSupplierNotFound() throws Exception {
        String payloadProductA = getPostPayload(PAO, UNIDADE, DEZ, PADARIA_NAO_CADASTRADA);

        mockMvc.perform(post(PRODUCTS_RESOURCE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payloadProductA))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.description").value("Supplier 4 not found."));
    }

    @Test
    public void shouldListAllProducts() throws Exception {
        String payloadProductA = getPostPayload(PAO, UNIDADE, DEZ, PADARIA_UNIAO);
        String payloadProductB = getPostPayload(LEITE, CAIXA, DEZ, CONFIANCA);

        mockMvc.perform(post(PRODUCTS_RESOURCE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payloadProductA))
            .andExpect(status().isCreated());

        mockMvc.perform(post(PRODUCTS_RESOURCE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payloadProductB))
            .andExpect(status().isCreated());

        mockMvc.perform(get(PRODUCTS_RESOURCE).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$.[*].name", Matchers.containsInAnyOrder(PAO, LEITE)))
            .andExpect(jsonPath("$.[*].unitPrice", Matchers.containsInAnyOrder(UNIDADE, CAIXA)));
    }

    @Test
    public void shouldReturnOneProduct() throws Exception {
        String payloadProductC = getPostPayload(OLEO, UNIDADE, DEZ, TAUSTE);

        mockMvc.perform(post(PRODUCTS_RESOURCE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payloadProductC))
            .andExpect(status().isCreated());

        Optional<Product> productC = productRepository.findFirstByName(OLEO);

        if (productC.isPresent()) {
            mockMvc.perform(get(PRODUCTS_RESOURCE + "/{id}", productC.get().getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        } else {
            Assertions.fail("Product not found");
        }
    }

    @Test
    public void shouldUpdateProduct() throws Exception {
        String payloadProductC = getPostPayload(OLEO, UNIDADE, DEZ, TAUSTE);
        String payloadProductD = getPutPayload(OLEO, UNIDADE, PADARIA_UNIAO);

        mockMvc.perform(post(PRODUCTS_RESOURCE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payloadProductC))
            .andExpect(status().isCreated());

        Optional<Product> productC = productRepository.findFirstByName(OLEO);

        if (productC.isPresent()) {
            Long id = productC.get().getId();

            mockMvc.perform(get(PRODUCTS_RESOURCE + "/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

            mockMvc.perform(put(PRODUCTS_RESOURCE + "/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payloadProductD))
                .andExpect(status().isOk());

            mockMvc.perform(get(PRODUCTS_RESOURCE + "/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(OLEO)));
        } else {
            Assertions.fail("Product not found");
        }
    }

    @Test
    public void shouldDeletedProduct() throws Exception {
        String payloadProductC = getPostPayload(OLEO, UNIDADE, DEZ, TAUSTE);

        mockMvc.perform(post(PRODUCTS_RESOURCE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payloadProductC))
            .andExpect(status().isCreated());

        Optional<Product> productC = productRepository.findFirstByName(OLEO);

        if (productC.isPresent()) {
            Long id = productC.get().getId();

            mockMvc.perform(get(PRODUCTS_RESOURCE + "/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

            mockMvc.perform(delete(PRODUCTS_RESOURCE + "/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

            Assertions.assertEquals(0, productRepository.findAllByName("Produto C").size());
        } else {
            Assertions.fail("Product not found");
        }
    }

    @Test
    public void shouldNotDeletedProductNotFound() throws Exception {
        String payloadProductC = getPostPayload(OLEO, UNIDADE, DEZ, TAUSTE);

        mockMvc.perform(post(PRODUCTS_RESOURCE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payloadProductC))
            .andExpect(status().isCreated());

        mockMvc.perform(delete(PRODUCTS_RESOURCE + "/{id}", NOT_FOUNT_ID)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.description").value(String.format("Product %d not found.", NOT_FOUNT_ID)));
    }

    @Test
    public void shouldIncreaseProduct() throws Exception {
        String payloadProductC = getPostPayload(OLEO, UNIDADE, DEZ, TAUSTE);
        String increaseOrDecreasePayload = getIncreaseOrDecreasePayload(DEZ);

        mockMvc.perform(post(PRODUCTS_RESOURCE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payloadProductC))
            .andExpect(status().isCreated());

        Optional<Product> productC = productRepository.findFirstByName(OLEO);

        if (productC.isPresent()) {
            Long id = productC.get().getId();

            mockMvc.perform(get(PRODUCTS_RESOURCE + "/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

            mockMvc.perform(post(PRODUCTS_RESOURCE + "/{id}/increase", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(increaseOrDecreasePayload))
                .andExpect(status().isOk());

            mockMvc.perform(get(PRODUCTS_RESOURCE + "/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantityStock").value(VINTE));
        } else {
            Assertions.fail("Product not found");
        }
    }

    @Test
    public void shouldDecreaseProduct() throws Exception {
        String payloadProductC = getPostPayload(OLEO, UNIDADE, DEZ, TAUSTE);
        String increaseOrDecreasePayload = getIncreaseOrDecreasePayload(DEZ);

        mockMvc.perform(post(PRODUCTS_RESOURCE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payloadProductC))
            .andExpect(status().isCreated());

        Optional<Product> productC = productRepository.findFirstByName(OLEO);

        if (productC.isPresent()) {
            Long id = productC.get().getId();

            mockMvc.perform(get(PRODUCTS_RESOURCE + "/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

            mockMvc.perform(post(PRODUCTS_RESOURCE + "/{id}/decrease", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(increaseOrDecreasePayload))
                .andExpect(status().isOk());

            mockMvc.perform(get(PRODUCTS_RESOURCE + "/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantityStock").value(ZERO));
        } else {
            Assertions.fail("Product not found");
        }
    }

    private String getPostPayload(String productName, String unit, BigDecimal initialStock, long supplierId) {
        try {
            return createPostPayload(productName, unit, initialStock, supplierId);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private String createPostPayload(String productName, String unit, BigDecimal initialStock, long supplierId) throws JSONException {
        JSONObject item = new JSONObject();
        item.put("name", productName);
        item.put("unitPrice", unit);
        item.put("initialQuantityStock", initialStock);
        item.put("supplierId", supplierId);
        return item.toString();
    }

    private String getPutPayload(String productName, String unit, long supplierId) {
        try {
            return createPutPayload(productName, unit, supplierId);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private String createPutPayload(String productName, String unit, long supplierId) throws JSONException {
        JSONObject item = new JSONObject();
        item.put("name", productName);
        item.put("unitPrice", unit);
        item.put("supplierId", supplierId);
        return item.toString();
    }

    private String getIncreaseOrDecreasePayload(BigDecimal quantity) {
        try {
            return createIncreaseOrDecreasePayload(quantity);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private String createIncreaseOrDecreasePayload(BigDecimal quantity) throws JSONException {
        JSONObject item = new JSONObject();
        item.put("quantity", quantity);
        return item.toString();
    }

}
