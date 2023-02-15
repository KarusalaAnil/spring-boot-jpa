package com.example.springbootjpa;

import com.example.springbootjpa.constroller.ProductController;
import com.example.springbootjpa.entity.Product;
import com.example.springbootjpa.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

/*Please set web environment for me for the @AutoConfigureMockMVC*/
@SpringBootTest
@AutoConfigureMockMvc
class SpringBootJpaApplicationTests {

    /*In order to communicate to Controller by using inject the Comtroller*/
    @Autowired
    private ProductController productController;

    /*to perform the MVC related and expecting to mock MVC org.springframework.test.web.serlvet*/
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository repository;

    /* we need tell to mockMVC who is my controller */
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(ProductController.class).build();
    }

    @Test
    public void addProductTest() throws Exception {
        //Url
        //Http Method Post
        // res & res :product (Json String)
            Product product = new Product(2, "demo", 100, "Demo product", "sample product");
            /*before request delegate to controller i need to tell here u r calling */
            Mockito.when(repository.save(Mockito.any())).thenReturn(product);
            /*to identified list of value we can use id*/
            mockMvc.perform(MockMvcRequestBuilders
                            .post("/products") // http method
                            .content(convertingObjectToString(product)) //send data
                            .contentType("application/json") // req
                            .accept("application/json")) //res
                    .andExpect(MockMvcResultMatchers.status().isOk()) // status
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists()); // return data

    }

    @Test
    public void getAllProductListTest() throws Exception {
        Product product1 = new Product(1, "demo", 100, "Demo product", "sample product");
        Product product2 = new Product(2, "demo", 200, "Demo2 product", "sample2 product");
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(product1, product2));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print()) // print data
                .andExpect(MockMvcResultMatchers.status().isOk()) // status
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists()) // return data
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(1));
    }

    @Test
    public void getTestProductById() throws Exception {
        Product product1 = new Product(108, "demo", 100, "Demo product", "sample product");
        Mockito.when(repository.findById(108)).thenReturn(Optional.of(product1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products/" + 108)
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()) // status
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(108)); // return data
    }

    @Test
    public void getUpdateProductTest() throws Exception {
        /*DB data =product1 is 1 to get*/
        Product product1 = new Product(1, "demo", 100, "Demo product", "sample product");
        Product productUpdated = new Product(108, "demo", 55555, "Demo4 product", "sample4 product");
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(product1));
        Mockito.when(repository.save(Mockito.any())).thenReturn(productUpdated);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/products/" + 1)
                        .content(convertingObjectToString(productUpdated))
                        .contentType("application/json")
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // status
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("demo")); // return data
    }

    @Test
    public void deleteTest() throws Exception {
        /*not return value so use do nothing and create instance for repository */
        Mockito.doNothing().when(repository).deleteById(Mockito.anyInt());
        Mockito.when(repository.count()).thenReturn(Long.valueOf(100));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/products/" + 1))
                .andExpect(MockMvcResultMatchers.status().isOk()); // status
//                .andExpect(MockMvcResultMatchers.jsonPath("$.").value(100));

    }
    @Test
    public void getProductsWithLessPriceTest() throws Exception {
        Product product1 = new Product(1, "demo", 100, "Demo product", "sample product");
        Product productUpdated = new Product(108, "demo", 55555, "Demo4 product", "sample4 product");
        Mockito.when(repository.findByPriceLessThan(108)).thenReturn(Arrays.asList(product1));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/products/less/"+108)
                .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists());
    }


    private String convertingObjectToString(Object o) {
        try {
            return new ObjectMapper().writeValueAsString(o);
        } catch (Exception e) {

        }
        return null;
    }

}
