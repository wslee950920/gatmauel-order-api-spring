package com.gatmauel.user.order;

import com.gatmauel.user.order.domain.detail.DetailDTO;
import com.gatmauel.user.order.domain.detail.DetailRepository;
import com.gatmauel.user.order.domain.food.Food;
import com.gatmauel.user.order.domain.food.FoodRepository;
import com.gatmauel.user.order.domain.order.OrderRepository;
import com.gatmauel.user.order.utils.JsonUtils;
import com.gatmauel.user.order.web.payload.request.MakeOrderRequestPayload;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderApplicationTests {
	@Autowired
	private WebApplicationContext context;

	@Autowired
	private FoodRepository foodRepository;

	@Autowired
	private DetailRepository detailRepository;

	@Autowired
	private OrderRepository orderRepository;

	private MockMvc mvc;

	private Food food;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
				.build();

		Food food= Food.builder()
				.name("칼국수")
				.price(7000).build();
		this.food=foodRepository.save(food);
	}

	@AfterEach
	public void cleaUp(){
		detailRepository.deleteAll();
		orderRepository.deleteAll();
		foodRepository.deleteAll();
	}

	@Test
	void contextLoads() {
	}

	@WithMockUser(roles = "USER")
	@Test
	public void request_make_order_success() throws Exception{
		String customer="맨유경비원";
		String phone="01020770883";
		int total=14000;
		String request="감사합니다.";
		String address="경기도 수원시";

		DetailDTO detailDTO=DetailDTO.builder()
				.foodId(food.getId())
				.foodName(food.getName())
				.num(2).build();
		List<DetailDTO> detailDTOList=new ArrayList<>();
		detailDTOList.add(detailDTO);

		MakeOrderRequestPayload requestPayload=MakeOrderRequestPayload.builder()
				.customer(customer)
				.phone(phone)
				.total(total)
				.request(request)
				.address(address)
				.details(detailDTOList).build();

		mvc.perform(
				post("/@user/order/make")
						.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
						.content(JsonUtils.toJson(requestPayload)))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.customer").value(customer))
				.andExpect(jsonPath("$.phone").value(phone))
				.andExpect(jsonPath("$.total").value(total))
				.andExpect(jsonPath("$.request").value(request))
				.andExpect(jsonPath("$.address").value(address))
				.andExpect(jsonPath("$.details[0].foodId").value(detailDTO.getFoodId()))
				.andExpect(jsonPath("$.details[0].foodName").value(detailDTO.getFoodName()))
				.andExpect(jsonPath("$.details[0].num").value(detailDTO.getNum()));;
	}

}
